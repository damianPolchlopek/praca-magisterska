package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.Login;
import com.polchlopek.praca.magisterska.entity.Measurement;
import com.polchlopek.praca.magisterska.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public class LoginDAO {

    private SessionFactory sessionFactory;

    public LoginDAO() {
        sessionFactory = SessionFact.getInstance().getSessionFactory();
    }


    public List<Login> getLogins() {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{

            Query<Login> theQuery =
                    currentSession.createQuery("FROM Login log order by log.id desc");
            theQuery.setFirstResult(0);
            theQuery.setMaxResults(10);

            return theQuery.getResultList();
        }
        finally {
            currentSession.getTransaction().commit();
        }

    }

    public void addLogin(User user){

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{

            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            java.sql.Time sqlTime = new java.sql.Time(System.currentTimeMillis());

            Login tmpLogin = new Login(sqlDate, sqlTime, "Krakow", user);
            currentSession.save(tmpLogin);
        }
        finally {
            currentSession.getTransaction().commit();
        }


    }

    public Login getLogin(int theId){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            return currentSession.get(Login.class, theId);
        }
        finally {
            currentSession.getTransaction().commit();
        }
    }

    public void deleteLogin(Login login){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            currentSession.delete(login);
        }
        finally {
            currentSession.getTransaction().commit();
        }
    }





}
