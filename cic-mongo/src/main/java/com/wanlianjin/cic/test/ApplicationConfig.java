package com.wanlianjin.cic.test;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.wanlianjin.cic.mongo.util.BuildOptions;
import com.wanlianjin.cic.mongo.util.MongoSequence;
import com.wanlianjin.cic.mongo.util.MongoSession;
import com.wanlianjin.cic.mongo.util.MongoTemplate;
import com.wanlianjin.cic.mongo.util.Sequence;


@Configuration
@ComponentScan(basePackages = "com.kahadb.utility.mongodb")
public class ApplicationConfig {
	
	@Bean
	public MongoSession mongoSession() throws Exception{
		BuildOptions options = new BuildOptions();
		options.setHosts("10.196.20.194:21101,10.196.20.194:21102,10.196.20.194:21103");
		options.setDatabaseName("ddd");
		options.setUserName("res_ddd");
		options.setPassword("s123_zxcv");
		return new MongoSession(options);
	}
	
	@Bean
	public Sequence sequence() throws Exception{
		return new MongoSequence(mongoSession());
	}
	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		MongoTemplate template = new MongoTemplate(mongoSession());
		template.setSequence(sequence());
		return template;
	}
}
