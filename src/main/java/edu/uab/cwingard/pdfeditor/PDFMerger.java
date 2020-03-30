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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Merges multiple pdf's into a single pdf.
 * 
 * @author Blake Wingard - cwingard@uab.edu
 */
public class PDFMerger {
    public static void main( String[] args ) throws IOException{
        String outputFilename = null;
        ArrayList<String> inputFilenames = new ArrayList<>();
        File outputFile = null;
        ArrayList<File> inputFiles = new ArrayList<>();
        PDDocument outputPDF = null;
        ArrayList <PDDocument> inputPDFs = new ArrayList<>();
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        int fileCount = 0;
        int outputFlag = 0;
        
        if( args.length < 4 ){
            System.err.println( "ERROR: Not enough inputs" );
        } else {
            for( String arg : args ){
                if( arg.equals( "-o" ) ){
                    ++outputFlag;
                } else if( outputFlag == 1 ){
                    outputFilename = arg;
                    ++outputFlag;
                } else if( outputFlag > 2 ){
                    System.err.println( "ERROR: To many output flags." );
                    System.exit( -1 );
                } else {
                    inputFilenames.add( arg );
                }
            }
            if( outputFlag == 0 ){
                System.err.println( "ERROR: No output flag specified." );
            } else if( outputFlag != 2 ){
                System.err.println( "ERROR: No output filename provided for the output flag." );
            }
            try{
                outputPDF = new PDDocument();
                for( String inputFilename : inputFilenames ){
                    inputFiles.add( new File( inputFilename ));
                    inputPDFs.add( PDDocument.load( inputFiles.get( fileCount )));
                    fileCount++;
                }
            } catch( FileNotFoundException ex ){
                System.err.println( "ERROR: Failed to open: " + ex.toString());
            } catch( IOException ex ) {
                Logger.getLogger(PDFMerger.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for( PDDocument inputPDF : inputPDFs ){
                try {
                    pdfMerger.appendDocument( outputPDF, inputPDF );
                    inputPDF.close();
                } catch (IOException ex) {
                    Logger.getLogger(PDFMerger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if( outputFilename != null ){
                outputPDF.save( outputFilename );
            }
        }
    }
}
