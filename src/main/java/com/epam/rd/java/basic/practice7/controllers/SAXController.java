package com.epam.rd.java.basic.practice7.controllers;

import com.epam.rd.java.basic.practice7.constants.Constants;
import com.epam.rd.java.basic.practice7.constants.XML;
import com.epam.rd.java.basic.practice7.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigDecimal;


/**
 * Controller for SAX parser.
 */
public class SAXController extends DefaultHandler {

    private final String xmlFileName;

    // current element name holder
    private String currentElement;

    // main container
    private Bookstore bookstore;

    private Book book;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain sax parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // XML document contains namespaces
        factory.setNamespaceAware(true);

        // set validation
        if (validate) {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        parser.parse(xmlFileName, this);
    }

    // ///////////////////////////////////////////////////////////
    // ERROR HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////

    @Override
    public void error(org.xml.sax.SAXParseException e) throws SAXException {
        // if XML document not valid just throw exception
        throw e;
    }

    public Bookstore getBookstore() {
        return bookstore;
    }

    // ///////////////////////////////////////////////////////////
    // CONTENT HANDLER IMPLEMENTATION
    // ///////////////////////////////////////////////////////////

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentElement = localName;

        if (XML.BOOKSTORE.equalsTo(currentElement)) {
            bookstore = new Bookstore();
            return;
        }

        if (XML.BOOK.equalsTo(currentElement)) {
            book = new Book();
            if (attributes.getLength() > 0) {
                book.setId(Integer.valueOf(attributes.getValue(0)));
            }
            return;
        }

        if (XML.PRICE.equalsTo(currentElement)) {
            CurrencyType currencyType = CurrencyType.fromValue(attributes.getValue(0));
            book.setPrice(new Price(null, currencyType));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String elementText = new String(ch, start, length).trim();

        // return if content is empty
        if (elementText.isEmpty()) {
            return;
        }

        if (XML.GENRE.equalsTo(currentElement)) {
            book.setGenre(Genre.fromValue(elementText));
            return;
        }
        if (XML.AUTHOR.equalsTo(currentElement)) {
            book.setAuthor(elementText);
            return;
        }
        if (XML.TITLE.equalsTo(currentElement)) {
            book.setTitle(elementText);
            return;
        }
        if (XML.YEAR.equalsTo(currentElement)) {
            book.setYear(Integer.parseInt(elementText));
            return;
        }
        if (XML.SERIES.equalsTo(currentElement)) {
            book.setSeries(elementText);
            return;
        }
        if (XML.PRICE.equalsTo(currentElement)) {
            book.getPrice().setValue(BigDecimal.valueOf(Float.parseFloat(elementText)));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (XML.BOOK.equalsTo(localName)) {
            // just add question to container
            bookstore.getBooks().add(book);
        }
    }

    public static void main(String[] args) throws Exception {
        // try to parse valid XML file (success)
        SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);

        // do parse with validation on (success)
        saxContr.parse(true);

        // obtain container
        Bookstore bookstore = saxContr.getBookstore();

        // we have Bookstore object at this point:
        System.out.print("Here is the bookstore: \n" + bookstore);

        // now try to parse NOT valid XML (failed)
        saxContr = new SAXController(Constants.INVALID_XML_FILE);
        try {
            // do parse with validation on (failed)
            saxContr.parse(true);
        } catch (Exception ex) {
            System.err.println("Validation is failed:\n" + ex.getMessage());
            System.err.println("Try to print bookstore object:" + saxContr.getBookstore());
        }

        // and now try to parse NOT valid XML with validation off (success)
        saxContr.parse(false);

        // we have Bookstore object at this point:
        System.out.print("Here is the bookstore: \n" + saxContr.getBookstore());
    }
}
