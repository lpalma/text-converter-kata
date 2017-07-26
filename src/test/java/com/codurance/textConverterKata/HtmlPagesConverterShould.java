package com.codurance.textConverterKata;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HtmlPagesConverterShould {

    @Mock
    private BufferedReader bufferedReader;

    @Test
    public void foo() throws IOException {
        HtmlPagesConverter converter = new HtmlPagesConverter("foo");
        assertEquals("fixme", converter.getFilename());
    }


}
