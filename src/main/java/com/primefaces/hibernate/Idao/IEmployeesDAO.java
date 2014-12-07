package com.primefaces.hibernate.Idao;

import com.primefaces.hibernate.generic.GenericDao;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import java.util.List;

public interface IEmployeesDAO extends GenericDao<Employees, Integer> {
    
    public void create(Employees employee, Address address, Department department);
    public void addToDepartment(Department department, List<Employees> employees);
    @Override
    public void delete(Employees employee);
    public Employees findByName(String name);
    public List<Employees> findFromDepartment(Department department);
    public List<Employees> list();
}
