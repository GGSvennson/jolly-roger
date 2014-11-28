package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CountryDAO {
    
    public CountryDAO() {
    }
    
    public static Country findCountryById(SessionFactory sessionFactory, short id) {
        
        Country country;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        country = (Country) session.get(Country.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return country;
    }
    
    public static Country findCountryByName(SessionFactory sessionFactory, String name) {
        
        Country country = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        String hql = "from Country c where c.country = :name";
        List<Country> result = session.createQuery(hql)
        .setString("name", name)
        .list();
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        if(result.size() > 0)
            country = result.get(0);
        
        return country;
    }
    
    public static Country findCountryOfCity(SessionFactory sessionFactory, City city) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from City c join fetch c.country where c.cityId = :cityId";
        City ct = (City) session.createQuery(hql)
                            .setShort("cityId", city.getCityId())
                            .list().get(0);
        Country country = ct.getCountry();
        
        //Commit transaction
        session.getTransaction().commit();
        
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
        //start transaction
        session.beginTransaction();
        List<Country> countries =
                (List<Country>) session.createQuery("from Country c order by c.country")
                        .list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return countries;
    }
}
