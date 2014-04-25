package com.peterfranza.stackserver.client;

import javax.inject.Inject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

public class DefaultStackServerClient implements StackServerClient {

	private WebResource dataResource;

	@Inject
	public DefaultStackServerClient(String apiKey, String consumerSecret, String tokenSecret, String apiRoot) {
		OAuthParameters params = new OAuthParameters().signatureMethod("HMAC-SHA1").version();
		params.setConsumerKey(apiKey);

		// OAuth secrets to access resource
		OAuthSecrets secrets = new OAuthSecrets();
		secrets.consumerSecret(consumerSecret)
		.setTokenSecret(tokenSecret);

		Client client = Client.create();

		// if parameters and secrets remain static, filter can be added to each web resource
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

		dataResource = client.resource(apiRoot);
		dataResource.addFilter(filter);
	}
	
	@Override
	public void submit(Throwable t) {
		
//		ServiceInterface proxy = WebResource.newResource(ServiceInterface.class,
//				dataResource.target("/submit"));
	}

}
