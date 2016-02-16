Important Note: Please check the documentation(comments) in the java files or Report for more information

Instructions to execute different files
---------------------------------------
TSV Parser:
	I/P: TSV File(s) + (Directory path to TSV file(s) and XHTML file(s))
	O/P: XHTML File(s) at the specified directory

XHTML to Json:
	I/P: XHTMl File(s) + (Directory path to XHTML file(s) and JSON files)
	O/P: JSON File(s) at specified directory
	Special Libraries: HTML Cleaner

Deduplication:
	I/P: JSON Files + (Directory path to JSON files)
	O/P: Integer value indicating number of duplicates

Crawler:
	I/P: Directory path to TSV files, XHTML files and JSON files
	O/P: JSON job files at specified folder
	
ETLLib:
I] TSV to Json:
	Command Used: "tsvtojson"
	I/P: TSV File(s) + (Directory path to TSV file(s)) and colheader.txt
	O/P: JSON File(s)
II] Json to Individual Json Files
	Command Used: "retranslate" and Python code to verify the count of the individual json job files 
	I/P: JSON File(s)
	O/P: Individual JSON Job Files from each JSON File(s) 

Procedure:-

I] Content Extraction using Apache Tika
	1) Unzip the folder "CSCI572HW1" and import the unzipped project in any IDE (Eclipse Kepler).
	2) Now add the "tika-app-1.6" and "htmlcleaner-2.9" jar files to the project.
	3) For running on a single TSV file (Questions 1 and 2)
		A) Execute the TSVParser.java
			a) For running TSVParser.java, you can directly run it using the "run" in IDE or press "Ctrl+F11"
			b) The program will ask you to enter the directory path where you have your TSV file (Make sure you just have 1 TSV file in that folder)
			c) The program will also ask the location where Xhtml file needs to be saved
		B) Execute the FromXhtmlToJson.java 
			a) For running FromXhtmlToJson.java you can directly run it using the "run" in IDE or press "Ctrl+F11"
			b) The program will ask you to enter the directory path where you have your Xhtml file (Make sure you give the path in the previous step)
			c) The program will also ask the location where Json file needs to be saved
		C) Execute the Deduplication.java
			a) For running Deduplication.java you can directly run it using the "run" in IDE or press "Ctrl+F11"
			b) The program will ask you to enter the directory path where you have your JSON job files (1 json file per job)
	4) For running on the entire set of 430 files
		A) Execute the Crawler.java file
			a) For running Crawler.java you can directly run it using the "run" in IDE or press "Ctrl+F11"
			b) The program will ask you to enter the directory path where you have your TSV file (Make sure you just have all TSV files in that folder)
			c) The program will also ask the location where Xhtml file needs to be saved
			d) The program will also ask the location where Json file needs to be saved
		B) Execute the Deduplication.java
			a) For running Deduplication.java you can directly run it using the "run" in IDE or press "Ctrl+F11"
			b) The program will ask you to enter the directory path where you have your JSON job files (1 json file per job)

Note: The Deduplication.java file will output the number of JSON job file that are detected as duplicates

II] Content Extraction using Python ETLLib library
	1) Open the Terminal and set the env PATH to the etllib-master bin folder

	2) Run the following command to convert TSV files to JSON files:
for a in $(ls Directory_path_to_Input_TSV_files); do tsvtojson -t Directory_path_to_Input_TSV_files/${a%%.*}.tsv -j Directory_path_to_Output_JSON_files/${a%%.*}.json -c Directory_path_to_colheader.txt_File -o employmentjobs; done

	3) Run the following command to convert JSON files to Individual JSON Job files:
for ag in $(ls Directory_path_to_Output_JSON_files); do
repackage -v -j Directory_path_to_Output_JSON_files/${ag%%.*}.json -o employmentjobs
done

Note: The Individual JSON job files will be stored in the current working directory of the Terminal

	4) Execute the following Python Code to verify the count of the individual JSON Job files generated in step 3)
>>> import json
>>> import glob
>>> import os
>>> import os.path
>>> import fnmatch
>>> rootdir = 'Directory_path_to_Individual_JSON_Job_Files'
>>> count = 0
>>> for subdir, dirs, files in os.walk(rootdir):
...     for file in files:
...             filename = os.path.join(subdir, file)
...             if fnmatch.fnmatch(file, '*.json'):
...                     json_data = open(filename)
...                     parse_json = json.load(json_data)
...                     job_filles = parse_json['employmentjobs']
...                     len(job_files)
...                     count = count + len(job_files)
...             else:
...                     print "Not a JSON File"
...     print (count)

Note: This Python code crawls through the JSON Job files (having multiple jobs) and counts the number of jobs inside each JSON Job file using the "employmentjobs" array name used in Step 2) and Step 3) and also, gives the total count of the jobs