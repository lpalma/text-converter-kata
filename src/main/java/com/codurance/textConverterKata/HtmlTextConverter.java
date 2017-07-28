package com.codurance.textConverterKata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HtmlTextConverter
{
	public static final String HTML_LINE_BREAK = "<br />";

	private String fullFilenameWithPath;

    public HtmlTextConverter(String fullFilenameWithPath)
    {
        this.fullFilenameWithPath = fullFilenameWithPath;
    }

    public String convertToHtml() throws IOException{
		BufferedReader bufferedReader = getBufferedReader();

		String html = bufferedReader
				.lines()
				.map(toHtml())
				.collect(Collectors.joining(""));

		bufferedReader.close();

		return html;
    }

	public String getFilename() {
		return this.fullFilenameWithPath;
	}

	private Function<String, String> toHtml() {
		return line -> StringEscapeUtils.escapeHtml(line) + HTML_LINE_BREAK;
	}

	protected BufferedReader getBufferedReader() throws FileNotFoundException {
		return new BufferedReader(new FileReader(fullFilenameWithPath));
	}
}
