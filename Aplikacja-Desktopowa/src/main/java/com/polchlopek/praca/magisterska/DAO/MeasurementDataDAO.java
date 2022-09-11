package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.MeasurementData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class MeasurementDataDAO {

    private SessionFactory sessionFactory;

    public MeasurementDataDAO() {
        sessionFactory = SessionFact.getInstance().getSessionFactory();
    }

    public List<MeasurementData> getDataMeassurement(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            Query<MeasurementData> theQuery =
                    currentSession.createQuery("from MeasurementData where id_meas=:theId");

            theQuery.setParameter("theId", theId);

            return theQuery.getResultList();
        }
        finally {
            currentSession.getTransaction().commit();
        }

    }

}
