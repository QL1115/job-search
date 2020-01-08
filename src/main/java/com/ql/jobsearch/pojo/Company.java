package com.ql.jobsearch.pojo;

import java.util.*;

// @Component
public class Company {

	// 公司名稱
	private String compName;

	// 公司介紹：eg. 人數、職位...
	private Map<String, String> intros;

	// 公司所有的工作刊登
	private List<Job> jobs;

	public Company() {
		compName = "";
		intros = new HashMap<>();
		jobs = new LinkedList<>();
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Map<String, String> getIntros() {
		return Collections.unmodifiableMap(intros);
	}

//	public void setIntros(Map<String, String> intros) {
//		this.intros = intros;
//	}

	public void setIntro (String key, String value) {
		intros.put(key, value);
	}


	public List<Job> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void setJobs(LinkedList<Job> jobs) {
		this.jobs = jobs;
	}
}
