package com.epam.rd.java.basic.practice7.util;

import com.epam.rd.java.basic.practice7.constants.Constants;
import com.epam.rd.java.basic.practice7.controllers.DOMController;
import com.epam.rd.java.basic.practice7.entity.Book;
import com.epam.rd.java.basic.practice7.entity.Bookstore;

import java.util.Collections;
import java.util.Comparator;


/**
 * Contains static methods for sorting.
 */
public class Sorter {

    // //////////////////////////////////////////////////////////
    // these are comparators
    // //////////////////////////////////////////////////////////

    /**
     * Sorts books by author.
     */
    public static final Comparator<Book> SORT_BOOK_BY_AUTHOR = (o1, o2) -> o1.getAuthor().compareTo(o2.getAuthor());

    /**
     * Sorts books by year.
     */
    public static final Comparator<Book> SORT_BOOKS_BY_YEAR = (o1, o2) -> o1.getYear() - o2.getYear();

    /**
     * Sorts book by ID in reverse mode.
     */
    public static final Comparator<Book> SORT_BOOKS_BY_ID_REVERSE = (o1, o2) -> o2.getId() - o1.getId();

    // //////////////////////////////////////////////////////////
    // these methods take Bookstore object and sort it
    // with according comparator
    // //////////////////////////////////////////////////////////

    public static void sortBooksByAuthor(Bookstore bookstore) {
        Collections.sort(bookstore.getBooks(), SORT_BOOK_BY_AUTHOR);
    }

    public static void sortBooksByYear(Bookstore bookstore) {
        Collections.sort(bookstore.getBooks(), SORT_BOOKS_BY_YEAR);
    }

    public static void sortBooksByIdReverse(Bookstore bookstore) {
        Collections.sort(bookstore.getBooks(), SORT_BOOKS_BY_ID_REVERSE);
    }


    public static void main(String[] args) throws Exception {
        // input.xml --> Bookstore object
        DOMController domController = new DOMController(Constants.VALID_XML_FILE);
        domController.parse(false);
        Bookstore bookstore = domController.getBookstore();

        System.out.println("=============Default order================");
        System.out.println(bookstore);
        System.out.println("=============Sorting by author=============");
        Sorter.sortBooksByAuthor(bookstore);
        System.out.println(bookstore);
        System.out.println("=============Sorting by year===============");
        Sorter.sortBooksByYear(bookstore);
        System.out.println(bookstore);
        System.out.println("=============Sorting by genre==============");
        Sorter.sortBooksByIdReverse(bookstore);
        System.out.println(bookstore);
    }
}
