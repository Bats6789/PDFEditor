/*
 * File: main.java
 * Author: Dustin Marshall dustinrm@uab.edu
 * Assignment:  PDFTextExtractor - EE333 Spring 2020
 * Vers: 1.0.0 04/12/2020 DRM - initial coding
 *
 * Credits:  (if any for sections of code)
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.dustinrm.pdftextextractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author Dustin Marshall dustinrm@uab.edu
 */
public class main {

    public static void main(String[] args) {
        for( String arg : args ){
            System.out.println( arg );
        }
        PDFTextStripper extract;
        String filename;
        String extractChoice;
        String extractedText;
        File file;
        File outputFile;
        FileWriter writer;
        PDDocument document;
        //initializing variables
        extractChoice = args[0];
        filename = args[1];
        try {
            file = new File(filename);
            outputFile = new File("output.txt");
            extract = new PDFTextStripper();
            writer = new FileWriter(outputFile);
            document = PDDocument.load(file);
            if (args.length < 2) {
                System.out.println("Not enough arguments.");
            } else if (args.length == 2 && extractChoice.equalsIgnoreCase("Whole")) {
                extractedText = extract.getText(document);
                writer.write(extractedText);
                writer.close();
                document.close();
            } else if (args.length == 3 && extractChoice.equalsIgnoreCase("Page")) {
                int startPage = Integer.parseInt(args[2]);
                int endPage = Integer.parseInt(args[2]);
                extract.setStartPage(startPage);
                extract.setEndPage(endPage);
                extractedText = extract.getText(document);
                writer.write(extractedText);
                writer.close();
                document.close();
            } else if (args.length == 4 && extractChoice.equalsIgnoreCase("Pages")) {
                int startPage = Integer.parseInt(args[2]);
                int endPage = Integer.parseInt(args[3]);
                extract.setStartPage(startPage);
                extract.setEndPage(endPage);
                extractedText = extract.getText(document);
                writer.write(extractedText);
                writer.close();
                document.close();
            } else {
                System.out.println("Error: Input was incorrect");
            }
        } catch (IOException ex) {
            System.err.println("IOException.");
        }

    }

}
