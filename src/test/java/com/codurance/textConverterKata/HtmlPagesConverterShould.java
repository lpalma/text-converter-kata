package com.codurance.textConverterKata;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HtmlPagesConverterShould {

    public static final String FILENAME = "foo";
    public static final String EMPTY_STRING = "";
    public static final String EOF = null;
    public static final String HTML_NEW_LINE = "<br />";
    public static final String ESCAPED_HTML = "&amp;&lt;&gt;&quot;&quot;" + HTML_NEW_LINE;
    public static final String CHARACTERS_TO_ESCAPE = "&<>\"'";

    @Mock
    private BufferedReader indexBufferedReader;

    private BufferedReader pageBufferedReader;

    @Test
    public void convert_to_empty_string_when_file_is_empty() throws IOException {
        given(indexBufferedReader.readLine()).willReturn(EMPTY_STRING, EOF);

        pageBufferedReader = bufferedReader("");

        HtmlPagesConverter converter = new HtmlPagesConverterStub(FILENAME, indexBufferedReader);

        String convertedHtmlPage = converter.getHtmlPage(0);

        assertThat(convertedHtmlPage, is(EMPTY_STRING));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throw_exception_if_page_does_not_exist() throws IOException {
        given(indexBufferedReader.readLine()).willReturn(EMPTY_STRING, EOF);

        HtmlPagesConverter converter = new HtmlPagesConverterStub(FILENAME, indexBufferedReader);

        converter.getHtmlPage(1);
    }

    @Test
    public void convert_text_to_html_format() throws IOException {
        given(indexBufferedReader.readLine()).willReturn(
                "some line",
                HtmlPagesConverter.PAGE_BREAK,
                "another line",
                EOF);

        pageBufferedReader = bufferedReader("some line\n" +
                HtmlPagesConverter.PAGE_BREAK + "\n" +
                CHARACTERS_TO_ESCAPE);

        HtmlPagesConverter converter = new HtmlPagesConverterStub(FILENAME, indexBufferedReader);

        String convertedHtmlPage = converter.getHtmlPage(1);

        assertThat(convertedHtmlPage, is(ESCAPED_HTML));
    }

    private BufferedReader bufferedReader(String fileContent) {
        StringReader stringReader = new StringReader(fileContent);

        return new BufferedReader(stringReader);
    }

    class HtmlPagesConverterStub extends HtmlPagesConverter {

        public HtmlPagesConverterStub(String filename, BufferedReader bufferedReader) throws IOException {
            super(filename, bufferedReader);
        }

        @Override
        protected BufferedReader getBufferedReader() throws FileNotFoundException {
            return pageBufferedReader;
        }
    }
}
