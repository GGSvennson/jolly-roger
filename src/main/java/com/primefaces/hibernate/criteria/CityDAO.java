package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class CityDAO {
    
    public CityDAO() {
        
    }
    
    public static City findCityById(SessionFactory sessionFactory, short id) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(City.class)
                .add(Restrictions.eq("cityId", new Short(id)));
        City city = (City) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return city;
    }
    
    public static City findCityByName(SessionFactory sessionFactory, String name) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(City.class)
                .add(Restrictions.eq("city", name));
        City city = (City) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return city;
        
    }
    
    public static City findCityOfAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Short> cityIds = session.createCriteria(Address.class, "a")
                .createAlias("a.city", "city")
                .add(Restrictions.idEq(address.getAddressId()))
                .setProjection(Property.forName("city.cityId"))
                .list();

        Criteria criteria = session.createCriteria(City.class)
                .add(Restrictions.in("cityId", cityIds));
        
        City city = (City) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return city;
    }
    
    public static List<City> findCitiesOfCountry(SessionFactory sessionFactory, Country country) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<City> cityList = (List<City>) session.createCriteria(City.class)
                .add(Restrictions.eq("country", country))
                .list();
        
        tx.commit();
        session.close();
        
        return cityList;
    }
    
    public static void addCity(SessionFactory sessionFactory, Country country, City city) {
        
            Session session = sessionFactory.openSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object 
            city.setCountry(country);
            session.save(city);

            //Commit transaction
            session.getTransaction().commit();
            
            session.close();
    }
    
    public static void updateCity(SessionFactory sessionFactory, City city) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(city);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static void deleteCity(SessionFactory sessionFactory, City city) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Delete the Model object
        session.delete(city);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<City> listCities(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<City> cities = session.createCriteria(City.class).list();
        
        tx.commit();
        session.close();
        
        return cities;
    }
}
