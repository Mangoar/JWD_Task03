package com.company.service.impl;

import com.company.entities.Attribute;
import com.company.entities.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {

    private List<String> listToParse;
    private StringBuilder xmlString;
    private StringBuilder tempXmlString;
    private List<Node> nodeList;
    private int counter = 1;

    public XmlParser(List<String> listToParse) {
        this.listToParse = listToParse;
        nodeList = new ArrayList<>();
        xmlString = new StringBuilder();
        tempXmlString = new StringBuilder();
        for (String line : listToParse) {
            xmlString.append(line.trim());
        }
        deleteComments();
        deleteUnparsableXMLParts();
    }

    private void deleteComments() {
        String str = xmlString.toString();
        str = str.replaceAll("\\s*\\<!--[^\\-->]*\\-->\\s*", "");
        xmlString.setLength(0);
        xmlString.append(str);
        tempXmlString.append(str);
    }

    private void deleteUnparsableXMLParts() {
        String str = xmlString.toString();
        str = str.replaceAll("\\<?(.*?)\\?>", "");
        str = str.replaceAll("\\<!DOCTYPE(.*?)\\>", "");
        str = str.replaceAll("\\</br(.*?)\\>", "");
        xmlString.setLength(0);
        xmlString.append(str);
        tempXmlString.append(str);
    }

    public List<Node> parse() {
        parseNode(xmlString, null);
        return nodeList;
    }

    public void parseNode(StringBuilder xmlString, Node parentNode) {
        String tag = xmlString.substring(xmlString.indexOf("<") + 1, xmlString.indexOf(">"));
        Node currNode = new Node(counter, parentNode);
        parseTag(tag, currNode);
        counter++;
        StringBuilder tempDeepXmlStringBuilder = new StringBuilder(xmlString.substring(xmlString.indexOf(">") + 1,
                xmlString.indexOf("</" + currNode.getName() + ">")));
        StringBuilder tempRightXmlStringBuilder = new StringBuilder(xmlString.delete(0,
                xmlString.indexOf("</" + currNode.getName() + ">") + currNode.getName().length() + 3));

        if (checkOverlapingTagsinXML(tempDeepXmlStringBuilder)) {
            System.out.println("Your XML File is not valid!");
            System.exit(0);
        }
        if (checkIfTagsInXml(tempDeepXmlStringBuilder)) {
            parseNode(tempDeepXmlStringBuilder, currNode);
        } else {
            currNode.setContent(tempDeepXmlStringBuilder.toString());
        }
        if (checkIfTagsInXml(tempRightXmlStringBuilder)) {
            parseNode(tempRightXmlStringBuilder, parentNode);
        }
        nodeList.add(currNode);
    }

    private boolean checkOverlapingTagsinXML(StringBuilder xmlContent) {
        Matcher matcher = Pattern.compile("\\<(.*?)\\>").matcher(xmlContent);
        List<String> tags = new ArrayList<>();
        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();
            tags.add(matcher.group(1));
        }
        if (tags.size() % 2 != 0) {
            return true;
        }
        return false;
    }

    private void parseTag(String tag, Node parentNode) {
        List<Attribute> attributes = new ArrayList<>();
        List<String> tagWords = new ArrayList<>();
        List<String> attrNames = new ArrayList<>();
        List<String> attrValues = new ArrayList<>();

        Pattern p = Pattern.compile("[a-zA-Z0-9]+");
        Matcher m1 = p.matcher(tag);

        while (m1.find()) {
            tagWords.add(m1.group());
        }

        String tagName = tagWords.get(0);
        parentNode.setName(tagName);
        tagWords.remove(0);
        if (!tagWords.isEmpty()) {
            for (int i = 0; i < tagWords.size(); i++) {
                if (i % 2 != 0) {
                    attrValues.add(tagWords.get(i));
                } else {
                    attrNames.add(tagWords.get(i));
                }
            }

            for (int i = 0; i < attrNames.size(); i++) {
                Attribute attribute = new Attribute(attrNames.get(i), attrValues.get(i));
                attributes.add(attribute);
            }

            parentNode.setAttributes(attributes);
        }
    }

    private boolean checkIfTagsInXml(StringBuilder tempXml) {
        Matcher matcher = Pattern.compile("\\<(.*?)\\>").matcher(tempXml);

        List<String> tags = new ArrayList<>();

        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();
            tags.add(matcher.group(1));
        }
        if (tags.isEmpty()) {
            return false;
        } else {
            return true;
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

    public StringBuilder getTempXmlString() {
        return tempXmlString;
    }

    public void setTempXmlString(StringBuilder tempXmlString) {
        this.tempXmlString = tempXmlString;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
