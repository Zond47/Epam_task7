package com.epam.rd.java.basic.practice7.util;

import com.epam.rd.java.basic.practice7.controllers.DOMController;
import com.epam.rd.java.basic.practice7.entity.Book;
import com.epam.rd.java.basic.practice7.entity.Bookstore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SorterTest {
    private Bookstore bookstore;
    private DOMController domController;

    @Before
    public void setUp() throws Exception {
        domController = new DOMController("input.xml");
        domController.parse(true);
        bookstore = domController.getBookstore();
    }

    @After
    public void tearDown() {
        bookstore = null;
    }

    @Test
    public void sortBooksByAuthor() {
        Sorter.sortBooksByAuthor(bookstore);
        Book book = bookstore.getBooks().get(0);
        int expected = 2;
        int actual = book.getId();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sortBooksByYear() {
        Sorter.sortBooksByYear(bookstore);
        Book book = bookstore.getBooks().get(0);
        int expected = 2008;
        int actual = book.getYear();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sortBooksByIdReverse() {
        Sorter.sortBooksByIdReverse(bookstore);
        Book book = bookstore.getBooks().get(0);
        int expected = 5;
        int actual = book.getId();
        Assert.assertEquals(expected, actual);
    }
}