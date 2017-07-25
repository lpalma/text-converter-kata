package com.codurance.textConverterKata;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlTextConverterShould {
    @Test
    public void foo() {
        HtmlTextConverter converter = new HtmlTextConverter("foo");
        assertEquals("fixme", converter.getFilename());
    }
}
