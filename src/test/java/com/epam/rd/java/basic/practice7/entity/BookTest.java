package com.epam.rd.java.basic.practice7.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BookTest {
    private Book book;

    @Before
    public void setUp() {
        book = new Book();
        book.setGenre(Genre.THRILLER);
        book.setAuthor("Jennifer Estep");
        book.setTitle("Spider's Bite");
        book.setYear(2020);
        book.setSeries("Elemental Assassin");
        book.setPrice(new Price(BigDecimal.valueOf(500.0), CurrencyType.USD));
        book.setId(7);
    }

    @After
    public void tearDown() {
        book = null;
    }

    @Test
    public void getSetGenre() {
        String expected = "Thriller";
        String actual = book.getGenre().value();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetAuthor() {
        String expected = "Jennifer Estep";
        String actual = book.getAuthor();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetTitle() {
        String expected = "Spider's Bite";
        String actual = book.getTitle();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetYear() {
        int expected = 2020;
        int actual = book.getYear();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetSeries() {
        String expected = "Elemental Assassin";
        String actual = book.getSeries();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetPrice() {
        BigDecimal expected = BigDecimal.valueOf(500.0);
        BigDecimal actual = book.getPrice().getValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSetId() {
        int expected = 7;
        int actual = book.getId();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        String expected = "id    : 7\n" +
                "genre : Thriller\n" +
                "author: Jennifer Estep\n" +
                "title : Spider's Bite\n" +
                "year  : 2020\n" +
                "series: Elemental Assassin\n" +
                "price : 500.0 USD\n";
        String actual = book.toString();
        Assert.assertEquals(expected, actual);
    }
}