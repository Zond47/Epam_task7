package com.epam.rd.java.basic.practice7.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenreTest {
    private Book book = new Book();

    @Before
    public void setUp() {
        book.setGenre(Genre.FANTASY);
    }

    @After
    public void tearDown() {
        book = null;
    }

    @Test
    public void value() {
        String expected = "Fantasy";
        Assert.assertEquals(expected, book.getGenre().value());
    }

    @Test
    public void fromValue() {
        String expected = "Fantasy";
        Assert.assertEquals(expected, book.getGenre().value());
    }

    @Test
    public void values() {
        Genre[] array = {Genre.MYSTERY, Genre.HISTORICAL, Genre.FANTASY, Genre.ROMANCE,
                Genre.SCIENCE_FICTION, Genre.THRILLER, Genre.URBAN_FICTION};
        int expected = Genre.values().length;
        int actual = array.length;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void valueOf() {
        String expected = "Historical";
        Assert.assertEquals(expected, Genre.valueOf("HISTORICAL").value());
    }
}