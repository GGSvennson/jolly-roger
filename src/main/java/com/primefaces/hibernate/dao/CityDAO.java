package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CityDAO {
    
    public CityDAO() {
        
    }
    
    public static City findCityById(SessionFactory sessionFactory, short id) {
        
        City city;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        city = (City) session.get(City.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
            
        return city;
    }
    
    public static City findCityByName(SessionFactory sessionFactory, String name) {
        
        City city = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String hql = "from City c where c.city = :name";
        List<City> result = session.createQuery(hql)
        .setString("name", name)
        .list();
        
        session.getTransaction().commit();
        
        session.close();

        if(result.size() > 0) {
            city = result.get(0);
        }
        
        return city;
        
    }
    
    public static City findCityOfAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Address a join fetch a.city where a.addressId = :addressId";
        Address addr = (Address) session.createQuery(hql)
                                .setShort("addressId", address.getAddressId())
                                .list().get(0);
        City city = addr.getCity();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return city;
    }
    
    public static List<City> findCitiesOfCountry(SessionFactory sessionFactory, Country country) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String hql = "from City c where c.country = :country";
        List<City> cityList = session.createQuery(hql)
        .setEntity("country", country)
        .list();
        
        session.getTransaction().commit();
        
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
        //start transaction
        session.beginTransaction();
        List<City> cities =
                (List<City>) session.createQuery("from City c order by c.city")
                        .list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return cities;
    }
    
}
