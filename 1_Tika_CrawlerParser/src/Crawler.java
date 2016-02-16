package com.usc.csci572;

import java.io.*;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class Crawler {
	
	/**
	  *Takes directory path of TSV files as i/p
	  *Creates JSON files in the provided o/p directory
	  *
	  */
	public static void main(String args[]) throws IOException, SAXException, TikaException {
		String dirForJsonFiles,dirForXhtmlFiles,dirForTsvFiles;
		BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter directory path where TSV files are stored (eg: C://abc//pqr//tsv//): ");
		dirForTsvFiles = brr.readLine();
		System.out.print("Enter directory path for XHTML files (eg: C://abc//pqr//xhtml//): ");
		dirForXhtmlFiles = brr.readLine();
		System.out.print("Enter directory path for storing JSON files (eg: C://abc//pqr//json//): ");
		dirForJsonFiles = brr.readLine();
		if(new File(dirForJsonFiles).isDirectory() && new File(dirForTsvFiles).isDirectory() && new File(dirForXhtmlFiles).isDirectory()) {
			new TSVParser().crawlTSVParser(dirForTsvFiles, dirForXhtmlFiles);
			new FromXhtmlToJson().crawlXHTMLParser(dirForXhtmlFiles, dirForJsonFiles);
		}
		else {
			System.out.println("Not a valid Driectory");
		}
	}
}