package com.ql.jobsearch.util;

import com.ql.jobsearch.pojo.Keyword;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KeywordLcsMatcher {

	private static final String KEYWORDS_FILE_NAME = "job-title.txt";
	private static List<Keyword> keywordList;
	private List<Keyword> possibleKeywordList;

	static {
		keywordList = new ArrayList<>(); // 使用 array list， 因為之後的操作多讀取
		try {
			loadAllKeywordsFile();
			System.out.println("all keywords: " + keywordList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public KeywordLcsMatcher () { }

	/**
	 * 從 keywordList 挑出可能符合的 keyword， 並且進行排序，最後回傳相關關鍵字給使用者
	 * @param str 使用者查詢的關鍵字
	 * @return sorted and related keyword list
	 */
	public List<Keyword> findPossibleKeywords(String str) {
		possibleKeywordList = new LinkedList<>();    // 使用 linked list，因為之後的操作多增刪
		int maxValue = -1;	// 最長長度 ？
		int maxIndex = -1;	// 最大索引值
		for(int i = 0; i < keywordList.size(); i++){

			int lcs = findLCS(keywordList.get(i).getName(), str);
//			System.out.println(lcs);	// lcs 是比對成功的長度嗎？
			if(lcs > maxValue){
				maxValue = lcs;
				maxIndex = i;
			}
			if(lcs > 0) {
				Keyword keyword = new Keyword(keywordList.get(i).getName(), lcs);
				possibleKeywordList.add(keyword);
			}
		}
		// sort possibleKeywordList
		if (possibleKeywordList.size() > 1) {
			sort();
		}
		return possibleKeywordList;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * LCS algorithm
	 * @param x String 1
	 * @param y String 2
	 * @return 比對成功的長度？
	 */
	private int findLCS(String x, String y){
		int m = x.length();
		int n = y.length();
		// return if we have reached the end of either sequence
		if (m == 0 || n == 0) {
			return 0;
		}
		// if last character of X and Y matches
		if (x.charAt(m - 1) == y.charAt(n - 1)) {
			return findLCS(x.substring(0, x.length() - 1), y.substring(0, y.length() - 1)) + 1;
		}
		// else if last character of X and Y don't match
		return Integer.max(
				findLCS(x, y.substring(0, y.length() - 1)),
				findLCS(x.substring(0, x.length() - 1), y));
	}

	/**
	 * 載入 job-title.txt 文件 並且存入 keywordList 中
	 * @throws IOException
	 */
	private static void loadAllKeywordsFile() throws IOException {
		Resource res = new ClassPathResource("static/" + KEYWORDS_FILE_NAME);
		readFile(res.getFile());
	}

	private static void readFile(File file) {
		if(file.length()!=0) {
			BufferedReader bfReader = null;
			try {
				InputStream in = new FileInputStream(file);
				bfReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String inString = "";
				Keyword keyword = null;
				while((inString = bfReader.readLine())!= null){ // 一次讀取一行，一行剛好是一個keyword
					keyword = new Keyword(inString);
					keywordList.add(keyword);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					bfReader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	//////////////////////
	/**
	 * quick sort
	 */
	private void sort(){
		quickSort(0, possibleKeywordList.size()-1);
	}

	private void quickSort(int leftbound, int rightbound){
		//implement quickSort algorithm
		Keyword pivot = possibleKeywordList.get(leftbound);
		int i = leftbound;
		int j = rightbound;
		int k = -1;                    // 從 index k 切割 lst
		while (i < j) {
			// 左右 index 未到同一個位置，並且 index i 的 value >= pivot 的 value 時，合格 =》繼續檢查下一個
			while (i < j && possibleKeywordList.get(i).getCount() >= pivot.getCount()) i++;
			// 左右 index 未到同一個位置，並且 index j 的 value <= pivot 的 value 時，合格 =》繼續檢查下一個
			while (i < j && possibleKeywordList.get(j).getCount() <= pivot.getCount()) j--;
			//
			if (i < j) {            // i 位置的 count < j 位置的 count =》 不符合規則, 所以要交換
				swap(possibleKeywordList, i, j);
			} else if (i == j) {    // i 和 j 在同一位置代表已經比對到最後一個了
				// index i==j 的 value 大於 最左邊(pivot)的 value 時不符合規則，i、pivot 的 value 交換
				if (possibleKeywordList.get(i).getCount() > pivot.getCount()) {
					swap(possibleKeywordList, i, leftbound);
					k = i;
				} else { // index i==j 的 value 小於等於 最左邊(pivot)的 value 時
					swap(possibleKeywordList, i - 1, leftbound);
					k = i - 1;

				}
			}
		}
		// 遞迴: cut lst 並且 sort sub lst
		if (k > -1) {
			if (k > 1) quickSort(leftbound, k - 1);
			if (k + 1 < possibleKeywordList.size()) quickSort(k + 1, rightbound);
		}
	}

	/**
	 * 交換 list 當中兩個 index 的 value
	 */
	private void swap(List<Keyword> list, int aIndex, int bIndex){
		Keyword temp = list.get(aIndex);
		list.set(aIndex, list.get(bIndex));
		list.set(bIndex, temp);
	}
}
