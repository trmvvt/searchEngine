package com.usc.csci572;

import java.util.ArrayList;

public class JobDTO {

	//Columns that are used for deduplication
	private String Company;
	private String Department;
	private String Location2;
	private String Title;
	private String Url;
	private ArrayList<JobDTO> JobFiles; //Used if we have to create one JSON for one TSV file
	
	/**
	 * @return the company
	 */
	public String getCompany() {
		return Company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		Company = company;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return Department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		Department = department;
	}
	/**
	 * @return the location2
	 */
	public String getLocation2() {
		return Location2;
	}
	/**
	 * @param location2 the location2 to set
	 */
	public void setLocation2(String location2) {
		Location2 = location2;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}
	/**
	 * @return the jobFiles
	 */
	public ArrayList<JobDTO> getJobFiles() {
		return JobFiles;
	}
	/**
	 * @param jobFiles the jobFiles to set
	 */
	public void setJobFiles(ArrayList<JobDTO> jobFiles) {
		JobFiles = jobFiles;
	}	
}
