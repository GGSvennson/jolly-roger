<<<<<<< HEAD
package com.primefaces.hibernate.criteria;

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
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Employees.class)
                .add(Restrictions.eq("department", department));
        List<Employees> emps = criteria.list();
        
        while(!emps.isEmpty()) {
            Employees emp = emps.remove(0);
            Users usr = emp.getUser();
            session.delete(emp);
            session.delete(usr);
            session.flush();
            session.clear();
        }
        session.delete(department);
        
        tx.commit();
        session.close();
    }
    
    public static List<Department> listDepartmentsExceptAdministration(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        DetachedCriteria subquery = DetachedCriteria.forClass(Department.class)
                .add(Restrictions.like("departmentName", "Administration"))
                .setProjection(Projections.property("departmentName"));

        Criteria criteria = session.createCriteria(Department.class)
                .add(Subqueries.propertyNotIn("departmentName", subquery))
                .addOrder(Order.asc("departmentName"));
        
        List<Department> depts = criteria.list();
        
        tx.commit();
        session.close();
        
        return depts;
    }
    
    public static List<Department> listDepartments(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Department> depts = session.createCriteria(Department.class)
                .addOrder(Order.asc("departmentName"))
                .list();
        
        tx.commit();
        session.close();
        
        return depts;
    }    
}
=======
package com.primefaces.hibernate.criteria;

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
        Transaction tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(Employees.class)
                .add(Restrictions.eq("department", department));
        List<Employees> emps = criteria.list();
        
        while(!emps.isEmpty()) {
            Employees emp = emps.remove(0);
            Users usr = emp.getUser();
            session.delete(emp);
            session.delete(usr);
            session.flush();
            session.clear();
        }
        session.delete(department);
        
        tx.commit();
        session.close();
    }
    
    public static List<Department> listDepartmentsExceptAdministration(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        DetachedCriteria subquery = DetachedCriteria.forClass(Department.class)
                .add(Restrictions.like("departmentName", "Administration"))
                .setProjection(Projections.property("departmentName"));

        Criteria criteria = session.createCriteria(Department.class)
                .add(Subqueries.propertyNotIn("departmentName", subquery))
                .addOrder(Order.asc("departmentName"));
        
        List<Department> depts = criteria.list();
        
        tx.commit();
        session.close();
        
        return depts;
    }
    
    public static List<Department> listDepartments(SessionFactory sessionFactory) {
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        List<Department> depts = session.createCriteria(Department.class)
                .addOrder(Order.asc("departmentName"))
                .list();
        
        tx.commit();
        session.close();
        
        return depts;
    }    
}
>>>>>>> 6a06d0359bb3aa53f4d8274e549eaf3f8c381949
