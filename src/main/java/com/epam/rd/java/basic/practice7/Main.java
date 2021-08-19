package com.epam.rd.java.basic.practice7;

import com.epam.rd.java.basic.practice7.controllers.DOMController;
import com.epam.rd.java.basic.practice7.controllers.SAXController;
import com.epam.rd.java.basic.practice7.controllers.STAXController;
import com.epam.rd.java.basic.practice7.entity.Bookstore;
import com.epam.rd.java.basic.practice7.util.Sorter;

/**
 * Entry point for practice.
 */
public final class Main {

    public static final String OUTPUT = "Output ==> ";

    public static void main(final String[] args) throws Exception {
        String xmlFileName = args[0];
        System.out.println("Input ==> " + xmlFileName);
        ////////////////////////////////////////////////////////
        // DOM
        ////////////////////////////////////////////////////////

        // get
        DOMController domController = new DOMController(xmlFileName);
        domController.parse(true);
        Bookstore bookstore = domController.getBookstore();

        // sort (case 1)
        Sorter.sortBooksByAuthor(bookstore);

        // save
        String outputXmlFile = "output.dom.xml";
        DOMController.saveToXML(bookstore, outputXmlFile);
        System.out.println(OUTPUT + outputXmlFile);

        ////////////////////////////////////////////////////////
        // SAX
        ////////////////////////////////////////////////////////

        // get
        SAXController saxController = new SAXController(xmlFileName);
        saxController.parse(true);
        bookstore = saxController.getBookstore();

        // sort  (case 2)
        Sorter.sortBooksByYear(bookstore);

        // save
        outputXmlFile = "output.sax.xml";

        // other way:
        DOMController.saveToXML(bookstore, outputXmlFile);
        System.out.println(OUTPUT + outputXmlFile);

        ////////////////////////////////////////////////////////
        // StAX
        ////////////////////////////////////////////////////////

        // get
        STAXController staxController = new STAXController(xmlFileName);
        staxController.parse();
        bookstore = staxController.getBookstore();

        // sort  (case 3)
        Sorter.sortBooksByIdReverse(bookstore);

        // save
        outputXmlFile = "output.stax.xml";
        DOMController.saveToXML(bookstore, outputXmlFile);
        System.out.println(OUTPUT + outputXmlFile);
    }
}
