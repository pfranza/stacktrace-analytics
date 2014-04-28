package com.peterfranza.stackserver.data.impl;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.inject.persist.Transactional;
import com.peterfranza.stackserver.data.UserDataManager;
import com.peterfranza.stackserver.data.model.User;
import com.peterfranza.stackserver.data.model.User_;

public class DefaultUserDataManager implements UserDataManager {

	@Inject
	EntityManager entityManager;
	
	@Override
	public int getUserCount() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		Root<User> p = cq.from(User.class);
		cq.select(qb.count(p));		
		return entityManager.createQuery(cq).getSingleResult().intValue();
	}
	

	@Override
	public User getByEmail(String emailAddress) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = qb.createQuery(User.class);

		Root<User> p = c.from(User.class);
		Predicate condition = qb.equal(qb.upper(p.get(User_.emailAddress)), emailAddress.toUpperCase());
		c.where(condition);
		TypedQuery<User> q = entityManager.createQuery(c); 
		return getSingleResultOrNull(q);
	}


	@Override
	public User getByAuthorizationId(String authorizationId) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = qb.createQuery(User.class);

		Root<User> p = c.from(User.class);
		Predicate condition = qb.equal(p.get(User_.authorizationId), authorizationId);
		c.where(condition);
		TypedQuery<User> q = entityManager.createQuery(c); 
		return getSingleResultOrNull(q);
	}

	@Override
	public boolean isUserAuthorized(String emailAddress) {
		
		if(getUserCount() == 0)
			return true;
		
		return getByEmail(emailAddress) != null;
	}

	
	public static <T> T getSingleResultOrNull(TypedQuery<T> query){
		query.setMaxResults(1);
		query.setHint("org.hibernate.cacheable", true);
		List<T> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException();
    }

	@Override
	@Transactional
	public void createUser(String email) {
		User u = new User();
		u.setEmailAddress(email);
		entityManager.persist(u);	
		System.out.println("Added: " + u.getEmailAddress() + "  " + u.getId());
		
		entityManager.persist(u);
	}

	@Override
	@Transactional
	public void setUserAuthorizatonId(String email, String authorizationId) {
		User u = getByEmail(email);
		u.setAuthorizationId(authorizationId);
		entityManager.persist(u);
	}

	@Override
	@Transactional
	public void removeUser(String email) {
		entityManager.remove(getByEmail(email));
	}


	@Override
	public Collection<User> getAllUsers() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> c = qb.createQuery(User.class);

		c.from(User.class);
		TypedQuery<User> q = entityManager.createQuery(c);
		
		return q.getResultList();
	}


}
