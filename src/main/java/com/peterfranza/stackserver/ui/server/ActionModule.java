package com.peterfranza.stackserver.ui.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.ui.shared.AddApplication;
import com.peterfranza.stackserver.ui.shared.AddUser;
import com.peterfranza.stackserver.ui.shared.DeleteUser;
import com.peterfranza.stackserver.ui.shared.FetchAllApplications;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;
import com.peterfranza.stackserver.ui.shared.FetchStackTraceList;

public class ActionModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		install(new ServletModule(){
			@Override
			protected void configureServlets() {
				serve("/stackserverui/dispatch").with(GuiceStandardDispatchServlet.class);
			}
		});
		bindHandler(FetchAllUsers.class, FetchAllUsersHandler.class);
		bindHandler(AddUser.class, AddUserHandler.class);
		bindHandler(DeleteUser.class, DeleteUserHandler.class);
		
		bindHandler(AddApplication.class, AddApplicationHandler.class);
		bindHandler(FetchAllApplications.class, FetchAllApplicationsHandler.class);
		
		bindHandler(FetchStackTraceList.class, FetchStackTraceListHandler.class);
		
	}

}
