package com.peterfranza.stackserver.ui.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Provider;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.peterfranza.stackserver.data.UserDataManager;
import com.peterfranza.stackserver.data.model.User;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;
import com.peterfranza.stackserver.ui.shared.UserCollectionResult;

public class FetchAllUsersHandler implements ActionHandler<FetchAllUsers, UserCollectionResult>{

	@Inject 
	public Provider<UserDataManager> dataManager;
	
	@Override
	public UserCollectionResult execute(FetchAllUsers arg0,
			ExecutionContext arg1) throws DispatchException {
		UserCollectionResult result = new UserCollectionResult();
			result.setList(transform(dataManager.get().getAllUsers()));
			
		System.out.println("User Count " + dataManager.get().getUserCount());
		System.out.println("Users " + dataManager.get().getAllUsers().size());
			
		return result;
	}
	
	private ArrayList<String> transform(Collection<User> list) {
		ArrayList<String> r = new ArrayList<String>();
		for(User u: list) {
			r.add(u.getEmailAddress());
		}
		Collections.sort(r);
		return r;
	}

	@Override
	public Class<FetchAllUsers> getActionType() {
		return FetchAllUsers.class;
	}

	@Override
	public void rollback(FetchAllUsers arg0, UserCollectionResult arg1,
			ExecutionContext arg2) throws DispatchException {}

}
