package com.epam.rd.java.basic.practice7.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PriceTest {
    private Book book = new Book();

    @Before
    public void setUp() {
        book.setPrice(new Price(BigDecimal.valueOf(123.0), CurrencyType.UAH));
    }

    @After
    public void restoreStreams() {
        book = null;
    }


    @Test
    public void getValue() {
        BigDecimal expected = BigDecimal.valueOf(123.0);
        Assert.assertEquals(expected, book.getPrice().getValue());
    }

    @Test
    public void setValue() {
        book.getPrice().setValue(BigDecimal.valueOf(100.0));
        BigDecimal expected = BigDecimal.valueOf(100.0);
        Assert.assertEquals(expected, book.getPrice().getValue());
    }

    @Test
    public void getCurrency() {
        String expected = "UAH";
        Assert.assertEquals(expected, book.getPrice().getCurrency().value());
    }
}