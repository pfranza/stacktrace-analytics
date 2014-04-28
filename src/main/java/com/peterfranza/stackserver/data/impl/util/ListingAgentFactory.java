package com.peterfranza.stackserver.data.impl.util;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class ListingAgentFactory {
	
	@Inject
	EntityManager entityManager;
	
	<T> ListingAgent<T> create(Class<T> cls) {
		return new AbstractListingAgent<T>(entityManager, cls);
	}
}