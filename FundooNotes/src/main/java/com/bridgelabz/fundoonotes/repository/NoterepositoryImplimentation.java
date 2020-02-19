package com.bridgelabz.fundoonotes.repository;

import java.util.List;

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
		Query qry=session.createQuery("from NoteInformation where id=:id");
		qry.setParameter("id", id);
		return (NoteInformation) qry.uniqueResult();
	}
	@Override
	public boolean deleteNote(Long id, Long userId) {
		Session session=entityManager.unwrap(Session.class);
		Query qry=session.createQuery("DELETE from NoteInformation where id=:id").setParameter("id", id);
		int result=qry.executeUpdate();
		if(result>=1) {
			return true;
		}
		return false;
	}
	@Override
	public List<NoteInformation> getArchievedNotes(Long userId) {
		Session session =entityManager.unwrap(Session.class);
		List list=session.createQuery("FROM NoteInformation where id='"+userId+"'"+"and is_archieved=true").getResultList();
		return list;
	}
	@Override
	public List<NoteInformation> getTrashedNotes(Long userId) {
		Session session =entityManager.unwrap(Session.class);
		List list = session.createQuery("from NoteInformation where id='" + userId + "'" + "and is_trashed=true").getResultList();
		return list;
	}
	@Override
	public List<NoteInformation> getAllNotes(Long userId) {
		Session session =entityManager.unwrap(Session.class);
		List list=session.createQuery("FROM NoteInformation where id='"+userId+"'"+"and is_trashed=false and is_archieved=false ORDER BY id DESC").getResultList();
		return list;
	}

}
