package com.bridgelabz.fundoonotes.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

@Repository
public class UserrepositoryImpl<T> implements UserRepository {
	// @PersistenceContext//inject an Entity Manager into their DAO classes.
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserInformation save(UserInformation userInformation) {
		Session session = entityManager.unwrap(Session.class);
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
//		System.out.println("ID :"+id);
		Session session = entityManager.unwrap(Session.class);
		try {
			UserInformation user = (UserInformation) getCurrentNote(id);
			user.setVerified(true);
			session.update(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean upDate(PasswordUpdate information, Long id) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery q = session.createQuery("UPDATE UserInformation set password =:p" + " " + " " + "where userId=:id");
		q.setParameter("p", information.getConfirmPassword());
		q.setParameter("id", id);
		System.out.println("heloo");
		int status = q.executeUpdate();
		System.err.println("hii");
		if (status > 0) {
			return true;
		} else {
			return false;
		}
	}

	public T getCurrentNote(Serializable value) {
		Session session = entityManager.unwrap(Session.class);
		return (T) session.get(UserInformation.class, value);
	}

	@Override
	public List<UserInformation> getUsers() {
		Session session = entityManager.unwrap(Session.class);
		List userLists = session.createQuery("FROM UserInformation").getResultList();
		return userLists;
	}

	@Override
	public UserInformation getUserById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery(" FROM UserInformation where id=:id");
		q.setParameter("id", id);
		return (UserInformation) q.uniqueResult();
	}
}
