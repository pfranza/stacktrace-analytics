package com.peterfranza.stackserver.ui.shared;

import net.customware.gwt.dispatch.shared.Action;

public class AddApplication implements Action<ApplicationCollectionResult> {

	private String applicationName;
	private String applicationDescription;
	
	AddApplication(){}
	public AddApplication(String e, String d) {
		this.applicationName = e;
		this.applicationDescription = d;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public String getApplicationDescription() {
		return applicationDescription;
	}
	
	
}
