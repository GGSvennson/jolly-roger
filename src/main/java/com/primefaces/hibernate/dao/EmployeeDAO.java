package com.primefaces.hibernate.dao;

import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Users;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class EmployeeDAO {
    
    public EmployeeDAO() {
    }
    
    public static Employees findEmployeeById(SessionFactory sessionFactory, int id) {
        
        Employees emp;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object
        emp = (Employees) session.get(Employees.class, id);
        //Commit transaction
        session.getTransaction().commit();
        
        session.close();
            
        return emp;
    }
    
    public static Employees findEmployeeByName(SessionFactory sessionFactory, String name) {
        
        Employees emp = null;
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Save the Model object

        String hql = "from Employees e where e.name = :name";
        List<Employees> result = session.createQuery(hql)
        .setString("name", name)
        .list();
        
        session.getTransaction().commit();
        
        session.close();

        if(result.size() > 0) {
            emp = result.get(0);
        }
        return emp;
        
    }
    
    public static List<Employees> findEmployeesInDepartment(SessionFactory sessionFactory, Department department) {
        
        Session session = sessionFactory.openSession();

        //start transaction
        session.beginTransaction();
        //Get the Model object
        String hql = "from Employees e where e.department = :department";
        List<Employees> result = session.createQuery(hql)
                .setEntity("department", department)
                .list();
        //Commit transaction
        session.getTransaction().commit();

        session.close();
        
        return result;
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
        //start transaction
        session.beginTransaction();
        String hql = "from Employees e join fetch e.department order by e.department.departmentName, e.name, e.jobRole";
        List<Employees> employees = 
                (List<Employees>) session.createQuery(hql)
                        .list();
        //Commit transaction
        session.getTransaction().commit();
        session.close();
        
        return employees;
    }
}
