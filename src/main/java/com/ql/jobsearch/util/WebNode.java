package com.ql.jobsearch.util;

import com.ql.jobsearch.pojo.Keyword;
import java.io.IOException;
import java.util.ArrayList;

/**
 * web 節點
 */
public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;	// = webPage.score + all children’s nodeScore
	
	public WebNode(WebPage webPage){
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}
	
	/**
	 * 此方法會計算出此node全部的分數（包含 子node 的 web page 的分數）
	 * @param keywords
	 * @throws IOException
	 */
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException{
		// Done. this method should be called in post-order mode
		
		//1. compute current webPage score
		webPage.setScore(keywords);
		
		//2. set current webPage score to nodeScore
		nodeScore = webPage.score;
		
		//3. nodeScore += all childrens nodeScores
		for (WebNode webNode : children) {
			nodeScore += webNode.nodeScore;
		}
				
	}
	
	public void addChild(WebNode child){
		// Done. add the WebNode to its children list
		children.add(child);
	}
	
	public boolean isTheLastChild(){
		if(this.parent == null) return true;
		ArrayList<WebNode> siblings = this.parent.children;
		
		return this.equals(siblings.get(siblings.size() - 1));
	}
	
	public int getDepth(){
		int retVal = 1;
		WebNode currNode = this;
		while(currNode.parent!=null){
			retVal ++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}