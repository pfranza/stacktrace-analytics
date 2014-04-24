package com.peterfranza.stackserver.data.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
    private String id;
	
	private String emailAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	
}
