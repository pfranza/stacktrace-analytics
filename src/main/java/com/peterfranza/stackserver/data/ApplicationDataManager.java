package com.peterfranza.stackserver.data;

public interface ApplicationDataManager {

	ApplicationDefinition getApplicationByApiKey(String apiKey);
	
}
