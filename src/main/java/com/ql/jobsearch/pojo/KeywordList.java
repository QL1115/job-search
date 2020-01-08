package com.ql.jobsearch.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeywordList {
	private List<Keyword> list;

	public KeywordList() {
		this.list = new ArrayList<>();
	}

	public void addKeyword(Keyword k) {
		list.add(k);
	}


	/**
	 * 得到 keywords 類似: java+python
	 * @return
	 */
	public String getKeywordsQueryParam() {
		if (list.size() > 1) {
			StringBuilder builder = new StringBuilder();
			for (Keyword keyword : list) {
				builder.append(keyword.getName()).append("+");
			}
			if (builder.toString().length() > 0) builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} else {
			return list.get(0).getName();
		}
	}

	public ArrayList<Keyword> getKeywordList() {
		return (ArrayList<Keyword>) list;
	}

	public String getWeightsQueryParam() {
		if (list.size() > 1 ) {
			StringBuilder builder = new StringBuilder();
			for (Keyword keyword : list) {
				if (keyword.getWeight() > 0) builder.append(keyword.getName()).append("+");
			}
			if (builder.toString().length() > 0) builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} else {
			return "";
		}
	}

}
