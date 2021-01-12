package com.company.service.impl;

import com.company.entities.Node;

import java.util.ArrayList;
import java.util.List;

public class XmlStructurePrinter {

    private List<Node> nodeList;
    private int lvlCounter;

    public XmlStructurePrinter() {
    }

    public XmlStructurePrinter(List<Node> nodeList) {
        this.nodeList = nodeList;
        lvlCounter = 1;
    }

    public void printStructure() {
        printLevel(null);
    }

    public void printLevel(List<Node> parentNodes) {
        List<Node> nextLevelNodes = new ArrayList<>();
        if (parentNodes != null) {
            for (Node parentNode : parentNodes) {
                for (Node node : nodeList) {
                    if (node.getParentNode() != null) {
                        if (node.getParentNode().equals(parentNode)) {
                            System.out.print(node);
                            nextLevelNodes.add(node);
                        }
                    }
                }
            }
        } else {
            for (Node node : nodeList) {
                if (node.getParentNode() == null) {
                    System.out.print(node);
                    nextLevelNodes.add(node);
                }
            }
        }

        if (!nextLevelNodes.isEmpty()) {
            System.out.println("==!!!==End of tag level №" + lvlCounter + "==!!!==");
            lvlCounter++;
            printLevel(nextLevelNodes);
        }
    }

    public int getLvlCounter() {
        return lvlCounter;
    }

    public void setLvlCounter(int lvlCounter) {
        this.lvlCounter = lvlCounter;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
