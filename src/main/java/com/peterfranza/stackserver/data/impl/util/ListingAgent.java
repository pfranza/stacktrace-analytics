package com.peterfranza.stackserver.data.impl.util;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface ListingAgent<T> {

	Collection<T> getEntries(int start, int length, QueryModifier<T> modifier);

	int getCount();

	public interface QueryModifier<T> {
		void modifyQuery(CriteriaBuilder qb, CriteriaQuery<T> c, Root<T> p);
	}

}
