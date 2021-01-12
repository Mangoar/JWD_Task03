package com.company.main;

import com.company.service.impl.XmlParser;
import com.company.service.impl.XmlReader;
import com.company.service.impl.XmlStructurePrinter;
import com.company.service.validation.XmlValidator;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter name of file in resources folder( without file extension ): ");
        String fileName = input.next();

        XmlReader xmlReader = new XmlReader(fileName);
        boolean validationPassed;
        try {
            XmlValidator xmlValidator = new XmlValidator(xmlReader.readFileAll());
            validationPassed = xmlValidator.validate();
            if (!validationPassed) {
                System.out.println("Your XML File is not valid!");
            } else {
                XmlParser xmlParser = new XmlParser(xmlReader.readFileAll());
                xmlParser.parse();
                XmlStructurePrinter xmlStructurePrinter = new XmlStructurePrinter(xmlParser.getNodeList());
                xmlStructurePrinter.printStructure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
