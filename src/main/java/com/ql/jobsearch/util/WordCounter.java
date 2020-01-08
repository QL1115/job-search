package com.ql.jobsearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WordCounter {
	private String urlStr;
    private String content;
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
    }
    
    private String fetchContent() {

	    String retVal = "";
	    try {
		    URL url = new URL(this.urlStr);
		    URLConnection conn = url.openConnection();
		    InputStream in = conn.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));


		    String line = null;

		    while ((line = br.readLine()) != null) {
			    retVal = retVal + line + "\n";
		    }
	    } catch (MalformedURLException e) {
		    return retVal;
	    } catch (IOException e) {
		    return retVal;
	    }

	    return retVal;
    }
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
		}
		
		// a case-insensitive search, we turn the whole content and keyword into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
	
		return retVal;
    }
}