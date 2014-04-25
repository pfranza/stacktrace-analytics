package com.peterfranza.stackserver.data;

public interface ApplicationDefinition {

	String getId();
	
	String getAPIKey();
	String getTokenSecret();
	String getConsumerSecret();
	
}
