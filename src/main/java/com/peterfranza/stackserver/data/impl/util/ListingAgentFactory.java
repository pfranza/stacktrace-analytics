package com.peterfranza.stackserver.data.impl.util;

import com.google.inject.ImplementedBy;

@ImplementedBy(AbstractListingAgentFactory.class)
public interface ListingAgentFactory {
	<T> ListingAgent<T> create(Class<T> cls);
}
