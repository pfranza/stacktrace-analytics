package com.peterfranza.stackserver.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApplicationModel {

	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String applicationName;
	private String applicationDescription;
	
	private String apiKey;
	private String tokenSecret;
	private String consumerSecret;
	
	
	
}
