package com.peterfranza.stackserver.ui.server;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.peterfranza.stackserver.ui.shared.AddApplication;
import com.peterfranza.stackserver.ui.shared.ApplicationCollectionResult;
import com.peterfranza.stackserver.ui.shared.FetchAllApplications;

public class AddApplicationHandler implements ActionHandler<AddApplication, ApplicationCollectionResult>{

	@Inject FetchAllApplicationsHandler fetch;
	
	@Override
	public Class<AddApplication> getActionType() {
		return AddApplication.class;
	}

	@Override
	public ApplicationCollectionResult execute(AddApplication action,
			ExecutionContext context) throws DispatchException {
		fetch.dataManager.get().createApplication(action.getApplicationName(), action.getApplicationDescription());
		return fetch.execute(new FetchAllApplications(), context);
	}

	@Override
	public void rollback(AddApplication action,
			ApplicationCollectionResult result, ExecutionContext context)
			throws DispatchException {}

}
