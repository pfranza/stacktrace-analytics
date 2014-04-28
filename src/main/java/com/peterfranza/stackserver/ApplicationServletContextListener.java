package com.peterfranza.stackserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.api.GetServerTime;
import com.peterfranza.stackserver.api.SubmitStackTrace;
import com.peterfranza.stackserver.data.impl.util.AbstractListingAgent;
import com.peterfranza.stackserver.data.impl.util.ListingAgent;
import com.peterfranza.stackserver.data.impl.util.ListingAgentFactory;
import com.peterfranza.stackserver.security.ApiSecurityModule;
import com.peterfranza.stackserver.security.openid.OpenIDLoginModule;
import com.peterfranza.stackserver.ui.server.ActionModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ApplicationServletContextListener extends GuiceServletContextListener {

	private ServletModule module = new JerseyServletModule(){
		
		@Override
		protected void configureServlets() {			
			bind(SubmitStackTrace.class);
			bind(GetServerTime.class);
			
			filter("/*").through(PersistFilter.class);
			serve("/api/*").with(GuiceContainer.class);	
		}
	};

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(Stage.DEVELOPMENT, new JpaPersistModule("ApplicationData"), module, 
				new ActionModule(), new OpenIDLoginModule(), new ApiSecurityModule());
	}
	
}
