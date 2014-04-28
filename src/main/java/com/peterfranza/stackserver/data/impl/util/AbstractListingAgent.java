package com.peterfranza.stackserver.data.impl.util;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.assistedinject.Assisted;

public class AbstractListingAgent<T> implements ListingAgent<T> {

	private Class<T> clazz;
	private EntityManager entityManager;

	AbstractListingAgent(EntityManager entityManager, @Assisted Class<T> clazz) {
		this.clazz = clazz;
		this.entityManager = entityManager;
	}
	
	private Class<T> getEntityClass() {
		return clazz;
	}
	
	@Override
	public int getCount() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		Root<T> p = cq.from(getEntityClass());
		cq.select(qb.count(p));		
		return entityManager.createQuery(cq).getSingleResult().intValue();
	}


	@Override
	public Collection<T> getEntries(int start, int length, QueryModifier<T> modifier) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(getEntityClass());	
		Root<T> p = c.from(getEntityClass());
		if(modifier != null) {
			modifier.modifyQuery(qb, c, p);
		}
		return entityManager.createQuery(c).setFirstResult(start)
				.setMaxResults(length).getResultList();
	}
	
}
