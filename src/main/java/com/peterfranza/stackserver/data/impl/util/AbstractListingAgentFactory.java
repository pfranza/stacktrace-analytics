package com.peterfranza.stackserver.data.impl.util;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class AbstractListingAgentFactory implements ListingAgentFactory{
	
	@Inject
	Provider<EntityManager> entityManager;
	
	public <T> ListingAgent<T> create(Class<T> cls) {
		return new AbstractListingAgent<T>(entityManager.get(), cls);
	}
}