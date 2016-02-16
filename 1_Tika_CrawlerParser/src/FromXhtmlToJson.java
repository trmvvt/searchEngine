package com.usc.csci572;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.config.ServiceLoader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.WriteOutContentHandler;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class FromXhtmlToJson extends AbstractParser {
	public static String dirForXhtmlFiles;
	public static String dirForJsonFiles;
	private static String jsonFileName;

	private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.text("application+xhtml"));

	private static final ServiceLoader LOADER = new ServiceLoader(TSVParser.class.getClassLoader());

	@Override
	public Set<MediaType> getSupportedTypes(ParseContext arg0) {
		return SUPPORTED_TYPES;
	}

	/**
	  *Overrides parse method in Abstract Parser 
	  *Takes InputStream, ContentHandler, Metadata and ParseContext as input
	  *Writes an xhtml document i.e. gives an XHTML document as output
	  *
	  */
	@Override
	public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context) throws IOException, SAXException, TikaException {
		HtmlCleaner cleaner = new HtmlCleaner();

		String[] header = {"Posted Date", "Location", "Department",
				"title", "Salary", "Start", "Duration", "Job Type",
				"Applications", "Company", "Contact Person", "Phone No",
				"Fax No", "Location", "Latitude", "Longitude",
				"First Seen Date", "Url", "Last Seen Date" };
		
		TagNode node = cleaner.clean(new File(dirForXhtmlFiles+jsonFileName+".xhtml"));
		node = node.getChildTags()[1].getChildTags()[0];
		int loopSize = node.getChildTags()[0].getChildTags().length;
		System.out.println("No of records in table of XHTML file : " + loopSize);
		int count=0;
		JsonContentHandler json;
		TagNode trNode;
		for(int i=0;i<loopSize;i++) {
			trNode = node.getChildTags()[0].getChildTags()[i];
			FileWriter fw = new FileWriter(new File(dirForJsonFiles+jsonFileName+"-"+count+".json"));
			BufferedWriter bw = new BufferedWriter(fw);
			handler = new WriteOutContentHandler(-1);
			json = new JsonContentHandler(handler, metadata);
			json.startDocument();
			json.startJsonElement();
			for(int j=0;j<trNode.getChildTags().length;j++) {
				json.startJsonAttribute(header[j], trNode.getChildTags()[j].getText().toString());
				if(j==trNode.getChildTags().length-1){
					json.endJsonAttribute(true);
				}else{
					json.endJsonAttribute(false);
				}
			}
			json.endJsonElement(true);
			json.endDocument();
			bw.write(json.toString());
			bw.close();
			json = null;
			count++;
		}
	}
	
	/**
	  *Takes directory path of TSV files and directory path of where XHTML should be stored as input
	  *Creates JSON files in the provided o/p directory
	  *
	  */
	public void crawlXHTMLParser(String XhtmlFilesDir, String JsonFilesDir) throws IOException, SAXException, TikaException {
		dirForXhtmlFiles = XhtmlFilesDir;
		dirForJsonFiles = JsonFilesDir;
		File folder = new File(dirForXhtmlFiles);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				jsonFileName = file.getName();
				jsonFileName = jsonFileName.substring(0, jsonFileName.length() - 6);
				InputStream stream = new FileInputStream(dirForXhtmlFiles + file.getName());
				WriteOutContentHandler handler = new WriteOutContentHandler(-1);
				Metadata metadata = new Metadata();
				FromXhtmlToJson p = new FromXhtmlToJson();
				p.parse(stream, handler, metadata, new ParseContext());
			}
		}
	}

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		BufferedReader brr = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.print("Enter directory path for XHTML files (eg: C://abc//pqr//xhtml//): ");
		dirForXhtmlFiles = brr.readLine();
		System.out.print("Enter directory path for storing JSON files (eg: C://abc//pqr//json//): ");
		dirForJsonFiles = brr.readLine();
		File folder = new File(dirForXhtmlFiles);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				jsonFileName = file.getName();
				jsonFileName = jsonFileName.substring(0,jsonFileName.length() - 6);
				InputStream stream = new FileInputStream(dirForXhtmlFiles+ file.getName());
				WriteOutContentHandler handler = new WriteOutContentHandler(-1);
				Metadata metadata = new Metadata();
				FromXhtmlToJson p = new FromXhtmlToJson();
				p.parse(stream, handler, metadata, new ParseContext());
			}
		}
	}
}
