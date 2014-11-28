package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Employees;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class AddressDAO {
    
    public AddressDAO() {
    }
    
    public static Address findAddressById(SessionFactory sessionFactory, short id) {
        
        Address address;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Address.class)
                .add(Restrictions.eq("addressId", new Short(id)));
        address = (Address) criteria.uniqueResult();
        
        tx.commit();
        session.close();
            
        return address;
    }
    
    public static Address findAddressByName(SessionFactory sessionFactory, String address, String address2) {
        
        Address myAddress;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Address.class)
                .add(Restrictions.eq("address", address));
        
        if(null != address2)
            criteria.add(Restrictions.eq("address2", address2));
        
        myAddress = (Address) criteria.uniqueResult();
        
        tx.commit();
        session.close();
            
        return myAddress;
    }
    
    public static Short findAddressOfEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Employees.class, "e")
                .createAlias("e.address", "address")
                .add(Restrictions.eq("id", employee.getId()));
        
        ProjectionList columns = Projections.projectionList()
                .add(Projections.property("address.addressId"))
                .add(Projections.property("address.address"))
                .add(Projections.property("address.address2"));
        criteria.setProjection(columns);
        
        List<Object[]> list = criteria.list();
        String addrs = "";
        Short addressId = (short)0;
        for(Object[] arr : list){
            int cont = 1;
            for(Object ob: arr) {
                if(null != ob)
                    if(ob instanceof Short)
                        addressId = (Short)ob;
                    else if(ob instanceof String) {
                        if(cont == 1)
                            addrs += (String)ob + " ";
                        else
                            addrs += (String)ob;
                        
                        cont++;
                    }
            }
        }
        
        Map<Short, String> address = new HashMap<>();
        address.put(addressId, addrs);
        
        tx.commit();
        session.close();
        
        return addressId;
    }
    
    public static List<Address> findAddressesOfCity(SessionFactory sessionFactory, City city) {
        
        List<Address> addressList;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        addressList = (List<Address>) session.createCriteria(Address.class)
                .add(Restrictions.eq("city", city))
                .list();
        
        tx.commit();
        session.close();
        
        return addressList;
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
        Transaction tx = session.beginTransaction();
        
        List<Address> addresses = session.createCriteria(Address.class).list();
        
        tx.commit();
        session.close();
        
        return addresses;
    }
}
