package com.peterfranza.stackserver.ui.server;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.peterfranza.stackserver.data.UserDataManager;
import com.peterfranza.stackserver.ui.shared.DeleteUser;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;
import com.peterfranza.stackserver.ui.shared.UserCollectionResult;

public class DeleteUserHandler implements ActionHandler<DeleteUser, UserCollectionResult>{

	@Inject UserDataManager dataManager;
	@Inject FetchAllUsersHandler fetch;
	
	@Override
	public UserCollectionResult execute(DeleteUser arg0, ExecutionContext arg1)
			throws DispatchException {
		dataManager.removeUser(arg0.getEmailAddress());
		return fetch.execute(new FetchAllUsers(), arg1);
	}

	@Override
	public Class<DeleteUser> getActionType() {
		return DeleteUser.class;
	}

	@Override
	public void rollback(DeleteUser arg0, UserCollectionResult arg1,
			ExecutionContext arg2) throws DispatchException {}

	

}
