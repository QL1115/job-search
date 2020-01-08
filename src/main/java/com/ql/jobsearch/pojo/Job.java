package com.ql.jobsearch.pojo;

import java.util.HashMap;

//@Component
public class Job {

	// Job ID: eg. 原 jdao62PLjnZnBdc9TKtxAAAAAA==,  現 id: 62PLjnZnBdc9TKtxAAAAAA
	private String id;

	// 職務名稱
	private String title;

	// 公司
	private String compName;

	// 工作內容
	private String desc;

	// 可能：一個月：xxx~xxx, 一年：xxx~xxx, ...
	private String salaries;

	// 上班地點
	private String location;

	// 工作來源網站
	private String source;

//	private String url;

	// 刊登日期：Google -> 幾天前
	private String postDate;




	public Job () { }

	public Job (String title, String desc, String source) {
		this.title = title;
		this.desc = desc;
		this.source = source;
	}

	public Job (String id, String title, String compName, String desc, String salaries,
	            String location, String source, String postDate) {
		this.id = id;
		this.title = title;
		this.compName = compName;
		this.desc = desc;
		this.salaries = salaries;
		this.location = location;
		this.source = source;
		this.postDate = postDate;
	}

//	public Job (String id, String title, String compName, String desc, String salaries,
//	            String location, String source, String url, String postDate) {
//		this.id = id;
//		this.title = title;
//		this.compName = compName;
//		this.desc = desc;
//		this.salaries = salaries;
//		this.location = location;
//		this.url = url;
//		this.source = source;
//		this.postDate = postDate;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSalaries() {
		return salaries;
	}

	public void setSalaries(String salaries) {
		this.salaries = salaries;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

//	@Override
//	public String toString() {
//		return "Job{" +
//				"id='" + id + '\'' +
//				", title='" + title + '\'' +
//				", compName='" + compName + '\'' +
//				", desc='" + desc + '\'' +
//				", salaries='" + salaries + '\'' +
//				", location='" + location + '\'' +
//				", source='" + source + '\'' +
//				", url='" + url + '\'' +
//				", postDate='" + postDate + '\'' +
//				'}';
//	}


	@Override
	public String toString() {
		return "Job{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", compName='" + compName + '\'' +
				", desc='" + desc + '\'' +
				", salaries='" + salaries + '\'' +
				", location='" + location + '\'' +
				", source='" + source + '\'' +
				", postDate='" + postDate + '\'' +
				'}';
	}
}
