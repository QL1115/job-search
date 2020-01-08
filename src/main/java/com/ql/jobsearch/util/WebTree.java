package com.ql.jobsearch.util;

import com.ql.jobsearch.pojo.Keyword;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 整個 web tree
 */
public class WebTree {
	public WebNode root;
	
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}
	
	/**
	 * 左子樹、右子樹、根。根排在後面。
	 */
	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		setPostOrderScore(root, keywords);
	}

	/**
	 * 後序
	 */
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
		// 1. compute the score of children nodes
		for (WebNode webNode: startNode.children) {
			webNode.setNodeScore(keywords);
		}
		// 2. setNode score of startNode, 因為是 post order 所以 root 會是最後
		startNode.setNodeScore(keywords);
		
	}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));
		System.out.print("(");
		System.out.print(startNode.webPage.name+","+startNode.nodeScore);
		
		for(WebNode child : startNode.children){
			eularPrintTree(child);

		}
		System.out.print(")");
		if(startNode.isTheLastChild()) System.out.print("\n" + repeat("\t", nodeDepth-2));
		
	}
	
	private String repeat(String str,int repeat){
		String retVal  = "";
		for(int i=0;i<repeat;i++){
			retVal+=str;
		}
		return retVal;
	}
}