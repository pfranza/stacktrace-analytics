package com.peterfranza.stackserver;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.api.SubmitStackTrace;
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
