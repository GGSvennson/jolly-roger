package com.primefaces.hibernate.criteria;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

public class EmployeeDAO {
    
    public EmployeeDAO() {
    }
    
    public static Employees findEmployeeById(SessionFactory sessionFactory, int id) {
    
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Employees.class)
                .add(Restrictions.eq("id", new Integer(id)));
        Employees emp = (Employees) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return emp;
    }
    
    public static Employees findEmployeeByName(SessionFactory sessionFactory, String name) {
        
                Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Employees.class)
                .add(Restrictions.eq("name", name));
        Employees emp = (Employees) criteria.uniqueResult();
        
        tx.commit();
        session.close();
        
        return emp;
        
    }
    
    public static List<Employees> findEmployeesInDepartment(SessionFactory sessionFactory, Department department) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        DetachedCriteria subCriteria = DetachedCriteria.forClass(Department.class)
                .add(Property.forName("departmentId").eq(department.getDepartmentId()))
                .setProjection(Property.forName("id"));

        Criteria criteria = session.createCriteria(Employees.class, "e")
                .createAlias("e.department", "department")
                .add(Subqueries.propertyIn("department.departmentId", subCriteria));
        
        List<Employees> emps = criteria.list();
        
        tx.commit();
        session.close();
        
        return emps;
    }
    
    public static void addEmployeesToDepartment(SessionFactory sessionFactory, Department department, List<Employees> employees) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();

        //Save the Model object
        employees.stream().forEach((emp) -> {
            emp.setDepartment(department);
            session.saveOrUpdate(emp);
        });

        //Commit transaction
        session.getTransaction().commit();

        session.close();
    }
    
    public static void addEmployee(SessionFactory sessionFactory, Employees employee, Address address, Department department) {
        
            Session session = sessionFactory.openSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object 
            employee.setAddress(address);
            employee.setDepartment(department);
            session.save(employee);

            //Commit transaction
            session.getTransaction().commit();
            
            session.close();
    }
    
    public static void updateEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();
        
        //start transaction
        session.beginTransaction();
        //Update the Model object
        session.update(employee);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static void deleteEmployee(SessionFactory sessionFactory, Employees employee) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Delete the Model object
        Users user = employee.getUser();
        session.delete(employee);
        session.delete(user);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
    }
    
    public static List<Employees> listEmployees(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        DetachedCriteria subquery = DetachedCriteria.forClass(Department.class);
        subquery.setProjection(Projections.id());

        Criteria criteria = session.createCriteria(Employees.class, "e")
                .createAlias("e.department", "department")
                .add(Subqueries.propertyIn("department.departmentId", subquery))
                .addOrder(Order.asc("department.departmentName"))
                .addOrder(Order.asc("name"))
                .addOrder(Order.asc("jobRole"));
        
        List<Employees> emps = criteria.list();
        
        tx.commit();
        session.close();
        
        return emps;
    }
}
