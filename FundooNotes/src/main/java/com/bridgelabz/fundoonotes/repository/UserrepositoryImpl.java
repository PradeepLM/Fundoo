package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.UserInformation;

@Repository
public class UserrepositoryImpl implements UserRepository{
	@PersistenceContext//inject an Entity Manager into their DAO classes.
	private EntityManager entityManager;
	@Override
	public UserInformation save(UserInformation userInformation) {
		 Session session=entityManager.unwrap(Session.class);
		 session.saveOrUpdate(userInformation);
		return userInformation;
	}
	@Override
	public UserInformation getUser(String name) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where name=:name");
		q.setParameter("name", name);
		return (UserInformation) q.uniqueResult();
	}

}
