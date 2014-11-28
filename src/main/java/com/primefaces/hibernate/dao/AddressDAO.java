<<<<<<< HEAD
package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AddressDAO {
    
    public AddressDAO() {
    }
    
    public static Address findAddressById(SessionFactory sessionFactory, short id) {
        
        Address address;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        address = (Address) session.get(Address.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
            
        return address;
    }
    
    public static Address findAddressByName(SessionFactory sessionFactory, String address, String address2) {
        
        Address addr = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String hql = "from Address a where a.address = :address and a.address2 = :address2";
        List<Address> result = session.createQuery(hql)
                .setString("address", address)
                .setString("address2", address2)
                .list();
        
        session.getTransaction().commit();
        
        session.close();

        if(result.size() > 0) {
            addr = result.get(0);
        }
        
        return addr;
        
    }
    
    public static List<Address> findAddressesOfCity(SessionFactory sessionFactory, City city) {
        List<Address> addressList;
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Address a where a.city = :city"; //  join fetch c.addressList
        addressList = session.createQuery(hql)
                            .setEntity("city", city)
                            .list();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return addressList;
    }
    
    public static Address findAddressOfEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Employees e join fetch e.address where e.id = :id";
        Employees emp = (Employees) session.createQuery(hql)
                            .setInteger("id", employee.getId())
                            .list().get(0);
        Address addr = emp.getAddress();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return addr;
    }
    
    public static void addEmmployeesToAddress(SessionFactory sessionFactory, Address address, List<Employees> empsList) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        empsList.stream().forEach((emp) -> {
            emp.setAddress(address);
            session.saveOrUpdate(emp);
        });
        
        //Commit transaction
        session.getTransaction().commit();

        session.close();
    }
    
    public static void addAddress(SessionFactory sessionFactory, Country country, City city, Address address) {
        
            Session session = sessionFactory.openSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object
            city.setCountry(country);
            address.setCity(city);
            session.save(address);

            //Commit transaction
            session.getTransaction().commit();
            
            session.close();
    }
    
    public static void updateAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(address);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static void deleteAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Delete the Model object
        session.delete(address);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<Address> listAddress(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        //start transaction
        session.beginTransaction();
        List<Address> addresses = (List<Address>) session.createQuery("from Address").list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return addresses;
    }
}
=======
package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AddressDAO {
    
    public AddressDAO() {
    }
    
    public static Address findAddressById(SessionFactory sessionFactory, short id) {
        
        Address address;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        address = (Address) session.get(Address.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
            
        return address;
    }
    
    public static Address findAddressByName(SessionFactory sessionFactory, String address, String address2) {
        
        Address addr = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String hql = "from Address a where a.address = :address and a.address2 = :address2";
        List<Address> result = session.createQuery(hql)
                .setString("address", address)
                .setString("address2", address2)
                .list();
        
        session.getTransaction().commit();
        
        session.close();

        if(result.size() > 0) {
            addr = result.get(0);
        }
        
        return addr;
        
    }
    
    public static List<Address> findAddressesOfCity(SessionFactory sessionFactory, City city) {
        List<Address> addressList;
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Address a where a.city = :city"; //  join fetch c.addressList
        addressList = session.createQuery(hql)
                            .setEntity("city", city)
                            .list();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return addressList;
    }
    
    public static Address findAddressOfEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Employees e join fetch e.address where e.id = :id";
        Employees emp = (Employees) session.createQuery(hql)
                            .setInteger("id", employee.getId())
                            .list().get(0);
        Address addr = emp.getAddress();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return addr;
    }
    
    public static void addEmmployeesToAddress(SessionFactory sessionFactory, Address address, List<Employees> empsList) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        empsList.stream().forEach((emp) -> {
            emp.setAddress(address);
            session.saveOrUpdate(emp);
        });
        
        //Commit transaction
        session.getTransaction().commit();

        session.close();
    }
    
    public static void addAddress(SessionFactory sessionFactory, Country country, City city, Address address) {
        
            Session session = sessionFactory.openSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object
            city.setCountry(country);
            address.setCity(city);
            session.save(address);

            //Commit transaction
            session.getTransaction().commit();
            
            session.close();
    }
    
    public static void updateAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(address);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static void deleteAddress(SessionFactory sessionFactory, Address address) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Delete the Model object
        session.delete(address);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<Address> listAddress(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        //start transaction
        session.beginTransaction();
        List<Address> addresses = (List<Address>) session.createQuery("from Address").list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return addresses;
    }
}
>>>>>>> 6a06d0359bb3aa53f4d8274e549eaf3f8c381949
