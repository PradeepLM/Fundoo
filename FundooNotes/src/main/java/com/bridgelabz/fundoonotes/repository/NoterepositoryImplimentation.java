package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
@Repository
public class NoterepositoryImplimentation implements NoteRepository{
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public NoteInformation save(NoteInformation noteInformation) {
		
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteInformation);
		return noteInformation;
	}

}
