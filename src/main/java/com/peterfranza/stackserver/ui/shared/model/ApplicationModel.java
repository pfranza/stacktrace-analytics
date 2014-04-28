package com.peterfranza.stackserver.ui.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApplicationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2381989693913943094L;

	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String applicationName;
	private String applicationDescription;
	
	private String apiKey;
	private String tokenSecret;
	private String consumerSecret;
	
	public String getId() {
		return id;
	}
	public String getAPIKey() {
		return apiKey;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
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
