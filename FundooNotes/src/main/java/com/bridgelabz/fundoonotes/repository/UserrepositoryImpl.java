package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.UserInformation;

@Repository
public class UserrepositoryImpl implements UserRepository{
	//@PersistenceContext//inject an Entity Manager into their DAO classes.
	@Autowired
	private EntityManager entityManager;
	@Override
	public UserInformation save(UserInformation userInformation) {
		 Session session=entityManager.unwrap(Session.class);
		 session.saveOrUpdate(userInformation);
		return userInformation;
	}
	
	
	@Override
	public UserInformation getUser(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where email=:email");
		q.setParameter("email", email);
		return (UserInformation) q.uniqueResult();
	}
	
	
	@Override
	public boolean verify(Long id) {
		Session session=entityManager.unwrap(Session.class);
		//Query qry=session.createQuery("update UserInformation set is_verified =:p\" + \" \" + \" \" + \" where id=:i");
		TypedQuery<UserInformation> qry = session.createQuery("update UserInformation set isVerified =:p where userId =:i");
		qry.setParameter("p", true);
		qry.setParameter("i", id);
		try {
		qry.executeUpdate();
		return true;
		}
		catch (Exception e) {
				return false;
		}
		
	}

}
