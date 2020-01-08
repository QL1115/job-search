package com.ql.jobsearch.api;

import com.ql.jobsearch.pojo.Job;
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
//			if (weight != null ) {  /* 使用規定格式的關鍵字和權重進行搜尋 */

//			} else {    /* 一般搜尋 */
				keyword = keyword.replaceAll("\\s", ";");
				keyword = keyword.replaceAll(",", ";");
				System.out.println("jobs keyword>> " + keyword);
				searchEngineQuery.setSearchKeyword(keyword);
				List<Job> jobList = searchEngineQuery.queryJobList();
				model.addAttribute("jobList", jobList);
				model.addAttribute("keyword", keyword);
				//
				model.addAttribute("category", "職缺搜尋功能");
//			}
		} catch (IOException e) {
			return "error/500";   // 500 頁面
		}
		return "jobs";
	}

	@RequestMapping("/jobs/{id}")
	public String findJob(@PathVariable("id") String id, Model model) {
		try {
			Job job = searchEngineQuery.querySingleJob(id);
			model.addAttribute(job);
		} catch (IOException e) {
			return "error/500";   // 500 頁面
		}

		return "job";   // 到 job.html 頁面
	}

}
