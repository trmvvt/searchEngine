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
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.CloseShieldInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.WriteOutContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.xpath.XPathParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TSVParser extends AbstractParser {
	private static final ServiceLoader LOADER = new ServiceLoader(TSVParser.class.getClassLoader());
	private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.text("application+xhtml"));
	private static String xhtmlFileName;
	public static String dirForTSVFiles;
	public static String dirForXhtmlFiles;
	
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

		AutoDetectReader reader = new AutoDetectReader(new CloseShieldInputStream(stream), metadata, context.get(ServiceLoader.class, LOADER));
		String str;
		String[] header = {"Posted Date", "Location", "Department", "Title","",
				"Salary", "Start", "Duration", "Job Type", "Applications",
				"Company", "Contact Person", "Phone No", "Fax No", "Location2",
				"Latitude", "Longitude", "First Seen Date", "Url",
				"Last Seen Date" };
		XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
		
		xhtml.startDocument();
		xhtml.characters("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\" \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">");
		xhtml.characters("<html>");
		xhtml.startElement("body");
		xhtml.characters("<body>");
		xhtml.startElement("table");
		xhtml.characters("<table>");	
		while ((str = reader.readLine()) != null) {
			String[] values = str.split("\t");
			xhtml.startElement("tr");
			xhtml.characters("<tr>");
			for (int i = 0; i < values.length; i++) {
				if(i!=4 ){
					xhtml.startElement("td", "class", header[i]);
					xhtml.characters("<td class=\""+header[i]+"\">");
					xhtml.characters(values[i]);
					xhtml.characters("</td>");
				}
			}
			xhtml.endElement("tr");
			xhtml.characters("</tr>");
		}
		xhtml.endElement("table");
		xhtml.characters("</table>");
		xhtml.endElement("body");
		xhtml.endElement("html");
		xhtml.characters("</html>");
		xhtml.endDocument();
		xhtmlFileName = xhtmlFileName.substring(0, xhtmlFileName.length()-4);
		FileWriter fw = new FileWriter(new File(dirForXhtmlFiles+xhtmlFileName+".xhtml"));
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(xhtml.toString());
		bw.close();
		reader.close();
	}
	
	/**
	  *Takes directory path of TSV files and directory path of where XHTML should be stored as input
	  *Creates JSON files in the provided o/p directory
	  *
	  */
	public void crawlTSVParser(String TSVFilesDir, String XhtmlFilesDir) throws IOException, SAXException, TikaException {
		dirForTSVFiles = TSVFilesDir;
		dirForXhtmlFiles = XhtmlFilesDir;
		File folder = new File(dirForTSVFiles);
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles) {
			if(file.isFile()) {
				xhtmlFileName=file.getName();
				InputStream stream = new FileInputStream(dirForTSVFiles + file.getName());
				WriteOutContentHandler handler = new WriteOutContentHandler(-1);
				Metadata metadata = new Metadata();
				TSVParser p = new TSVParser();
				//Calling Parse method
				p.parse(stream, handler, metadata, new ParseContext());
			}
		}
	}
	
	public static void main(String[] args) throws IOException, SAXException, TikaException {
		BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter directory path for TSV files (eg: C://abc//pqr//tsv//): ");
		dirForTSVFiles = brr.readLine();
		System.out.print("Enter directory path for storing XHTML files (eg: C://abc//pqr//xhtml//): ");
		dirForXhtmlFiles = brr.readLine();
		File folder = new File(dirForTSVFiles);
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles) {
			if(file.isFile()) {
				xhtmlFileName=file.getName();
				InputStream stream = new FileInputStream(dirForTSVFiles + file.getName());
				WriteOutContentHandler handler = new WriteOutContentHandler(-1);
				Metadata metadata = new Metadata();
				TSVParser p = new TSVParser();
				p.parse(stream, handler, metadata, new ParseContext());
			}
		}
	}

}