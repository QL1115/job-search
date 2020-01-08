package com.ql.jobsearch.api;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ql.jobsearch.pojo.Job;
import com.ql.jobsearch.util.SearchEngineQuery;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用不同的搜尋引擎搜尋到的結果
 */
@RestController
public class SearchEngineEndpoints {

	private SearchEngineQuery searchEngineQuery = new SearchEngineQuery();

	@PostMapping("/search-engine")
	public List<Job> queryGooglePost(@RequestParam String keyword, @RequestParam String engine) {
		System.out.println("search engine keyword>> " + keyword);
		engine= engine.toLowerCase();
		List<Job> resultList = new ArrayList<>();
		searchEngineQuery.setSearchKeyword(keyword);
		try {
			switch (engine) {
				case "google":
					resultList = searchEngineQuery.queryGoogle();
					break;
				case "bing":
					resultList = searchEngineQuery.queryBing();
					break;
				case "yahoo":
					resultList = searchEngineQuery.queryYahoo();
					break;
				default:    // 如果用戶打的 engine param 不在上述三者，則使用 google 提供的職缺頁面
					resultList = searchEngineQuery.queryJobList();
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
//		model.addAttribute("resultList", resultList);
	}

}
