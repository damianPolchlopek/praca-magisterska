package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PersonDAO {

	private SessionFactory sessionFactory;

	public PersonDAO() {
		sessionFactory = SessionFact.getInstance().getSessionFactory();
	}

	public User getPerson(String nickName) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query<User> theQuery = session.createQuery("from User where username=:nickName");
		theQuery.setParameter("nickName", nickName);
		List<User> thePerson = theQuery.getResultList();

		session.getTransaction().commit();
		session.close();

		if(thePerson.isEmpty()) {
			return null;
		}
		else {
			return thePerson.get(0);
		}
	}

	public boolean isUsername(String nickName) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Query<User> theQuery = session.createQuery("from User where username=:nickName");
		theQuery.setParameter("nickName", nickName);
		List<User> thePerson = theQuery.getResultList();

		session.getTransaction().commit();
		session.close();

		if (thePerson.isEmpty()) {
			return false;
		}
		else{
			return true;
		}
	}

	public List<User> getPeople() {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();

		try{
			Query<User> theQuery =
					currentSession.createQuery("from User", User.class);

			return theQuery.getResultList();
		}
		finally {
			currentSession.getTransaction().commit();
		}
	}

	public void savePerson(User user){

		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();

		try{
			currentSession.saveOrUpdate(user);
		}
		finally {
			currentSession.getTransaction().commit();
		}
	}

	public void deletePerson(User user){
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.beginTransaction();

		try{
			currentSession.delete(user);
		}
		finally {
			currentSession.getTransaction().commit();
		}
	}


}