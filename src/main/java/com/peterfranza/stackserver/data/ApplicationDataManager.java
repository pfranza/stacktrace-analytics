package com.peterfranza.stackserver.data;

import java.util.Collection;

import com.google.inject.ImplementedBy;
import com.peterfranza.stackserver.data.impl.DefaultApplicationDataManager;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

@ImplementedBy(DefaultApplicationDataManager.class)
public interface ApplicationDataManager {

	ApplicationModel getApplicationByApiKey(String apiKey);

	ApplicationModel createApplication(String name, String description);
	
	Collection<ApplicationModel> getAllApplications();

	void appendTrace(ApplicationModel applicationModel, String hostSignature,
			String data, String version, StackTraceElement[] traceElements);
	
}
