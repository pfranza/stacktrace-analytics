package com.peterfranza.stackserver.security.openid;

import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;

import com.google.inject.servlet.ServletModule;

public class OpenIDLoginModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		
		filter("/stackserverui/dispatch", "/", "/index.html").through(OpenIDLoginFilter.class);
		serve("/login").with(OpenIDRequestServlet.class);
		serve("/loginresponse").with(OpenIDResponseServlet.class);
		
		ConsumerManager manager = new ConsumerManager();
        manager.setAssociations(new InMemoryConsumerAssociationStore());
        manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
        manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
        bind(ConsumerManager.class).toInstance(manager);
	}
}
