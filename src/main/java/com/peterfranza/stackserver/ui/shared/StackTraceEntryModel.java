package com.peterfranza.stackserver.ui.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StackTraceEntryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 952936856786026780L;

	private Date timeOccured;
	
	private String applicationId;
	
	private String version;
	
	private List<String> fingerprints = new ArrayList<String>();
	
	private String raw;

	public Date getTimeOccured() {
		return timeOccured;
	}

	public void setTimeOccured(Date timeOccured) {
		this.timeOccured = timeOccured;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getFingerprints() {
		return fingerprints;
	}

	public void setFingerprints(List<String> fingerprints) {
		this.fingerprints = fingerprints;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}
	
	
	
}
