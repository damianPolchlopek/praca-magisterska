package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class PersonDAO {

	private SessionFactory factory;

	public PersonDAO() {
		factory = new Configuration()
			.configure("/hibernate.cfg.xml")
			.addAnnotatedClass(User.class)
			.buildSessionFactory();
	}

	public User getPerson(String nickName) {

		Session session = factory.getCurrentSession();
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

		Session session = factory.getCurrentSession();
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



}