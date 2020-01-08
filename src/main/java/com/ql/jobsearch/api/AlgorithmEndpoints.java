package com.ql.jobsearch.api;

import com.ql.jobsearch.pojo.Keyword;
import com.ql.jobsearch.util.KeywordLcsMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AlgorithmEndpoints {

	/**
	 * LCS 比對 關鍵字
	 * @param keyword
	 * @return 回傳符合的關鍵字列表（排序）
	 */
	@PostMapping("/keyword/lcs")
	public List<String> keywordList(@RequestParam("keyword") String keyword) throws IOException {  // TODO 不確定這裡直接處理異常還是拋出比較好
		// LCS
		List<Keyword> sortedPossibleKeywordList = new KeywordLcsMatcher().findPossibleKeywords(keyword);
		// 只取得 keyword 的 name
		List<String> result = new ArrayList<>();
		int i = 0;
		while (i < sortedPossibleKeywordList.size()) {
			if (i >= 10) break;  // 只顯示前面幾筆推薦
			result.add(sortedPossibleKeywordList.get(i).getName());
			i++;
		}
		return result;
	}



}
