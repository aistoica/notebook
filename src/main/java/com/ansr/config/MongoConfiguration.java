package com.ansr.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Value("${databaseName}")
	private String databaseName;

	@Value("${server}")
	private String server;

	@Value("${port}")
	private String port;

	@Value("${userName}")
	private String userName;

	@Value("${password}")
	private String password;

	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		ServerAddress serverAddress = new ServerAddress(server, Integer.parseInt(port));
		MongoCredential credentials = MongoCredential.createMongoCRCredential(userName, databaseName,
				password.toCharArray());
		System.out.println("Username " + userName);
		MongoClient mongoClient;
		if (!userName.equals("") && !password.equals("")) {
			mongoClient = new MongoClient(serverAddress, Arrays.asList(credentials));
		} else {
			mongoClient = new MongoClient(serverAddress);
		}

		return mongoClient;

	}

}