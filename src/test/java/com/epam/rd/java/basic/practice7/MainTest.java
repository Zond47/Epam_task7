package com.epam.rd.java.basic.practice7;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream STD_OUT = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(STD_OUT);
        String[] files = {"output.dom.xml", "output.sax.xml", "output.stax.xml"};
        for (String file : files) {
            Path path = Paths.get(file);
            try {
                if (Files.exists(Paths.get(file)))
                    Files.delete(path);
            } catch (IOException e) {
                System.err.print(e.getMessage());
            }
        }
    }

    @Test
    public void main() throws Exception {
        Main.main(new String[]{"input.xml"});
        String expected = "Input ==> input.xml\r\n" +
                "Output ==> output.dom.xml\r\n" +
                "Output ==> output.sax.xml\r\n" +
                "Output ==> output.stax.xml\r\n";
        Assert.assertEquals(expected, outContent.toString());
    }
}