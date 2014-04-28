package com.peterfranza.stackserver.data;

import java.util.Collection;

import com.google.inject.ImplementedBy;
import com.peterfranza.stackserver.data.impl.DefaultUserDataManager;
import com.peterfranza.stackserver.ui.shared.model.User;

@ImplementedBy(DefaultUserDataManager.class)
public interface UserDataManager {

	int getUserCount();
	
	boolean isUserAuthorized(String emailAddress);
	
	void createUser(String email);
	
	void setUserAuthorizatonId(String email, String authorizationId);
	
	void removeUser(String email);
	
	User getByEmail(String emailAddress);
	
	User getByAuthorizationId(String authorizationId);

	Collection<User> getAllUsers();
	
}
