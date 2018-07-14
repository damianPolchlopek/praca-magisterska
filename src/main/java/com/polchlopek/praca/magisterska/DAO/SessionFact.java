package com.polchlopek.praca.magisterska.DAO;

import com.polchlopek.praca.magisterska.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFact {
    private static SessionFact ourInstance = new SessionFact();

    public static SessionFact getInstance() {
        return ourInstance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory sessionFactory;

    private SessionFact() {
        sessionFactory = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(Login.class)
                .addAnnotatedClass(Measurement.class)
                .addAnnotatedClass(MeasurementData.class)
                .addAnnotatedClass(MeasurementCategory.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

}
