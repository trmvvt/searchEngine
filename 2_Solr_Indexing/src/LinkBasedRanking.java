package usc.csci572.hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LinkBasedRanking {
	
	static HashMap<String,Integer> hmLocation = new HashMap<String,Integer>();
	static HashMap<String, Integer> hmJobType = new HashMap<String, Integer>();
	static HashMap<String, Integer> hmCompany = new HashMap<String, Integer>();
	
	public static void createHashMaps(String fileName) throws FileNotFoundException {
			JobDTO jsonDetails = new JobDTO();
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			Gson gsonRead = new GsonBuilder().create();
			jsonDetails = gsonRead.fromJson(br, JobDTO.class);
			
			//Location HashMap
			int indexOfDot; 
			String lat ="";
			if (lat != "0") {
				indexOfDot = jsonDetails.getLatitude().indexOf(".");
				if(indexOfDot+4 <= lat.length()){
					lat = jsonDetails.getLatitude().substring(0, indexOfDot + 4);
				}else{
					lat = jsonDetails.getLatitude();
				}
			}else{
				lat = "0";
			}
			String lon ="";
			if (lon != "0") {
				indexOfDot = jsonDetails.getLongitude().indexOf(".");
				if(indexOfDot+4 <= lon.length()){
					lon = jsonDetails.getLongitude().substring(0, indexOfDot + 4);
				}else{
					lon = jsonDetails.getLongitude();
				}
			}else{
				lon = "0";
			}
			String locKey;
			locKey = "("+lat+","+lon+")"; 
			//System.out.println(locKey);
			if(hmLocation.containsKey(locKey)){
				hmLocation.put(locKey,hmLocation.get(locKey)+1);
			}else{
				hmLocation.put(locKey, 1);
			}
			
			//JobType HashMap
			String jobKey = jsonDetails.getJobtype();
			//System.out.println(jobKey);
			if(hmJobType.containsKey(jobKey)){
				hmJobType.put(jobKey,hmJobType.get(jobKey)+1);
			}else{
				hmJobType.put(jobKey, 1);
			}
			
			//Company HashMap
			String compKey = jsonDetails.getCompany();
			//System.out.println(compKey);
			if(hmCompany.containsKey(compKey)){
				hmCompany.put(compKey,hmCompany.get(compKey)+1);
			}else{
				hmCompany.put(compKey, 1);
			}
	}
	
	private static void generateLinkBasedScores(String fileName) throws IOException, ParseException {
		JobDTO jobData = new JobDTO();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Gson gsonRead = new GsonBuilder().create();
		jobData = gsonRead.fromJson(br, JobDTO.class);
		int linkScore = 0;
		int compScore = 0;
		int jobTypeScore =0;
		int locScore=0;
		String comp;
		String loc;
		String jobType;
		comp = jobData.getCompany();
		int indexOfDot; 
		String lat ="";
		if (lat != "0") {
			indexOfDot = jobData.getLatitude().indexOf(".");
			if(indexOfDot+4 <= lat.length()){
				lat = jobData.getLatitude().substring(0, indexOfDot + 4);
			}else{
				lat = jobData.getLatitude();
			}
		}else{
			lat = "0";
		}
		String lon ="";
		if (lon != "0") {
			indexOfDot = jobData.getLongitude().indexOf(".");
			if(indexOfDot+4 <= lon.length()){
				lon = jobData.getLongitude().substring(0, indexOfDot + 4);
			}else{
				lon = jobData.getLongitude();
			}
		}else{
			lon = "0";
		}
		loc = "("+lat+ "," + lon+")";
		jobType = jobData.getJobtype();
		
		if(comp !=null && comp!=""){
			compScore = hmCompany.get(comp);
		}

		if(loc!=null && loc!=""){
			locScore = hmLocation.get(loc);
		}

		if(jobType!=null && jobType!=""){
			jobTypeScore = hmJobType.get(jobType);
		}
		
		linkScore = compScore+locScore+jobTypeScore;
		
		System.out.println(linkScore);
		
		Date lastSeen = new SimpleDateFormat("yyyy-MM-dd").parse(jobData.getLastSeenDate());
		Date postedDate = new SimpleDateFormat("yyyy-MM-dd").parse(jobData.getPostedDate());
		long diff = lastSeen.getTime() - postedDate.getTime();
		int noOfDays ;
		noOfDays= (int) (diff/(1000*60*60*24));
		
		String[] locArray = jobData.getLocation2().split(",");
		String country;
		country = locArray[locArray.length-1];
		
		ArrayList<String> southAmerica = new ArrayList<String>();
		southAmerica.add("Argentina");
		southAmerica.add("Bolivia");
		southAmerica.add("Brazil");
		southAmerica.add("Chile");
		southAmerica.add("Columbia");
		southAmerica.add("Equador");
		southAmerica.add("Guina");
		southAmerica.add("Paraguay");
		southAmerica.add("Peru");
		southAmerica.add("Suriname");
		southAmerica.add("Uruguay");
		southAmerica.add("Venezuela");
		
		String isSA = "";
		
		if(southAmerica.contains(country.trim())){
			isSA = "true";
		}else{
			isSA = "false";
		}
		
		String text,text2;
		int index;
		 BufferedReader in = new BufferedReader(new FileReader(fileName)); 
		 while(in.ready())
			{
				text = in.readLine();
				index = text.indexOf('}');
				text2 = text.substring(0, index) + ",\"linkScore\":"+ linkScore + ",\"jobTypeScore\":"+ jobTypeScore + ",\"locScore\":"+ locScore +  ",\"compScore\":"+ compScore + ",\"noOfDays\":" + noOfDays+  ",\"isSA\":\"" + isSA +"\""+ text.substring(index);
				PrintWriter out = new PrintWriter(fileName);
				out.println(text2);
				out.close();
				break;
			}
		 in.close();
		
	}
		
	public static void printHashMap(HashMap<String,Integer> hmLocation){
		for(String key: hmLocation.keySet()){
			System.out.println(hmLocation.get(key));
		}
	}
	
	
	public static void main(String args[]) throws IOException, ParseException {
		String dirForJsonFiles;
		System.out.print("Enter directory path where JSON files are stored (eg: C://abc//pqr//tsv//): ");
		BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
		dirForJsonFiles = brr.readLine();
		//dirForJsonFiles = "C:\\Users\\RenuPriya\\USC\\Third Semester\\CSCI 572\\Homework 2\\Data - SmallSet\\Output";
		if(new File(dirForJsonFiles).isDirectory()){
			File folder = new File(dirForJsonFiles);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String jsonFileName = file.getName();
					System.out.println(jsonFileName);
					createHashMaps(dirForJsonFiles +"\\"+ jsonFileName);
				}
			}
			
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String jsonFileName = file.getName();
					generateLinkBasedScores(dirForJsonFiles +"\\"+ jsonFileName);
				}
			}
			
		}
	}

	
}
