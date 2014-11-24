/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.util;

import org.apache.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Administrador
 */
public class HibernateUtil {
    
    private static final Logger LOG = Logger.getLogger(HibernateUtil.class);
    
    //XML based configuration
    private static SessionFactory sessionFactory;
    private static ServiceRegistry builder;
    
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("/com/primefaces/hibernate/hbm/hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");
             
            builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");
             
            SessionFactory sFactory = configuration.buildSessionFactory(builder);
             
            return sFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            LOG.error("Initial SessionFactory creation failed, " + ex.getMessage(), ex);
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
     
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
    
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            try {
                StandardServiceRegistryBuilder.destroy(builder);
            } catch (Exception e) {
                LOG.error("SessionFactory destroy failed, " + e.getMessage(), e);
            }
        }
    }
}
