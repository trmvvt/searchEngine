package com.usc.csci572;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import util.JaroWinkler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Deduplication {

	JobDTO jobDTO;
	JobDTO jsonJobDTO;
	
	/**
	  *Takes directory path of JSON Job files
	  *Prints the no of duplicates
	  *
	  */
	public void findDuplicates(String dir) throws IOException, NoSuchAlgorithmException {
		BufferedReader br = null;
		BufferedReader brRead = null;
		BufferedWriter brDet = null;
		HashMap<String, Integer> hComp = new HashMap<String, Integer>();
		StringBuilder concatColumns = null;
		int fileCount = 0;
		int dupCount = 0;
		int prevCount =0;
		
		File folder = new File( dir);
		File[] listOfFiles = folder.listFiles();
		boolean flag = false;
		FileWriter fw = new FileWriter(new File(dir +"dummy.txt"));
		skip: for (File file : listOfFiles) {
			fileCount++;
			System.out.println("Reading File No " + fileCount);
			br = new BufferedReader(new FileReader(dir + file.getName()));
			System.out.println("Reading file "+file.getName());
			Gson gsonWrite = new GsonBuilder().create();
			try{
				jobDTO = gsonWrite.fromJson(br, JobDTO.class);
			}
			catch(Exception e) {
				continue skip;
			}
			finally{
				System.out.println("No. of duplicates " + dupCount);
			}
			//If JSON object is not empty
			if (jobDTO != null) {
				//if we already read the json file with the same company
				if (hComp.containsKey(jobDTO.getCompany())) {
					System.out.println("Dummy1");
					brRead = new BufferedReader(new FileReader(dir +"dummy.txt"));
					String str = "";
					//Iterate through the file
					prevCount = dupCount;
					while ((str = brRead.readLine()) != null) {
						String[] values = str.split("\t");
						if (values.length > 1) {
							if(checkDups(values, jobDTO)){
								dupCount++;
							}							
						}
					}
					if(prevCount == dupCount){
						flag = true;
					}
					System.out.println("No. of duplicates " + dupCount);
					brRead.close();
				} else {
					hComp.put(jobDTO.getCompany(), 0);
					concatColumns = new StringBuilder();
					concatColumns.append(jobDTO.getCompany() + "\t")
							.append(jobDTO.getDepartment() + "\t")
							.append(jobDTO.getTitle() + "\t")
							.append(jobDTO.getUrl() + "\t")
							.append(jobDTO.getLocation2());
					System.out.println("Dummy2");
					brDet = new BufferedWriter(new FileWriter(dir+"dumy.txt",true));
					brDet.append("\n");
					brDet.append(concatColumns.toString());
					brDet.close();
				}
				
				if(flag == true){
					hComp.put(jobDTO.getCompany(), 0);
					concatColumns = new StringBuilder();
					concatColumns.append(jobDTO.getCompany() + "\t")
							.append(jobDTO.getDepartment() + "\t")
							.append(jobDTO.getTitle() + "\t")
							.append(jobDTO.getUrl() + "\t")
							.append(jobDTO.getLocation2());
					System.out.println("Dummy3");
					brDet = new BufferedWriter(new FileWriter(dir+"dummy.txt",true));
					brDet.append("\n");
					brDet.append(concatColumns.toString());
					brDet.close();
					flag = false;
				}
			}
		}
	}
	
	/**
	  *Takes the readLine values from dummy.txt i.e. the job files that are already processed and the current job file java JSON object
	  *If the company name matches then we will check whether the URLs are matching. If Yes, then return true(they are duplicates)
	  *If the URLs don't match then we will check for Department Name match. If Yes, then we will check whether job titles match
	  *If titles are matching then we will check for the locations match.
	  *If the string similarity score between locations is >= 0.8 then they are duplicates, we return true
	  *Returns false if any of this fails
	  *(CompanyNames - Normal String equality),(Department Names - Normal String equality),(Title - Normal String equality), (Locations - JaroWinkler Similarity(from code.google.com))
	  *
	  */
	public boolean checkDups(String[] values, JobDTO jsonJobDTO){
		if (values[0].equalsIgnoreCase(jsonJobDTO .getCompany())) {
			if (jsonJobDTO.getUrl() != null && values[3] != null) {
				if (values[3].equalsIgnoreCase(jsonJobDTO .getUrl())) {
					return true;
				}
			} else if (values[1].equalsIgnoreCase(jsonJobDTO.getDepartment())) {
				if (values[2].equalsIgnoreCase(jsonJobDTO.getTitle())) {
					if (jsonJobDTO.getLocation2() != null && values[4] != null) {
						System.out.println("Jaro Score: "+ JaroWinkler.similarity(jsonJobDTO.getLocation2(),values[4]));
						if (JaroWinkler.similarity(jsonJobDTO.getLocation2(),values[4]) >= 0.8) {
							return true;
						}
					} else if (jsonJobDTO.getLocation2() == null && values[4] == null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter directory path where JSON files are located (eg: C://abc//pqr//json//): ");
			String dir = brr.readLine();
			Deduplication dedup = new Deduplication();
			dedup.findDuplicates(dir);
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
	}

}
