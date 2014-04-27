package com.peterfranza.stackserver.ui.server;

import com.peterfranza.stackserver.ui.shared.AddUser;
import com.peterfranza.stackserver.ui.shared.DeleteUser;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class ActionModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(FetchAllUsers.class, FetchAllUsersHandler.class);
		bindHandler(AddUser.class, AddUserHandler.class);
		bindHandler(DeleteUser.class, DeleteUserHandler.class);
	}

}
