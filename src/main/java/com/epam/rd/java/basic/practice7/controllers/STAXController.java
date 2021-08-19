package com.epam.rd.java.basic.practice7.controllers;

import com.epam.rd.java.basic.practice7.constants.Constants;
import com.epam.rd.java.basic.practice7.constants.XML;
import com.epam.rd.java.basic.practice7.entity.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.math.BigDecimal;

import static javax.xml.stream.XMLInputFactory.IS_NAMESPACE_AWARE;

/**
 * Controller for StAX parser.
 */
public class STAXController extends DefaultHandler {

    private String xmlFileName;

    // main container
    private Bookstore bookstore;

    public Bookstore getBookstore() {
        return bookstore;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws XMLStreamException {
        Book book = new Book();

        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(IS_NAMESPACE_AWARE, true);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);

        XMLEventReader reader = factory.createXMLEventReader(new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                book = gettingBookstore(book, currentElement, startElement);
            }

            // handler for contents
            handlerForContents(book, currentElement, event);

            // handler for end tags
            handlerForEnd(book, event);
        }
        reader.close();
    }

    private Book gettingBookstore(Book book, String currentElement, StartElement startElement) {
        if (XML.BOOKSTORE.equalsTo(currentElement)) {
            bookstore = new Bookstore();
        }

        if (XML.BOOK.equalsTo(currentElement)) {
            book = new Book();
            Attribute attribute = startElement.getAttributeByName(new QName(XML.ID.value()));
            if (attribute != null) {
                book.setId(Integer.valueOf(attribute.getValue()));
            }
        }
        if (XML.PRICE.equalsTo(currentElement)) {
            Attribute attribute = startElement.getAttributeByName(new QName(XML.CURRENCY.value()));
            CurrencyType currencyType = CurrencyType.fromValue(attribute.getValue());
            book.setPrice(new Price(null, currencyType));
        }
        return book;
    }

    private void handlerForContents(Book book, String currentElement, XMLEvent event) {
        if (event.isCharacters()) {
            Characters characters = event.asCharacters();

            switch (currentElement) {
                case "genre":
                    book.setGenre(Genre.fromValue(characters.getData()));
                    break;
                case "author":
                    book.setAuthor(characters.getData());
                    break;
                case "title":
                    book.setTitle(characters.getData());
                    break;
                case "year":
                    book.setYear(Integer.parseInt(characters.getData()));
                    break;
                case "series":
                    book.setSeries(characters.getData());
                    break;
                case "price":
                    book.getPrice().setValue(BigDecimal.valueOf(Float.parseFloat(characters.getData())));
                    break;
                default:
                    System.out.print("There is no such value.");
            }
        }
    }

    private void handlerForEnd(Book book, XMLEvent event) {
        if (event.isEndElement()) {
            EndElement endElement = event.asEndElement();
            String localName = endElement.getName().getLocalPart();

            if (XML.BOOK.equalsTo(localName)) {
                bookstore.getBooks().add(book);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        Bookstore bookstore = staxContr.getBookstore();

        // we have Bookstore object at this point:
        System.out.println("====================================");
        System.out.print("Here is the bookstore: \n" + bookstore);
        System.out.println("====================================");
    }
}
