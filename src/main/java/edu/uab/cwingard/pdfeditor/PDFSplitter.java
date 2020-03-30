/*
 * File: PDFSplitter.java
 * Author: Blake Wingard, cwingard@uab.edu
 * Assignment: group project - EE333 Spring 2020
 * Vers: 1.0.0 03/29/2020 cbw - Initial coding
 */
package edu.uab.cwingard.pdfeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.multipdf.*;
import java.util.List;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Edits PDF's
 *
 * @author Blake Wingard - cwingard@uab.edu
 */
public class PDFSplitter {

    public static void main(String[] args) {
        for( String arg : args ){
            System.out.println( arg );
        }
        String filename;
        String path;
        int splitIndex;
        File file;
        ArrayList <String> filenames = new ArrayList<>();
        PDDocument pdfDocument;
        Splitter pdfSplitter;
        PDFMergerUtility pdfMerger;
        List< PDDocument> pdfDocuments;
        int i = 0;
        
        if (args.length < 2 ) {
            System.err.println("ERROR: Not enough arguments");
        } else {
            filename = args[ 0 ];
            splitIndex = Integer.parseInt( args[ 1 ] );
            if( splitIndex <= 0 ){
                System.err.println( "ERROR: index must be non-negative." );
                System.exit( -1 );
            }
            if( args.length >= 4 ){
                filenames.add( args[ 2 ] );
                filenames.add( args[ 3 ] );
            } else {
                filenames.add( "split1.pdf" );
                filenames.add( "split2.pdf" );
            }
            try {
                file = new File( filename );
                path = file.getAbsolutePath().substring( 0, file.getAbsolutePath().lastIndexOf(File.separator));
                pdfDocument = PDDocument.load(file);
                pdfSplitter = new Splitter();
                pdfMerger = new PDFMergerUtility();

                pdfDocuments = pdfSplitter.split(pdfDocument);
                for (int index = 1; index < splitIndex; ++index) {
                    pdfMerger.appendDocument(pdfDocuments.get(0), pdfDocuments.get(1));
                    pdfDocuments.get(1).close();
                    pdfDocuments.remove(1);
                }

                while( 2 < pdfDocuments.size() ) {
                    pdfMerger.appendDocument(pdfDocuments.get(1), pdfDocuments.get(2));
                    pdfDocuments.get(2).close();
                    pdfDocuments.remove(2);
                }

                for (PDDocument document : pdfDocuments) {
                    document.save( filenames.get( i ));
                    document.close();
                    ++i;
                }

            } catch (FileNotFoundException ex) {
                System.err.println("ERROR: File not found.");
            } catch (IOException ex) {
                System.err.println("IOException");
            }
        }
    }
}



