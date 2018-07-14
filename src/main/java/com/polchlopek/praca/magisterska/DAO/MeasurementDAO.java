package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.Measurement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class MeasurementDAO {

    private SessionFactory sessionFactory;

    public MeasurementDAO() {
        sessionFactory = SessionFact.getInstance().getSessionFactory();
    }


    public List<Measurement> getMeasurements() {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{

            Query<Measurement> theQuery =
                    currentSession.createQuery("from Measurement", Measurement.class);

            theQuery.setFirstResult(0);
            theQuery.setMaxResults(15);

            return theQuery.getResultList();

        }
        finally {
            currentSession.getTransaction().commit();
        }

    }

    public Measurement getMeasurement(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            return currentSession.get(Measurement.class, theId);
        }
        finally {
            currentSession.getTransaction().commit();
        }

    }



}
