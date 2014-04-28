package com.peterfranza.stackserver.security;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import org.aopalliance.intercept.MethodInterceptor;

import com.google.inject.AbstractModule;
import com.peterfranza.stackserver.security.SecurityInterceptor.ApplicationDefinitionFactory;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

public class ApiSecurityModule extends AbstractModule {

	private MethodInterceptor interceptor = new SecurityInterceptor();
	
	@Override
	protected void configure() {
		bindInterceptor(any(), annotatedWith(RequiresAuthentication.class), interceptor);
		bind(ApplicationModel.class).toProvider(ApplicationDefinitionFactory.class);
		requestInjection(interceptor);
		
	}
	
}
