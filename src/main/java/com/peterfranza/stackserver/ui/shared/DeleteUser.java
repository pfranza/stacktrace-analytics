package com.peterfranza.stackserver.ui.shared;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteUser implements Action<UserCollectionResult>{

	private String emailAddress;
	
	DeleteUser(){}
	public DeleteUser(String e) {
		this.emailAddress = e;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
}
