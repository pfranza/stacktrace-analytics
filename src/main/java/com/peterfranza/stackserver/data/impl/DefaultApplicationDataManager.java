package com.peterfranza.stackserver.data.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.google.inject.persist.Transactional;
import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.data.model.ApplicationModel_;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

public class DefaultApplicationDataManager implements ApplicationDataManager {

	@Inject
	EntityManager entityManager;
	
	@Override
	public ApplicationModel getApplicationByApiKey(String apiKey) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ApplicationModel> c = qb.createQuery(ApplicationModel.class);

		Root<ApplicationModel> p = c.from(ApplicationModel.class);
		c.where(qb.equal(qb.upper(p.get(ApplicationModel_.apiKey)), apiKey.toUpperCase()));
		TypedQuery<ApplicationModel> q = entityManager.createQuery(c); 
		return DefaultUserDataManager.getSingleResultOrNull(q);
	}
	

	@Override
	public Collection<ApplicationModel> getAllApplications() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ApplicationModel> c = qb.createQuery(ApplicationModel.class);

		c.from(ApplicationModel.class);
		TypedQuery<ApplicationModel> q = entityManager.createQuery(c); 
		return q.getResultList();
	}
	
	@Transactional
	@Override
	public ApplicationModel createApplication(String name, String description) {
		ApplicationModel m = new ApplicationModel();
			m.setApplicationName(name);
			m.setApplicationDescription(description);
			
			m.setApiKey(UUID.randomUUID().toString());
			m.setTokenSecret(nextRandomToken());
			m.setConsumerSecret(nextRandomToken());
						
		entityManager.persist(m);
		
		return m;
	}
	
	private SecureRandom random = new SecureRandom();
	private String nextRandomToken() {
	  return new BigInteger(256, random).toString(32);
	}


}
