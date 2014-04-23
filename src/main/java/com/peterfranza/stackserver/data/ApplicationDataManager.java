package com.peterfranza.stackserver.data;

import com.google.inject.ImplementedBy;
import com.peterfranza.stackserver.data.impl.DefaultApplicationDataManager;

@ImplementedBy(DefaultApplicationDataManager.class)
public interface ApplicationDataManager {

	ApplicationDefinition getApplicationByApiKey(String apiKey);
	
}
