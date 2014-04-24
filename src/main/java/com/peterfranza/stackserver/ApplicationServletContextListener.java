package com.peterfranza.stackserver;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import javax.servlet.ServletContextEvent;

import liquibase.integration.servlet.LiquibaseServletListener;

import org.aopalliance.intercept.MethodInterceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.api.SubmitStackTrace;
import com.peterfranza.stackserver.security.RequiresAuthentication;
import com.peterfranza.stackserver.security.SecurityInterceptor;
import com.peterfranza.stackserver.security.openid.OpenIDLoginModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ApplicationServletContextListener extends GuiceServletContextListener {

	private MethodInterceptor interceptor = new SecurityInterceptor();
	
	private ServletModule module = new JerseyServletModule(){
		@Override
		protected void configureServlets() {
			install(new JpaPersistModule("InMemoryData"));
			install(new OpenIDLoginModule());
			bindInterceptor(any(), annotatedWith(RequiresAuthentication.class), interceptor);
			
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
