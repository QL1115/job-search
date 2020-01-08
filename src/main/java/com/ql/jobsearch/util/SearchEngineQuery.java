package com.ql.jobsearch.util;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ql.jobsearch.pojo.Job;
import com.ql.jobsearch.pojo.Keyword;
import com.ql.jobsearch.pojo.KeywordList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SearchEngineQuery {

	private String searchKeyword = "職缺";
	private KeywordList keywordList;
	private String content;
	private List<Job> resultList;

	public SearchEngineQuery(){
		keywordList = new KeywordList();
	}


	public void addKeyword(Keyword keyword) {
//		this.searchKeyword = searchKeyword;
		keywordList.addKeyword(keyword);
	}

	public void deleteAllKeywords() {
		keywordList = new KeywordList();
	}




	private String fetchContent(String url)  {
		String retVal = "";
		try {
			System.out.println("fetch url: " + url);
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
//		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");	// 偽裝成瀏覽器偽裝得不夠
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			InputStream in = conn.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in, StandardCharsets.UTF_8);
			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;
			while ((line = bufReader.readLine()) != null) {
				retVal += line;
			}
		} catch (IOException e) {
			return retVal;
		}
		return retVal;
	}
	///////////////////////////////// 不同搜尋引擎的 POST

	// String id, String title, String compName, String desc, String salaries,
	// String location, String source, String postDate

//	String title, String desc, String source

	/**
	 * Google
	 */
	public List<Job> queryGoogle() throws IOException {

		String weightsQuery = keywordList.getWeightsQueryParam().equals("") ? "" : "weight=" + keywordList.getWeightsQueryParam();
		String url = "https://www.google.com/search?q=" + keywordList.getKeywordsQueryParam() + "&" + weightsQuery;
		content = fetchContent(url);
		resultList = new ArrayList<>();
		Document doc = Jsoup.parseBodyFragment(content);
		Element body = doc.body();
		Elements postsEle = body.select("div[class=srg]").select("div[class=g]");
		Job job = null;
		int i = 0;
		for (Element postEle : postsEle) {
			if (i >= 10) break;
			String title = postEle.select("h3[class=LC20lb]").first().text();
			String source = postEle.select("a").attr("href");
			String desc = postEle.select("div[class=rc]").select("div[class=s]").select("div").first().text();
			job = new Job(title, desc, source);
			resultList.add(job);
			i++;
		}
		return resultList;
	}

	/**
	 * Bing
	 */
	public List<Job> queryBing() throws IOException {
		String weightsQuery = keywordList.getWeightsQueryParam().equals("") ? "" : "&weight=" + keywordList.getWeightsQueryParam();
		content = fetchContent("https://www.bing.com/search?q=" + keywordList.getKeywordsQueryParam() + "&" + weightsQuery);

		resultList = new ArrayList<>();
		Document doc = Jsoup.parse(content);
		doc.outputSettings().charset(StandardCharsets.UTF_8);
		Element body = doc.body();
		Element olEle = body.select("ol[id=b_results]").first();
		Elements postsEle = olEle.select("li[class=b_algo]");
		Job job = null;
		int i = 0;
		for (Element postEle : postsEle) {
			if (i >= 10) break;
			String title = postEle.select("h2").select("a").text();
			String source = postEle.select("h2").select("a").attr("href");
			String desc = postEle.select("div[class=b_caption]").select("p").text();
			job = new Job(title, desc, source);
			resultList.add(job);
			i++;
		}
		return resultList;
	}

	/**
	 * Yahoo
	 */
	public List<Job> queryYahoo() throws IOException {
		String weightsQuery = keywordList.getWeightsQueryParam().equals("") ? "" : "&weight=" + keywordList.getWeightsQueryParam();
		content = fetchContent("https://tw.search.yahoo.com/search;_ylt=AwrtFncQDhNe03gAqBBq1gt.;_ylc=X1MDMjExNDcwNTAwMgRfcgMyBGZyAwRncHJpZAMEbl9yc2x0AzAEbl9zdWdnAzAEb3JpZ2luA3R3LnNlYXJjaC55YWhvby5jb20EcG9zAzAEcHFzdHIDBHBxc3RybAMEcXN0cmwDMTgEcXVlcnkDJUU4JTgxJUI3JUU3JUJDJUJBBHRfc3RtcAMxNTc4MzA3MDk2?fr2=sb-top-tw.search&p=" + keywordList.getKeywordsQueryParam()  + "&" + weightsQuery  + "&fr=sfp&iscqry=");
		resultList = new ArrayList<>();
		Document doc = Jsoup.parseBodyFragment(content);
		Element body = doc.body();
		Element olEle = body.select("ol[class=mb-15 reg searchCenterMiddle]").first();
		Elements postsEle = olEle.select("li:not([class=last])");
		Job job = null;
		int i = 0;
		for (Element postEle : postsEle) {
			if (i >= 10) break;
			String title = postEle.select("h3[class=title]").select("a[class= ac-algo fz-l lh-20 tc d-ib va-mid]").text();
			String source = postEle.select("h3[class=title]").select("a").attr("href");
			String desc = postEle.select("p[class=fbox-ov fbox-lc3 d-box ov-h lh-l]").text();
			job = new Job(title, desc, source);
			resultList.add(job);
			i++;
		}
		return resultList;
	}


	///////////////////////////////// Google 整理的職缺
	/**
	 * 查詢 job list
	 * @return job list
	 * @throws IOException
	 */
	public List<Job> queryJobList() throws IOException{ 	//HashMap<String, String>
		content= fetchContent("https://www.google.com/search?q=" + keywordList.getKeywordsQueryParam() + "&ibp=htl;jobs&sa=X&ved=2ahUKEwjj1uTxx93mAhXRGaYKHf7xCZEQiYsCKAB6BAgKEAM#htivrt=jobs&htidocid=gjWPJUJRSlCr-jBgAAAAAA%3D%3D&fpstate=tldetail");
		//
		resultList = new ArrayList<>();
		Document doc = Jsoup.parseBodyFragment(content);
		Element ul = doc.body();
		Elements elements = ul.select("li[class=PaEvOc hide-focus-ring gws-horizon-textlists__li-ed]");
//		Elements elements = doc.select("li[class=PaEvOc hide-focus-ring gws-horizon-textlists__li-ed]");
		Job job = null;
		for (Element ele: elements) {
			Element gScrolling = ele.select("g-scrolling-carousel").first();
			// eg. 原 id: jdao62PLjnZnBdc9TKtxAAAAAA==,  現 id: 62PLjnZnBdc9TKtxAAAAAA
			String id = gScrolling.select("div[jsname=x5pWN]").first().attr("id").substring(4, 20); // 去掉 jdao 以及 ==
			//
			String title = ele.select(".BjJfJf.cPd5d").first().text();
			//
			Element nls = ele.select(".SHrHx").first();
			String compName = nls.select(".gyaQGc").first().text();
			String location = nls.select(".LqUjCc").first().text();
			String source = nls.select(".LqUjCc").last().text();
			//
			Elements sps = ele.select("span[class=JQa6Xe]");

			// list 當中沒有 desc, single job page 有 desc
			String postDate = ele.select("span[class=JQa6Xe]").first().text();
			String salaries = sps.size() > 2 ?  ele.select("span[class=JQa6Xe]").last().text() : "";
			//
			job = new Job(id, title, compName, null, salaries, location, source, postDate);
//			job = new Job(id, title, compName, null, salaries, location, source, null, postDate);
			resultList.add(job);
		}
		return resultList;
	}

	/**
	 * 查詢 single job 的詳細內容
	 * @param id job id
	 * @return Job 物件 （擁有 detail information）
	 */
	public Job querySingleJob(String id) throws IOException {
		content = fetchContent("https://www.google.com/search?q=" + keywordList.getKeywordsQueryParam() + "&ibp=htl;jobs&sa=X&ved=2ahUKEwih2O2NzN3mAhWQHKYKHZfvA3UQiYsCKAB6BAgKEAM#htivrt=jobs&htidocid=" + id + "AAAAAA%3D%3D&fpstate=tldetail");
		Job job = null;
		if (content != null) {
//			Document doc = Jsoup.parse(content);
			Document doc = Jsoup.parseBodyFragment(content);
			doc.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
			Element body = doc.body();
			Element detailEle = body.select("div[class=pE8vnd GZjjfc]").first();
//			Element detailEle = doc.select("div[class=pE8vnd GZjjfc]").first();
			String title =  detailEle.select("h2[class=n2yttb][jsname=SBkjJd]").first().text();
			//
			Element cl = detailEle.select("div[class=wozmme]").first();
			String company = cl.select("div[class=pbHUre tcoBdd]").first().text();
			String location = cl.select("div[class=tcoBdd]").first().text();
			//
			Element ds = detailEle.select("div[class=edeiNb]").first();
			String postDate = ds.select("span[class=JQa6Xe]").first().text();
			String salaries = ds.select("span[class=JQa6Xe]").last().text();
			//
			String source = detailEle.select("a[class=D7VqAe LwS2ce]").first().attr("href");
			String desc = detailEle.select("span[class=HBvzbc]").first().text();
			//
			job = new Job(id, title,company, desc, salaries, location, source, postDate);
		}
		return job; // 擁有詳細信息 的 job
	}


	/**
	 * 利用不同搜尋引擎權重查詢的結果
	 * @param jobList
	 * @return
	 * @throws IOException
	 */
	public List<Job> queryJobListWeight(List<Job> jobList) throws IOException {
		List<Job> list = new LinkedList<>();
		for (Job job : jobList) {
			String jobUrl = job.getSource();
			// Google 職缺頁面, source 另外處理
//			if (!jobUrl.startsWith("http://") || !jobUrl.startsWith("https://")) {
//				jobUrl = querySingleJob(job.getId()).getSource();
//				System.out.println("不應該是 google 職缺的頁面：" + jobUrl);
//			}
			WebPage root = new WebPage(jobUrl, job.getTitle());
			WebTree tree = new WebTree(root);
			/* 獲取底下的連結 a */
			content = fetchContent(jobUrl);
			if (content != null) {
				Document doc = Jsoup.parse(content);
				Element body = doc.body();
				if (body != null) {
					// TODO: nav a href 先抓一層，之後要修改 【.select("nav")】
					Elements navAEles = body.select("nav").select("a");
					ArrayList<String> urlTemp = new ArrayList<>();
					if (navAEles != null && navAEles.size() > 0) {
						System.out.println("nav 連結 數量: " + navAEles.size());
						urlTemp.add(jobUrl);
						for (Element navA : navAEles) {
							String href = navA.attr("href");
							if (href.equals(jobUrl) || urlTemp.contains(href)) {
								continue;
							}
							urlTemp.add(href);
							tree.root.addChild(new WebNode(new WebPage(href, "")));
						}
					}
					tree.setPostOrderScore(keywordList.getKeywordList());
					System.out.println("分數: " + tree.root.nodeScore);
					double score = tree.root.nodeScore;
					// String title, String desc, String source, double score
					list.add(new Job(job.getTitle(), job.getDesc(), job.getSource(), score));
				}
			}
		}
		System.out.println("before weight sort: " + list);
		list.sort(new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {
				// 如果 o1.score > o2.score, 則 o1 排在 o2 前面
				if (o1.getScore() > o2.getScore()) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		System.out.println("after weight sort: " + list);
		return list;
	}
}
