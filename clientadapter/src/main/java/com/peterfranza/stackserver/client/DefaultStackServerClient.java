package com.peterfranza.stackserver.client;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

@Singleton
public class DefaultStackServerClient implements StackServerClient {

	private WebResource dataResource;
	
	private String version;
	private String hostSignature;

	private ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
				t.setDaemon(true);
				t.setName("DefaultStackServerClient");			
			return t;
		}
	});
	
	@Inject
	public DefaultStackServerClient(@Named("StackAnalyticsClient-APIKEY") String apiKey, 
			@Named("StackAnalyticsClient-ConsumerSecret") String consumerSecret, 
			@Named("StackAnalyticsClient-TokenSecret") String tokenSecret, 
			@Named("StackAnalyticsClient-APIROOT") String apiRoot,
			@Named("StackAnalyticsClient-VERSION") String version,
			@Named("StackAnalyticsClient-HOST") String hostSignature) {
	
		this.version = version;
		this.hostSignature = hostSignature;
		
		OAuthParameters params = new OAuthParameters().signatureMethod("HMAC-SHA1").version();
		params.setConsumerKey(apiKey);

		// OAuth secrets to access resource
		OAuthSecrets secrets = new OAuthSecrets();
		secrets.consumerSecret(consumerSecret).setTokenSecret(tokenSecret);

		Client client = Client.create();

		// if parameters and secrets remain static, filter can be added to each web resource
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

		dataResource = client.resource(apiRoot);
		dataResource.addFilter(filter);
		
	}
	
	public String getTime() {
		return dataResource.path("/time").get(String.class);
	}
	
	@Override
	public void submit(final Throwable t) {
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
					formData.add("data", new Gson().toJson(createDefinition(t, version, hostSignature)));
					
				dataResource.path("/submit").post(formData);
			}
		});
	}
	
	private static TraceDefinition createDefinition(Throwable t, String version, String hostSignature) {
		StringWriter errors = new StringWriter();
		t.printStackTrace(new PrintWriter(errors));
		
		ArrayList<StackTraceElement> elements = new ArrayList<StackTraceElement>();
		Throwable curr = t;
		while(curr != null) {
			elements.addAll(Arrays.asList(curr.getStackTrace()));
			curr = curr.getCause();
		}
		
		TraceDefinition d = new TraceDefinition();
			d.version = version;
			d.hostSignature = hostSignature;
			d.data = errors.toString();
			d.message = t.getMessage();
			d.traceElements = elements.toArray(new StackTraceElement[0]);
			
		return d;
	}
	
	@SuppressWarnings("unused")
	private static class TraceDefinition implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3921132426640666184L;
		
		public String hostSignature;
		public String data;
		public String version;
		public String message;
		public StackTraceElement[] traceElements;
	}
	
}
