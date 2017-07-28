package com.codurance.textConverterKata;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;

public class HtmlTextConverterShould {
    public static final String EMPTY = "";
    public static final String FILE_NAME = "some_file_name";
    public static final String HTML_NEW_LINE = "<br />";
    public static final String ESCAPED_HTML = "&amp;&lt;&gt;&quot;&quot;" + HTML_NEW_LINE;
    public static final String LINE = "line";
    public static final String CHARACTERS_TO_ESCAPE = "&<>\"'";

    private BufferedReader bufferedReader;
    private HtmlTextConverter htmlTextConverter;

    @Before
    public void setUp() throws Exception {
        htmlTextConverter = new HtmlTextConverterStub(FILE_NAME);
    }

    @Test
    public void return_empty_string_when_given_empty_file() throws Exception {
        bufferedReader = this.bufferedReader("");

        String convertedHtml = htmlTextConverter.convertToHtml();

        assertThat(convertedHtml, is(EMPTY));
    }

    @Test
    public void convert_end_of_lines_to_html_format() throws Exception {
        bufferedReader = this.bufferedReader(LINE);

        String convertedHtml = htmlTextConverter.convertToHtml();

        assertThat(convertedHtml, endsWith(HTML_NEW_LINE));
    }

    @Test
    public void escape_characters_to_html() throws Exception {
        bufferedReader = this.bufferedReader(CHARACTERS_TO_ESCAPE);

        String convertedHtml = htmlTextConverter.convertToHtml();

        assertThat(convertedHtml, endsWith(ESCAPED_HTML));
    }

    private BufferedReader bufferedReader(String fileContent) {
        StringReader stringReader = new StringReader(fileContent);

        return new BufferedReader(stringReader);
    }

    class HtmlTextConverterStub extends HtmlTextConverter {

        public HtmlTextConverterStub(String fullFilenameWithPath) {
            super(fullFilenameWithPath);
        }

        @Override
        protected BufferedReader getBufferedReader() throws FileNotFoundException {
            return bufferedReader;
        }
    }
}
