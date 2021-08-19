package com.epam.rd.java.basic.practice7.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BookstoreTest {
    private Bookstore bookstore;

    @Before
    public void setUp() {
        bookstore = new Bookstore();
    }

    @After
    public void tearDown() {
        bookstore = null;
    }

    @Test
    public void getBooks() {
        int expected = 0;
        int actual = bookstore.getBooks().size();
        Assert.assertEquals(expected, actual);
        List<Book> books = bookstore.getBooks();
        books.add(new Book());
        Assert.assertEquals(1, bookstore.getBooks().size());
    }

    @Test
    public void testToString() {
        String expected = "Bookstore contains no books";
        String actual = bookstore.toString();
        Assert.assertEquals(expected, actual);
    }
}