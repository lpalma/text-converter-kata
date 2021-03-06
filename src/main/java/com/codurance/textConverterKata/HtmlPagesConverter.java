package com.codurance.textConverterKata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlPagesConverter {

    public static final String PAGE_BREAK = "PAGE_BREAK";
    private String filename;
    private List<Integer> breaks = new ArrayList<Integer>();

    public HtmlPagesConverter(String filename) throws IOException {
        this(filename, new BufferedReader(new FileReader(filename)));
    }

    public HtmlPagesConverter(String filename, BufferedReader bufferedReader) throws IOException {
        this.filename = filename;

        createPageBreaks(bufferedReader);
    }

    public String getHtmlPage(int page) throws IOException {
        BufferedReader reader = getBufferedReader();
        reader.skip(breaks.get(page));
        StringBuffer htmlPage = new StringBuffer();
        String line = reader.readLine();
        while (line != null) {
            if (line.contains(PAGE_BREAK)) {
                break;
            }
            htmlPage.append(StringEscapeUtils.escapeHtml(line));
            htmlPage.append("<br />");

            line = reader.readLine();
        }
        reader.close();
        return htmlPage.toString();
    }

    protected BufferedReader getBufferedReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(this.filename));
    }

    private void createPageBreaks(BufferedReader reader) throws IOException {
        this.breaks.add(0);
        int cumulativeCharCount = 0;
        String line = reader.readLine();
        while (line != null) {
            cumulativeCharCount += line.length() + 1; // add one for the newline
            if (line.contains("PAGE_BREAK")) {
                int page_break_position = cumulativeCharCount;
                breaks.add(page_break_position);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public String getFilename() {
        return this.filename;
    }

}
