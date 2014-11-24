/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Administrador
 */
public class CountryDAO {
    
    public CountryDAO() {
    }
    
    public static Country findCountryById(SessionFactory sessionFactory, short id) {
        
        Country country;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Country.class)
                .add(Restrictions.eq("countryId", new Short(id)));
        country = (Country) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return country;
    }
    
    public static Country findCountryByName(SessionFactory sessionFactory, String name) {
        
        Country country;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Country.class)
                .add(Restrictions.eq("country", name));
        country = (Country) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return country;
    }
    
    public static Country findCountryOfCity(SessionFactory sessionFactory, City city) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Short> countryIds = session.createCriteria(City.class, "c")
                .createAlias("c.country", "country")
                .add(Restrictions.idEq(city.getCityId()))
                .setProjection(Property.forName("country.countryId"))
                .list();

        Criteria criteria = session.createCriteria(Country.class)
                .add(Restrictions.in("countryId", countryIds));
        
        Country country = (Country) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return country;
    }
    
    public static void addCitiesToCountry(SessionFactory sessionFactory, Country country, List<City> cityList) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        cityList.stream().forEach((city) -> {
            city.setCountry(country);
            session.saveOrUpdate(city);
        });
        
        //Commit transaction
        session.getTransaction().commit();

        session.close();
    }
    
    public static void addCountry(SessionFactory sessionFactory, Country country) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        session.save(country);

        //Commit transaction
        session.getTransaction().commit();

        session.close();
        
    }
    
    public static void updateCountry(SessionFactory sessionFactory, Country country) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        session.update(country);

        //Commit transaction
        session.getTransaction().commit();

        session.close();
        
    }
    
    public static void deleteCountry(SessionFactory sessionFactory, Country country) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.delete(country);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<Country> listCountries(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Country> countries = session.createCriteria(Country.class).list();
        
        tx.commit();
        session.close();
        
        return countries;
    }
}
