package com.peterfranza.stackserver.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.peterfranza.stackserver.data.ApplicationDefinition;

@Entity
public class ApplicationModel implements ApplicationDefinition {

	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String applicationName;
	private String applicationDescription;
	
	private String apiKey;
	private String tokenSecret;
	private String consumerSecret;
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public String getAPIKey() {
		return apiKey;
	}
	@Override
	public String getTokenSecret() {
		return tokenSecret;
	}
	@Override
	public String getConsumerSecret() {
		return consumerSecret;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public String getApplicationDescription() {
		return applicationDescription;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
	
	
	
}
