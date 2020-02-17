package com.bridgelabz.fundoonotes.utility;

import java.io.Serializable;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.entity.UserInformation;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class HibernateUtil<T> {
	@Autowired 
	private EntityManager entityManager;
	
	
	public T save(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.save(object);
	}
	
	public Query<T> createQuery(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	
	public void update(T object)
	{
		Session session = entityManager.unwrap(Session.class);
		 session.update(object);
	}
	public T updateAndGetObject(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.createQuery(query).list().stream().findFirst();
	}
	
	public Query<T> select(String query)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query);
	}
	public T getCurrentUser(Serializable value)
	{
		Session session = entityManager.unwrap(Session.class);
		return (T) session.get(UserInformation.class,value);
	}
	
	
}
