package com.epam.rd.java.basic.practice7.controllers;

import com.epam.rd.java.basic.practice7.constants.Constants;
import com.epam.rd.java.basic.practice7.constants.XML;
import com.epam.rd.java.basic.practice7.entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Controller for DOM parser.
 */
public class DOMController {
    private String xmlFileName;

    // main container
    private Bookstore bookstore;

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Bookstore getBookstore() {
        return bookstore;
    }

    public void parse(boolean validate) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        dbf.setNamespaceAware(true);
        if (validate) {
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        }
        DocumentBuilder db = dbf.newDocumentBuilder();

        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                // throw exception if XML document is NOT valid
                throw e;
            }
        });

        Document document = db.parse(xmlFileName);
        Element root = document.getDocumentElement();
        bookstore = new Bookstore();
        NodeList bookNodes = root.getElementsByTagName("book");
        for (int i = 0; i < bookNodes.getLength(); i++) {
            Node nNode = bookNodes.item(i);
            Book book = new Book();
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                int id = Integer.parseInt(nNode.getAttributes().item(0).getTextContent());
                book.setId(id);
                String line = element
                        .getElementsByTagName("genre")
                        .item(0)
                        .getTextContent();
                book.setGenre(Genre.fromValue(line));
                book.setAuthor(element
                        .getElementsByTagName("author")
                        .item(0)
                        .getTextContent());
                book.setTitle(element
                        .getElementsByTagName("title")
                        .item(0)
                        .getTextContent());
                book.setYear(Integer.parseInt(element
                        .getElementsByTagName("year")
                        .item(0)
                        .getTextContent()));
                if (element.getElementsByTagName("series").item(0) != null) {
                    book.setSeries(element
                            .getElementsByTagName("series")
                            .item(0)
                            .getTextContent());
                }
                BigDecimal value = BigDecimal.valueOf(Float.parseFloat(element
                        .getElementsByTagName("price")
                        .item(0)
                        .getTextContent()));
                CurrencyType currencyType = CurrencyType.fromValue(element.getElementsByTagName("price").item(0)
                        .getAttributes().item(0).getTextContent());
                Price price = new Price(value, currencyType);
                book.setPrice(price);
            }
            // add books to container
            bookstore.getBooks().add(book);
        }
    }

    public static Document getDocument(Bookstore bookstore) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Element tElement = document.createElement(XML.BOOKSTORE.value());
        document.appendChild(tElement);

        for (Book book : bookstore.getBooks()) {
            Element rootElement = document.createElement(XML.BOOK.value());
            rootElement.setAttribute(XML.ID.value(), String.valueOf(book.getId()));
            tElement.appendChild(rootElement);

            Element childElement = document.createElement(XML.GENRE.value());
            childElement.setTextContent(book.getGenre().value());
            rootElement.appendChild(childElement);

            childElement = document.createElement(XML.AUTHOR.value());
            childElement.setTextContent(book.getAuthor());
            rootElement.appendChild(childElement);

            childElement = document.createElement(XML.TITLE.value());
            childElement.setTextContent(book.getTitle());
            rootElement.appendChild(childElement);

            childElement = document.createElement(XML.YEAR.value());
            childElement.setTextContent(String.valueOf(book.getYear()));
            rootElement.appendChild(childElement);

            childElement = document.createElement(XML.SERIES.value());
            if (book.getSeries() != null) {
                childElement.setTextContent(book.getSeries());
                rootElement.appendChild(childElement);
            }

            childElement = document.createElement(XML.PRICE.value());
            CurrencyType currencyType = book.getPrice().getCurrency();
            BigDecimal value = book.getPrice().getValue();

            childElement.setTextContent(String.valueOf(value));
            // set attribute
            childElement.setAttribute(XML.CURRENCY.value(), String.valueOf(currencyType));
            rootElement.appendChild(childElement);
        }
        return document;
    }

    public static void saveToXML(Bookstore bookstore, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        // Bookstore -> DOM -> XML
        saveToXML(getDocument(bookstore), xmlFileName);
    }


    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException {

        StreamResult result = new StreamResult(new File(xmlFileName));

        // set up transformation
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        javax.xml.transform.Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        t.transform(new DOMSource(document), result);
    }

    public static void main(String[] args) throws Exception {
        // try to parse NOT valid XML document with validation on (failed)
        DOMController domContr = new DOMController(Constants.INVALID_XML_FILE);
        try {
            // parse with validation (failed)
            domContr.parse(true);
        } catch (SAXException ex) {
            System.err.println("XML not valid");
            System.err.println("Bookstore object --> " + domContr.getBookstore());
        }

        // try to parse NOT valid XML document with validation off (success)
        domContr.parse(false);

        // we have Bookstore object at this point:
        System.out.print("Here is the bookstore: \n" + domContr.getBookstore());

        // save bookstore in XML file
        Bookstore bookstore = domContr.getBookstore();
        DOMController.saveToXML(bookstore, Constants.VALID_XML_FILE + ".dom-result.xml");
    }
}
