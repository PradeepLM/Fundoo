package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
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
	@Override
	public NoteInformation findById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query qry=session.createQuery("FROM NotesInfo where id=:id");
		qry.setParameter("id", id);
		return (NoteInformation) qry.uniqueResult();
	}

}
