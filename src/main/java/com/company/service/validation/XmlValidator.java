package com.company.service.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlValidator {

    private List<String> listToParse;
    private StringBuilder xmlString;
    private List<String> tags;
    private List<String> comments;

    public XmlValidator(List<String> listToParse) {
        this.listToParse = listToParse;
        xmlString = new StringBuilder();
        for (String line : listToParse) {
            xmlString.append(line.trim());
        }
    }


    public boolean validate() {

        Matcher matcher = Pattern.compile("\\<(.*?)\\>").matcher(xmlString);

        tags = new ArrayList<>();
        comments = new ArrayList<>();

        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();

            if (matcher.group(1).startsWith("!--") && matcher.group(1).endsWith("--")) {
                comments.add(matcher.group(1));
            } else {
                if (!matcher.group(1).startsWith("?") && !matcher.group(1).endsWith("?") && !matcher.group(1).startsWith("!")) {
                    String tagParts[] = matcher.group(1).split(" ", 2);
                    if (tagParts.length > 0) {
                        tags.add(tagParts[0]);
                    } else {
                        tags.add(matcher.group(1).trim());
                    }
                }
            }
        }

        if (checkNotRightComment(comments)) {
            return false;
        }

        if (checkNotOneRootElement(tags)) {
            return false;
        }

        if (checkIfNotLowerCase(tags)) {
            return false;
        }

        if (checkIfNotClosedElements(tags)) {
            return false;
        } else {
            return true;
        }
    }

    //+
    private boolean checkNotRightComment(List<String> commentList) {
        for (String comment : commentList) {
            if (comment.substring(3, comment.length() - 2).contains("--")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfNotClosedElements(List<String> tagsList) {

        List<String> sortedOpenedList = new ArrayList<>();
        List<String> sortedClosedList = new ArrayList<>();
        for (String tag : tagsList) {
            if (!tag.trim().equals("/br")) {
                if (tag.startsWith("/")) {
                    sortedClosedList.add(tag.substring(1));
                } else {
                    sortedOpenedList.add(tag);
                }
            }
        }
        Collections.sort(sortedClosedList);
        Collections.sort(sortedOpenedList);

        if (!sortedClosedList.equals(sortedOpenedList)) {
            return true;
        }

        return false;
    }

    private boolean checkIfNotLowerCase(List<String> tagsList) {
        for (String tag : tagsList) {
            if (tag.matches(".*[A-Z].*")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNotOneRootElement(List<String> tagsList) {
        String rootOpen = tagsList.get(0);
        String rootClose = tagsList.get(tagsList.size() - 1).substring(1);
        for (int i = 1; i < tagsList.size() - 2; i++) {
            if (tagsList.get(i).contains(rootOpen)){
                return true;
            }
        }
        if (!rootClose.equals(rootOpen)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getListToParse() {
        return listToParse;
    }

    public void setListToParse(List<String> listToParse) {
        this.listToParse = listToParse;
    }

    public StringBuilder getXmlString() {
        return xmlString;
    }

    public void setXmlString(StringBuilder xmlString) {
        this.xmlString = xmlString;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
