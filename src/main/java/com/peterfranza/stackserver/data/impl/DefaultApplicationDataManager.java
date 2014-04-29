package com.peterfranza.stackserver.data.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.inject.persist.Transactional;
import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.data.impl.util.ListingAgent.QueryModifier;
import com.peterfranza.stackserver.data.impl.util.ListingAgentFactory;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel_;
import com.peterfranza.stackserver.ui.shared.model.StackTraceEntry;
import com.peterfranza.stackserver.ui.shared.model.StackTraceEntry_;

public class DefaultApplicationDataManager implements ApplicationDataManager {

	@Inject
	EntityManager entityManager;
	
	@Inject
	ListingAgentFactory stackManager;
	
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

	@Transactional
	@Override
	public void appendTrace(ApplicationModel applicationModel,
			String hostSignature, String data, String version,
			String message, StackTraceElement[] traceElements) {
		StackTraceEntry e = new StackTraceEntry();
			e.setApplicationId(applicationModel.getId());
			e.setRaw(data);
			e.setMessage(message);
			e.setTimeOccured(new Date());
			e.setVersion(version);
			e.setFingerprints(transformPrints(applicationModel, traceElements));
			
		entityManager.persist(e);
	}
	
	private List<String> transformPrints(ApplicationModel applicationModel, StackTraceElement[] traceElements) {
		ArrayList<String> list = new ArrayList<String>();
		for(StackTraceElement e: traceElements) {
			if(filterPrint(applicationModel, e)) {
				list.add(new String(DigestUtils.md5(e.toString())));
			}
		}
		return list;
	}

	private boolean filterPrint(ApplicationModel applicationModel, StackTraceElement e) {
		// TODO Auto-generated method stub
		System.out.println(e.toString());
		return true;
	}

	private SecureRandom random = new SecureRandom();
	private String nextRandomToken() {
	  return new BigInteger(256, random).toString(32);
	}


	@Override
	public int getStackCount() {
		return stackManager.create(StackTraceEntry.class).getCount();
	}


	@Override
	public Collection<StackTraceEntry> getStackEntries(int start, int length) {
		return stackManager.create(StackTraceEntry.class).getEntries(start, length, new QueryModifier<StackTraceEntry>(){
			@Override
			public void modifyQuery(CriteriaBuilder qb, CriteriaQuery<StackTraceEntry> c, Root<StackTraceEntry> p) {
				c.orderBy(qb.desc(p.get(StackTraceEntry_.timeOccured)));
			}});
	}

}
