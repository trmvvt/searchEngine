package com.usc.csci572;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.SafeContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class JsonContentHandler extends SafeContentHandler {

	private final Metadata metadata;
	private boolean documentStarted = false;

	AttributesImpl attributes = new AttributesImpl();
	
	/**
	  *Constructor
	  */
	public JsonContentHandler(ContentHandler handler, Metadata metadata) {
		super(handler);
		this.metadata = metadata;
	}

	/**
	  *This method is used to start a JSON document
	  */
	public void startDocument() throws SAXException {
		documentStarted = true;
		super.startDocument();
	}

	/**
	  *This method is used to start a JSON element
	  */
	public void startJsonElement() throws SAXException {
		super.startElement("", "{", "{", attributes);
		super.characters("{".toCharArray(), 0,1);
	}

	/**
	  *This method is used to start a JSON attribute i.e. key-value pairs
	  */
	public void startJsonAttribute(String name, String value) throws SAXException {
		super.startElement("", name + ":", value + ",", attributes);
		super.characters(("\""+name+"\":").toCharArray(), 0, ("\""+name+"\":").length());
		super.characters(("\""+value+"\"").toCharArray(),0,("\""+value+"\"").length());
	}
	
	/**
	  *This method is used to start a JSON array 
	  *Not needed for this assignment since we are creating JSON file per record, 
	  *But if we want to create a single JSON for a TSV then this should be used
	  */
	public void startArray() throws SAXException {
		super.startElement("", "[", "", attributes);
		super.characters("\"JobFiles\" : [".toCharArray(), 0,"\"JobFiles\" : [".length());
	}

	/**
	  *This method is used to end a JSON Array 
	  */
	public void endArray() throws SAXException {
		super.endElement("", "]", "]");
		super.characters("]".toCharArray(), 0, 1);
	}

	/**
	  *This method is used to end a JSON Attribute 
	  */
	public void endJsonAttribute(boolean isLast) throws SAXException {
		if (!isLast) {
			super.endElement("", ",", ",");
			super.characters(",".toCharArray(), 0, 1);
		} else {
			super.endElement("", "", "");
			super.characters("".toCharArray(), 0, 0);
		}
	}

	/**
	  *This method is used to end a JSON Element 
	  */
	public void endJsonElement(boolean isLast) throws SAXException {
		if (!isLast) {
			super.endElement("", "},", "},");
			super.characters("},".toCharArray(), 0, 2);
		} else {
			super.endElement("", "}", "}");
			super.characters("}".toCharArray(), 0, 1);
		}
	}

	/**
	  *This method is used to end a JSON document
	  */
	public void endDocument() throws SAXException {
		documentStarted = false;
		super.endDocument();
	}

}
