package com.peterfranza.stackserver.ui.server;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Provider;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.ui.shared.ApplicationCollectionResult;
import com.peterfranza.stackserver.ui.shared.FetchAllApplications;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

public class FetchAllApplicationsHandler implements ActionHandler<FetchAllApplications, ApplicationCollectionResult>{

	@Inject 
	public Provider<ApplicationDataManager> dataManager;
	
	@Override
	public ApplicationCollectionResult execute(FetchAllApplications arg0,
			ExecutionContext arg1) throws DispatchException {
		ApplicationCollectionResult result = new ApplicationCollectionResult();
		result.setList(new ArrayList<ApplicationModel>(dataManager.get().getAllApplications()));
		
		return result;
	}
	
	@Override
	public Class<FetchAllApplications> getActionType() {
		return FetchAllApplications.class;
	}

	@Override
	public void rollback(FetchAllApplications arg0, ApplicationCollectionResult arg1,
			ExecutionContext arg2) throws DispatchException {}

}

