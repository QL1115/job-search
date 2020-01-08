package com.ql.jobsearch.util;

import com.ql.jobsearch.pojo.Keyword;
import java.io.IOException;
import java.util.ArrayList;

/**
 * web 頁面
 */
public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;	//from HW3
	public double score;		// score is calculated from a given Keyword set
	
	public WebPage(String url,String name){
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);	
	}
	
	/**
	 * current webPage 的分數（不包含子頁面的）
	 * @param keywords
	 * @throws IOException
	 */
	public void setScore(ArrayList<Keyword> keywords) throws IOException{
		score = 0;
		for(Keyword k : keywords){			
			score += counter.countKeyword(k.getName())* k.getWeight();
		}
	}
	
}