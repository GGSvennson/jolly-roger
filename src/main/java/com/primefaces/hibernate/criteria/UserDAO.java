/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.LoginConverter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 *
 * @author Administrador
 */
public class UserDAO {
    
    public UserDAO() {
    }
    
    public static Users findUserById(SessionFactory sessionFactory, int id) {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Users.class)
                .add(Restrictions.eq("id", new Integer(id)));
        user = (Users) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return user;
    }
    
    public static Users findUserByUsername(SessionFactory sessionFactory, String username) {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Users.class)
                .add(Restrictions.eq("username", username));
        user = (Users) criteria.uniqueResult();
        
        tx.commit();
        session.close();

        return user;
        
    }
    
    public static Users findUserByPassword(SessionFactory sessionFactory, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        Users user = null;

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String pass = LoginConverter.hash256(password);
        
        Criteria criteria = session.createCriteria(Users.class)
                .add(Restrictions.eq("password", pass));
        user = (Users) criteria.uniqueResult();
        
        tx.commit();
        session.close();

        return user;
        
    }
    
    public static Users findUserByUsernamePassword(SessionFactory sessionFactory, String username, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
    
        Users user = null;

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String pass = LoginConverter.hash256(password);
        
        Criteria criteria = session.createCriteria(Users.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", pass));
        user = (Users) criteria.uniqueResult();
        
        tx.commit();
        session.close();

        return user;
    
    }
    
    public static Users findUserByEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        DetachedCriteria subCriteria = DetachedCriteria.forClass(Employees.class)
                .add(Property.forName("id").eq(employee.getId()))
                .setProjection(Property.forName("id"));

        Criteria criteria = session.createCriteria(Users.class, "u")
                .createAlias("u.employee", "employee")
                .add(Subqueries.propertyIn("employee.id", subCriteria));
        
        user = (Users) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return user;
    }
    
    public static List<Users> listUsers(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Users> users = session.createCriteria(Users.class).list();
        
        tx.commit();
        session.close();
        
        return users;
    }
    
    public static void addUser(SessionFactory sessionFactory, Address address, Department department, Employees employee,
            Users user)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
            Session session = sessionFactory.openSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object
            user.setPassword(LoginConverter.hash256(user.getPassword()));
            user.setEmployee(employee);
            employee.setUser(user);
            employee.setDepartment(department);
            employee.setAddress(address);
            
            session.save(user);

            //Commit transaction
            session.getTransaction().commit();
            
            session.close();
    }
    
    public static void updateUser(SessionFactory sessionFactory, Users user) {
        
        Session session = sessionFactory.openSession();
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(user);
        //Commit transaction
        session.getTransaction().commit();
        session.close();
    }
    
    public static void deleteUser(SessionFactory sessionFactory, Users user) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Delete the Model object
        session.delete(user);
        //Commit transaction
        session.getTransaction().commit();
        session.close();
    }
}
