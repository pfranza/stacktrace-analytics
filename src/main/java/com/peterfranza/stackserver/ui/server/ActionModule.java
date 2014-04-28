package com.peterfranza.stackserver.ui.server;

import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.ui.shared.AddUser;
import com.peterfranza.stackserver.ui.shared.DeleteUser;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

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
		bind(AddUser.class).in(RequestScoped.class);
	}

}
