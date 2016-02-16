
Information Retrieval and Web Search Engines:
Building a local search engine with Apache Tika, Solr and D3.

Here we create a local search engine of the Employment postings by cleansing, transforming, and developing an algorithm for ranking the job postings.
The dataset consists of ~119 million jobs and is currently ~40GB in size.

Tika Parser:  Get the employment dataset into individual JSON records (“files”) for searching via XHTML output using its ContentHandler framework. 	Develop and run a simple crawler that uses Tika across the dataset of Employment jobs to produce the individual JSON job files. Develop a process for deduplicating the Employment dataset.
Solr Indexing: Take JSON files and build an indexing system to send the data into Apache Solr and to create an Employment job search engine. Develop a content based, and a link based ranking algorithm to return back the appropriate sets of employment jobs based on user queries. The indexing system will be constructed again using the Apache OODT (http://oodt.apache.org/) large-scale data processing system. Apache OODT is particularly effective in dealing with file-based retrieval, processing and management. Combine OODT with ETLlib and its tooling to get the JSON data files loaded into Solr, and then extend Solr to support your new ranking algorithms.
Visualize Search Results: Leverage the Apache Solr index and the D3.js data visualization technology to interact and visualize the search engine data. To connect D3.js to your Solr index, we need to leverage Solr's XSLT-based transformer writer, and/or to create a specific  type of data loader in D3.js in order to load the employment dataset and its information into D3. 




