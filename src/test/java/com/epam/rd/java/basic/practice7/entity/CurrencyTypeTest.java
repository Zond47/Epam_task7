package com.epam.rd.java.basic.practice7.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyTypeTest {
    private Book book = new Book();

    @Before
    public void setUp() {
        book.setPrice(new Price(BigDecimal.valueOf(200.0), CurrencyType.USD));
    }

    @After
    public void tearDown() {
        book = null;
    }

    @Test
    public void value() {
        String expected = "USD";
        String actual = book.getPrice().getCurrency().value();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fromValue() {
        CurrencyType expected = CurrencyType.USD;
        CurrencyType actual = CurrencyType.fromValue("USD");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void values() {
        CurrencyType[] expected = {CurrencyType.USD, CurrencyType.UAH};
        CurrencyType[] actual = CurrencyType.values();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void valueOf() {
        CurrencyType expected = CurrencyType.UAH;
        CurrencyType actual = CurrencyType.valueOf("UAH");
        Assert.assertEquals(expected, actual);
    }
}