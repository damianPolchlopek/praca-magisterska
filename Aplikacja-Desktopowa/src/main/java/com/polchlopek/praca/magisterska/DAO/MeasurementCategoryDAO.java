package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.MeasurementCategory;
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


    public List<String> getMeasurementCategories() {

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

    public MeasurementCategory getMeasurementCategory(String category) {

        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            Query<MeasurementCategory> theQuery =
                    currentSession.createQuery("from MeasurementCategory  where category=:categoryParam");
            theQuery.setParameter("categoryParam", category);
            List<MeasurementCategory> measurementCategory = theQuery.getResultList();

            if (measurementCategory.isEmpty()){
                return null;
            }
            else{
                return measurementCategory.get(0);
            }
        }
        finally {
            currentSession.getTransaction().commit();
        }

    }

    public void addMeasurementCategory(MeasurementCategory measurementCategory){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();

        try{
            currentSession.saveOrUpdate(measurementCategory);
        }
        finally {
            currentSession.getTransaction().commit();
        }
    }





}
