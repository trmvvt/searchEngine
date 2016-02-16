Read Me
-------

Installing Solr:
----------------

Requirements:
-------------
    1. Java 1.7 or greater. Some places you can get it are from Oracle, Open JDK, or IBM.
        Running java -version at the command line should indicate a version number starting with 1.7.
        Gnu's GCJ is not supported and does not work with Solr.
   2.  A Solr release. - We have installed 4.10.1 version on Jetty. It has been updated to 4.10.2 two days back.
   
Once downloaded, 
1. Unzip the file
2. cd to example folder
3. Type the below command to start solrl
	java -jar start.jar
4.This will start up the Jetty application server on port 8983, and use your terminal to display the logging information from Solr.
5. You can see that the Solr is running by loading http://localhost:8983/solr/ in your web browser.
6. You can upload a file which is already present in the exampledocs folder of solr
	java -jar post.jar solr.xml monitor.xml
	This is a syntax to add an xml to solr.

Add JSON file to solr:
---------------------
1. This is different from adding xml. 
2. We can an example JSON which is already present in the exampledocs
3. We have to install curl for the same
4. cd example/exampledocs
   curl 'http://localhost:8983/solr/update/json?commit=true' --data-binary @books.json -H 'Content-type:application/json'

Installing cURL:
----------------

Windows:
1.Install cURL from http://curl.haxx.se/latest.cgi?curl=win64-ssl-sspi for Windows 64
2. You will get a curl.exe file
3 Change the path variable - add the path to curl.exe
4. Now u will be able to use cURL from cmd line.
	check whether curl is working or not
	curl -v http://www.google.com
	If you get the source of google.com then curl is working
   Now cd to example docs folder
   curl http://localhost:8983/solr/update/json?commit=true --data-binary @books.json -H "Content-type:application/json"
   This command will upload the JSON to Solr and indexes it
5. You can test using a query
	http://localhost:8983/solr/select?q=name:monsters&wt=json&indent=true
	
Ubuntu:

sudo apt-get install curl

Installing OODT:
----------------

prompt> curl -s http://svn.apache.org/repos/asf/oodt/trunk/mvn/archetypes/radix/src/main/resources/bin/radix | bash
prompt> mv oodt oodt-src; cd oodt-src; mvn package
prompt> mkdir ../oodt; tar -xvf distribution/target/oodt-distribution-0.1-bin.tar.gz -C ../oodt
prompt> cd ../oodt; ./bin/oodt start
prompt> ./resmgr/bin/batch_stub 2001

OODT:
----
Please find below the instructions to start different components in OODT

To start OODT:
cd oodt/bin
./oodt start
To stop:
./oodt stop

To start Filemgr:
cd oodt/filemgr/bin
./filemgr start
STop:
./filemgr stop

To start Workflow Manager:
cd oodt/workflow/bin
./wmgr start
To stop:
./wmgr stop

To start resmgr:
cd oodt/resmgr/bin
./resmgr start
To stop:
./resmgr stop

To start Batch stub:
cd oodt/resmgr/bin
./batch_stub 2001

To check if all managers are running as expected, we can use the http://localhost:8080/opsui as stated above 
or simple try using these links in browser

    http://localhost:9000/      (for FileManager)
    http://localhost:9001/      (for WorkflowManager)
    http://localhost:9002/      (for ResourceManager)

Ports:
------
crawler_port=9020
filemanager_port=9000
workflowmanager_port=9001
resmgr_port=9002
batchstub_port=2001

Setting up the OODT environment:
	We followed the steps described in https://cwiki.apache.org/confluence/display/OODT/CAS-PGE+Learn+by+Example web resourse.

ETlLib:
	We used the POSTER command from the ETLlib package in the homework.
	Command used to post the files on Apache Solr: Poster

We executed the following script to post the files on the Apache Solr and this script was called from the PGEConfig.xml file of the OODT’s File Concatenator:

#!/bin/sh

for ag in $(ls /users/nehaahuja/Desktop/Data2); do
	echo "/users/nehaahuja/Desktop/Data2/$ag" | poster -u "http://localhost:8983/solr/collection1/update/json?commit=true" -v
done


JAVA Programs:

1. QueryProgram.java
	This program display results to the four queries in an interactive format. This is a simple java program that can either be run from Eclipse or command line. 
	For, command line-
		javac QueryProgram.java
		java QueryProgram

	For, Eclipse
		Just use the run command directly from the IDE

	The program will ask you to enter the query number and display the top five results for that query in a plain text format.

2. LinkBasedRanking.java
	This program is used to generate the links or realtionship between documents. Run this program by importing the source code in the Eclipse IDE and then running it directly using run button. This program will ask you to enter the folder path where the JSON files are stored.
	We have used gson-2.2.4.jar file for the program. You will need to attach it in your Eclipse project for working.



