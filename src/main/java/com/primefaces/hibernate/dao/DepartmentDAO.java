/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Administrador
 */
public class DepartmentDAO {
    
    public DepartmentDAO() {
    }
    
    public static Department findDepartmentById(SessionFactory sessionFactory, long id) {
        
        Department dept;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Get the Model object
        dept = (Department) session.get(Department.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return dept;
    }
    
    public static Department findDepartmentByName(SessionFactory sessionFactory, String name) {
        
        Department dept = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Get the Model object
        String hql = "from Department d where d.departmentName = :name";
        List<Department> result = session.createQuery(hql)
        .setString("name", name)
        .list();
        //Commit transaction
        session.getTransaction().commit();

        session.close();
        
        if(result.size() > 0)
            dept = result.get(0);
        
        return dept;
    }
    
    public static Department findDepartmentOfEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        
        String hql = "from Employees e join fetch e.department where e.id = :id";
        Employees emp = (Employees) session.createQuery(hql)
                            .setParameter("id", employee.getId())
                            .list().get(0);
        Department dept = emp.getDepartment();
        
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
        
        return dept;
    }
    
    public static void addDepartment(SessionFactory sessionFactory, Department department) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        session.save(department);

        //Commit transaction
        session.getTransaction().commit();

        session.close();
    }
    
    public static void updateDepartment(SessionFactory sessionFactory, Department department) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(department);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static void deleteDepartment(SessionFactory sessionFactory, Department department) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        String hql = "from Employees e where e.department = :department";
        List<Employees> emps = (List<Employees>) session.createQuery(hql)
                                .setEntity("department", department)
                                .list();
        
        while(!emps.isEmpty()) {
            Employees emp = emps.remove(0);
            Users usr = emp.getUser();
            session.delete(emp);
            session.delete(usr);
            session.flush();
            session.clear();
        }
        session.delete(department);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<Department> listDepartments(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        //start transaction
        session.beginTransaction();
        List<Department> depts = (List<Department>) session.createQuery("from Department").list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return depts;
    }
}
