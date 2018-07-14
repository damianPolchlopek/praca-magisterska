package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.MeasurementData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class MeasurementCategoryDAO {

    private SessionFactory sessionFactory;

    public MeasurementCategoryDAO() {
        sessionFactory = SessionFact.getInstance().getSessionFactory();
    }

    public List<String> getArrayMeassurement(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            Query<String> theQuery = currentSession.createQuery("select cate.category " +
                    "from MeasurementCategory cate");

            return theQuery.getResultList();
        }
        finally {
            currentSession.getTransaction().commit();
        }
    }

}
