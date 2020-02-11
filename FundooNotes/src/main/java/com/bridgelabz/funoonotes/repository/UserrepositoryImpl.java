package com.bridgelabz.funoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Repository;

import com.bridgelabz.funoonotes.entity.UserInformation;
@Repository
public class UserrepositoryImpl implements UserRepository{
	@PersistenceContext//inject an Entity Manager into their DAO classes.
	private EntityManager entityManager;
	@Override
	public UserInformation save(UserInformation userInformation) {
		Session
		return null;
	}


}
