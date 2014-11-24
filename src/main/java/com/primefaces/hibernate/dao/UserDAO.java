/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.LoginConverter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

        //start transaction
        session.beginTransaction();
        //Save the Model object
        user = (Users) session.get(Users.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
            
        return user;
    }
    
    public static Users findUserByUsername(SessionFactory sessionFactory, String username) {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        
        String hql = "from Users u where u.username = :username";
        List<Users> users =
                (List<Users>) session.createQuery(hql)
                        .setString("username", username)
                        .list();
        if(users.size() > 0)
            user = users.get(0);
        
        session.getTransaction().commit();
        
        session.close();

        return user;
        
    }
    
    public static Users findUserByPassword(SessionFactory sessionFactory, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String pass = LoginConverter.hash256(password);
        
        String hql = "from Users u where u.password = :password";
        List<Users> users =
                (List<Users>) session.createQuery(hql)
                        .setString("password", pass)
                        .list();
        if(users.size() > 0)
            user = users.get(0);
        
        session.getTransaction().commit();
        
        session.close();

        return user;
        
    }
    
    public static Users findUserByUsernamePassword(SessionFactory sessionFactory, String username, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String pass = LoginConverter.hash256(password);
        
        String hql = "from Users u where u.username = :username and u.password = :password";
        List<Users> users =
                (List<Users>) session.createQuery(hql)
                        .setString("username", username)
                        .setString("password", pass)
                        .list();
        if(users.size() > 0)
            user = users.get(0);
        
        session.getTransaction().commit();
        
        session.close();

        return user;
    }
    
    public static Users findUserByEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Users user = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        
        String hql = "from Users u join fetch u.employee where u.employee = :employee";
        List<Users> users =
                (List<Users>) session.createQuery(hql)
                        .setEntity("employee", employee)
                        .list();
        if(users.size() > 0)
            user = users.get(0);
        
        session.getTransaction().commit();
        
        session.close();

        return user;
        
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
    
    public static List<Users> listUsers(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        //start transaction
        session.beginTransaction();
        List<Users> users = (List<Users>) session.createQuery("from Users").list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return users;
    }
}
