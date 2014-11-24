/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
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
public class DepartmentDAO {
    
    public DepartmentDAO() {
    }
    
    public static Department findDepartmentById(SessionFactory sessionFactory, long id) {
        
        Department dept;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Department.class)
                .add(Restrictions.eq("departmentId", new Long(id)));
        dept = (Department) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return dept;
    }
    
    public static Department findDepartmentByName(SessionFactory sessionFactory, String name) {
        
        Department dept;
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Department.class)
                .add(Restrictions.eq("departmentName", name));
        dept = (Department) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return dept;
    }
    
    public static Department findDepartmentOfEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Long> deptIds = session.createCriteria(Employees.class, "e")
                .createAlias("e.department", "department")
                .add(Restrictions.idEq(employee.getId()))
                .setProjection(Property.forName("department.departmentId"))
                .list();

        Criteria criteria = session.createCriteria(Department.class)
                .add(Restrictions.in("departmentId", deptIds));
        
        Department dept = (Department) criteria.uniqueResult();
        
        tx.commit();
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
        Transaction tx = session.beginTransaction();
        
        List<Department> depts = session.createCriteria(Department.class).list();
        
        tx.commit();
        session.close();
        
        return depts;
    }    
}
