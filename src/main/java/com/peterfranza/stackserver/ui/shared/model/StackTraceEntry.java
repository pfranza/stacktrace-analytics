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
	
}
