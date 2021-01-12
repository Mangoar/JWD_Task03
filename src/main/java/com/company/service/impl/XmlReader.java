package com.company.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {

    private String filename;
    private static final String XML_EXTENSION = ".xml";

    public XmlReader() {
    }

    public XmlReader(String filename) {
        this.filename = filename;
    }

    public List<String> readFileAll() throws IOException {

        List<String> listWithAllTags = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename + XML_EXTENSION);

        if (inputStream == null) {
            throw new IllegalArgumentException("file \"" + filename + XML_EXTENSION + "\" not found!");
        } else {
            try (InputStreamReader streamReader =
                         new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        listWithAllTags.add(line);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return listWithAllTags;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
