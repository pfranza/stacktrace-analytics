package com.peterfranza.stackserver.ui.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StackTraceEntry {
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private Date timeOccured;
	
	private String applicationId;
	
	private String version;
	
	@ElementCollection
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

	public String getId() {
		return id;
	}
	
	
	
}
