package com.ql.jobsearch.api;

import com.ql.jobsearch.pojo.Job;
import com.ql.jobsearch.pojo.Keyword;
import com.ql.jobsearch.util.SearchEngineQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;


@Controller
public class JobsEndpoints {

	private SearchEngineQuery searchEngineQuery = new SearchEngineQuery();

	@RequestMapping("/jobs")
	public String findJobs(Model model,
	                       @RequestParam("keyword") String keyword,
	                       @RequestParam(value = "weight", required = false) String weight) {
		try {
			keyword = keyword.replaceAll("\\s", "+");
			keyword = keyword.replaceAll(",", "+");
			String[] keywordsArr = keyword.split("/+");
			searchEngineQuery.deleteAllKeywords();
			for (String keywordStr : keywordsArr) {
				searchEngineQuery.addKeyword(new Keyword(keywordStr));
			}
			/* General Search - 一般搜尋 */
			List<Job> jobList = searchEngineQuery.queryJobList();   // 查詢到的結果

//			/* Weight Search - 權重搜尋 */
			if (weight != null && weight.length() > 0) model.addAttribute("weight", weight);


//			searchEngineQuery.queryJobListWeight(jobList);  // 將用關鍵字查到的 jobList 利用權重重新查詢及排序
			model.addAttribute("jobList", jobList);
			model.addAttribute("keyword", keyword);
			//
			model.addAttribute("category", "職缺搜尋功能");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "jobs";
	}

	@RequestMapping("/jobs/{id}")
	public String findJob(@PathVariable("id") String id, Model model) {
		try {
			Job job = searchEngineQuery.querySingleJob(id);
			model.addAttribute(job);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "job";   // 到 job.html 頁面
	}

}
