package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
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


}
