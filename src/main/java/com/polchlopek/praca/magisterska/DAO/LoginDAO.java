package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.Login;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
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

}
