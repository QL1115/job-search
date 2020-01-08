package com.ql.jobsearch.config;

import com.ql.jobsearch.pojo.Company;
import com.ql.jobsearch.pojo.Job;
import com.ql.jobsearch.pojo.Keyword;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PojoConfig {

	@Bean
	public Job job() {
		return new Job();
	}

	@Bean
	public Company company() {
		return new Company();
	}

	@Bean
	public Keyword keyword() {
		return new Keyword();
	}

}
