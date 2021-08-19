package com.epam.rd.java.basic.practice7.controllers;

import com.epam.rd.java.basic.practice7.entity.Bookstore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SAXControllerTest {
    private Bookstore bookstore;

    @Before
    public void setUp() throws Exception {
        SAXController saxController = new SAXController("input.xml");
        saxController.parse(true);
        bookstore = saxController.getBookstore();
    }

    @After
    public void tearDown() {
        bookstore = null;
    }

    @Test
    public void parse() {
        String expected = "id    : 1\n" +
                "genre : Fantasy\n" +
                "author: Sarah J Maas\n" +
                "title : House of Earth and Blood\n" +
                "year  : 2020\n" +
                "series: Crescent City\n" +
                "price : 150.0 UAH\n" +
                "\n" +
                "id    : 2\n" +
                "genre : Fantasy\n" +
                "author: Carol Goodman\n" +
                "title : The Demon Lover\n" +
                "year  : 2011\n" +
                "series: Fairwick Chronicles\n" +
                "price : 11.0 USD\n" +
                "\n" +
                "id    : 3\n" +
                "genre : Fantasy\n" +
                "author: Carol Goodman\n" +
                "title : The Water Witch\n" +
                "year  : 2013\n" +
                "series: Fairwick Chronicle\n" +
                "price : 10.0 USD\n" +
                "\n" +
                "id    : 4\n" +
                "genre : Fantasy\n" +
                "author: Carol Goodman\n" +
                "title : The Angel Stone\n" +
                "year  : 2013\n" +
                "series: Fairwick Chronicles\n" +
                "price : 12.0 USD\n" +
                "\n" +
                "id    : 5\n" +
                "genre : Mystery\n" +
                "author: Carol Goodman\n" +
                "title : The Night Villa\n" +
                "year  : 2008\n" +
                "series: null\n" +
                "price : 11.0 USD\n" +
                "\n";
        String actual = bookstore.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getBookstore() {
        int expected = 5;
        int actual = bookstore.getBooks().size();
        Assert.assertEquals(expected, actual);
    }
}