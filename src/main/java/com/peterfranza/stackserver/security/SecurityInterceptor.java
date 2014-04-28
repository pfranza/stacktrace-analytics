package com.peterfranza.stackserver.security;


import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.WebApplicationException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthRequest;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.OAuthSignature;
import com.sun.jersey.oauth.signature.OAuthSignatureException;


public class SecurityInterceptor implements MethodInterceptor {

	@Inject Provider<HttpContext> contextProvider;
	@Inject Provider<ApplicationDataManager> dataManager;
	
	private static final ThreadLocal<Provider<ApplicationModel>> localStore = new ThreadLocal<Provider<ApplicationModel>>();
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			for(Annotation a: invocation.getMethod().getAnnotations()) {
				if (a instanceof RequiresAuthentication ) {

					OAuthRequest request = new OAuthServerRequest(contextProvider.get().getRequest());
					OAuthParameters params = new OAuthParameters().signatureMethod("HMAC-SHA1").version().readRequest(request);
					
					boolean found = false;
					final ApplicationModel application = dataManager.get().getApplicationByApiKey(params.getConsumerKey());
					if(application != null) {
	
						OAuthSecrets secrets = new OAuthSecrets();
						secrets.setTokenSecret(application.getTokenSecret());
						secrets.setConsumerSecret(application.getConsumerSecret());

						try {
							if(!OAuthSignature.verify(request, params, secrets)) {
								System.out.println("Verification Failed");
								throw new WebApplicationException(403);
							} else {
								found = true;
								localStore.set(new Provider<ApplicationModel>() {
									@Override
									public ApplicationModel get() {
										return application;
									}
								});
							}
						}
						catch (OAuthSignatureException ose) {
							throw new WebApplicationException(403);
						}
					}

					if(!found) {
						throw new WebApplicationException(403);
					}
				}
			}

			return invocation.proceed();
		} finally {
			localStore.remove();
		}
	}
	
	public static class ApplicationDefinitionFactory implements Provider<ApplicationModel> {
		@Override
		public ApplicationModel get() {
			return localStore.get().get();
		}
		
	}
	
}