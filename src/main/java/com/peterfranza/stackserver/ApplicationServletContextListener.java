package com.peterfranza.stackserver;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import javax.naming.InitialContext;

import org.aopalliance.intercept.MethodInterceptor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.peterfranza.stackserver.api.SubmitStackTrace;
import com.peterfranza.stackserver.data.ApplicationDefinition;
import com.peterfranza.stackserver.security.RequiresAuthentication;
import com.peterfranza.stackserver.security.SecurityInterceptor;
import com.peterfranza.stackserver.security.SecurityInterceptor.ApplicationDefinitionFactory;
import com.peterfranza.stackserver.security.openid.OpenIDLoginModule;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ApplicationServletContextListener extends GuiceServletContextListener {

	private MethodInterceptor interceptor = new SecurityInterceptor();
	
	private ServletModule module = new JerseyServletModule(){
		@Override
		protected void configureServlets() {
			
			install(new JpaPersistModule("ApplicationData"));

			bind(SubmitStackTrace.class);
			
			filter("/*").through(PersistFilter.class);
			
			bindInterceptor(any(), annotatedWith(RequiresAuthentication.class), interceptor);
			bind(ApplicationDefinition.class).toProvider(ApplicationDefinitionFactory.class);
			install(new OpenIDLoginModule());
			
			serve("/api/*").with(GuiceContainer.class);			
		}
	};
	
	@Override
	protected Injector getInjector() {
		Injector i = Guice.createInjector(Stage.PRODUCTION, module);
		i.injectMembers(interceptor);
		return i;
	}
	
}
