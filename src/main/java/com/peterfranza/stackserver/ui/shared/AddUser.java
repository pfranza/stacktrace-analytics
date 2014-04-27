package com.peterfranza.stackserver.ui.shared;

import net.customware.gwt.dispatch.shared.Action;

public class AddUser implements Action<UserCollectionResult> {

	private String emailAddress;
	
	AddUser(){}
	public AddUser(String e) {
		this.emailAddress = e;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
}
