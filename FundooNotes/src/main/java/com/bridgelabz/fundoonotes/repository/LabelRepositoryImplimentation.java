package com.bridgelabz.fundoonotes.repository;
/**
 * @author pradeep
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;
@Repository
public class LabelRepositoryImplimentation implements LabelRepository{
	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public LabelInformation fetchLabel(Long userid, String labelname) {
		Session session=entityManager.unwrap(Session.class);
		Query qry=session.createQuery("FROM LabelInformation where UserId=:id and name=:name");
		qry.setParameter("id", userid);
		qry.setParameter("name", labelname);
		return (LabelInformation) qry.uniqueResult();
	}

	@Override
	public LabelInformation save(LabelInformation labelInformation) {
		Session session=entityManager.unwrap(Session.class);
		session.save(labelInformation);
		return labelInformation;
	}

	@Override
	public LabelInformation fetchLabelById(Long labeId) {
		Session session =entityManager.unwrap(Session.class);
		Query qry=session.createQuery("FROM LabelInformation where LabelId=:id");
		qry.setParameter("id", labeId);
		return (LabelInformation) qry.uniqueResult();
	}

	@Override
	public int deleteLabel(Long labelId) {
		Session session=entityManager.unwrap(Session.class);
		Query qry=session.createQuery("DELETE FROM LabelInformation where LabelId=:id");
		qry.setParameter("id", labelId);
		int result=qry.executeUpdate();
		return result;
		
	}

	@Override
	public List<LabelInformation> getAllLabels(Long id) {
		Session session=entityManager.unwrap(Session.class);
//		Query qry=session.createQuery("FROM LabelInformation where UserId=:id");
//		qry.setParameter("id", id); 
		return session.createQuery("FROM LabelInformation where UserId='"+id+"'").getResultList();
	}

	@Override
	public LabelInformation getLabelNotes(Long id) {
		Session session=entityManager.unwrap(Session.class);
		return (LabelInformation) session.createQuery("FROM LabelInformation where LabelId='"+id+"'").uniqueResult();
	}

	@Override
	public List<LabelInformation> fetchLabelId(Long id) {
		Session session=entityManager.unwrap(Session.class);
		List list=session.createQuery("FROM NoteInformation where id='"+id+"'").getResultList();
		return list;
	}

}
