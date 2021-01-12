package com.company.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private int id;
    private String name;
    private List<Attribute> attributes;
    private Node parentNode;
    private String content;

    public Node() {
    }

    public Node(int id, Node parentNode) {
        this.id = id;
        this.parentNode = parentNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{Node " + id +
                " name='" + name + '\'' +
                ", parentNodeId=" + (parentNode == null ? null : parentNode.getId()) +
//                ", attributes=" + attributes +
//                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && name.equals(node.name) && Objects.equals(attributes, node.attributes) && Objects.equals(parentNode, node.parentNode) && Objects.equals(content, node.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attributes, parentNode, content);
    }
}
