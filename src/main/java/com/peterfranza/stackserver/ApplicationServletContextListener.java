package com.peterfranza.stackserver;

import org.aopalliance.intercept.MethodInterceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.api.SubmitStackTrace;
import com.peterfranza.stackserver.security.SecurityInterceptor;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ApplicationServletContextListener extends GuiceServletContextListener {

	private MethodInterceptor interceptor = new SecurityInterceptor();
	
	private ServletModule module = new JerseyServletModule(){
		@Override
		protected void configureServlets() {
			
			bind(SubmitStackTrace.class);
			
			serve("/api/*").with(GuiceContainer.class);
		}
	};
	
	@Override
	protected Injector getInjector() {
		Injector i = Guice.createInjector(module);
		i.injectMembers(interceptor);
		return i;
	}

}
