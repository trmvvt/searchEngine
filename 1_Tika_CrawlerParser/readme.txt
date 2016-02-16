1. Develop a Tika TSVParser that will take in a TSVFile and create structured XHTML output, recognizing the column headers, and table row values.
2. Develop a JSONTableContentHandler that will take the XHTML output from the TSVParser (or any upstream parser) and then output JSON files corresponding to the XHTML table rows (one file per row).
3. Develop and run a simple crawler that uses Tika across the initial reduced dataset of Employment jobs to produce the individual JSON job files 
4. Develop a process for deduplicating the Employment dataset.





