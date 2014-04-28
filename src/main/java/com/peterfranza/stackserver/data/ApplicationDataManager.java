package com.peterfranza.stackserver.data;

import java.util.ArrayList;
import java.util.Collection;

import com.google.inject.ImplementedBy;
import com.peterfranza.stackserver.data.impl.DefaultApplicationDataManager;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;
import com.peterfranza.stackserver.ui.shared.model.StackTraceEntry;

@ImplementedBy(DefaultApplicationDataManager.class)
public interface ApplicationDataManager {

	ApplicationModel getApplicationByApiKey(String apiKey);

	ApplicationModel createApplication(String name, String description);
	
	Collection<ApplicationModel> getAllApplications();

	void appendTrace(ApplicationModel applicationModel, String hostSignature,
			String data, String version, StackTraceElement[] traceElements);

	int getStackCount();

	Collection<StackTraceEntry> getStackEntries(int start, int length);
	
}
