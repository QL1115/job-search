package com.ql.jobsearch.api;


import com.ql.jobsearch.pojo.Job;
import com.ql.jobsearch.pojo.Keyword;
import com.ql.jobsearch.util.SearchEngineQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Job> queryGooglePost(@RequestParam("keyword") String keyword,
	                                 @RequestParam("engine") String engine,
	                                 @RequestParam(value = "weight", required = false) String weight) {
		engine= engine.toLowerCase();
		List<Job> list = new ArrayList<>();
		try {
			String[] keywordsArr = keyword.split("\\+");
			searchEngineQuery.deleteAllKeywords();
			for (String keywordStr : keywordsArr) {
				searchEngineQuery.addKeyword(new Keyword(keywordStr));
			}

			/* General Search - 一般查詢 */
			switch (engine) {
				case "google":
					list = searchEngineQuery.queryGoogle();
					break;
				case "bing":
					list = searchEngineQuery.queryBing();
					break;
				case "yahoo":
					list = searchEngineQuery.queryYahoo();
					break;
				default:    // 如果用戶打的 engine param 不在上述三者，則使用 google 提供的職缺頁面
					list = searchEngineQuery.queryJobList();
					break;
			}

			/* Weight Search - 權重查詢 */
			System.out.println("weight: " + weight);
			if (weight != null) {
				System.out.println("weight: " + weight);
				String[] weightsArr = weight.split(",");
				if (keywordsArr.length == weightsArr.length) {
					// TODO 之後還要處理這裡的 double 的轉換 以及 new SearchEngineQuery() 的問題
					searchEngineQuery.deleteAllKeywords();
					for (int i = 0; i<keywordsArr.length; i++) {
						searchEngineQuery.addKeyword(new Keyword(keywordsArr[i], Double.parseDouble(weightsArr[i])));
					}
				}
				// 將用關鍵字查到的 jobList 利用權重重新查詢及排序
				list = searchEngineQuery.queryJobListWeight(list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
