package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.dao.DepartmentDAO;
import com.primefaces.hibernate.dao.EmployeeDAO;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.util.HttpSessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "departmentBean")
@ViewScoped
public class DepartmentBean implements Serializable {
    
    private List<Department> departments = new ArrayList<>();
    private Department newDepartment = new Department();
    private Department selectedDepartment = new Department();
    private List<Employees> selectedEmployees = new ArrayList<>();
    private String deptName;
    
    private SessionFactory sessionFactory;
    
    public DepartmentBean() {
    }
    
    @PostConstruct
    public void init() {
        HttpSession session = HttpSessionUtil.getSession(false);
        sessionFactory = (SessionFactory) session.getAttribute("sessionFactory");
        departments = DepartmentDAO.listDepartments(sessionFactory);
    }
    
    /*
    public void checkDepartmentName() {
        Department dpt = DepartmentDAO.findDepartmentByName(sessionFactory, newDepartment.getDepartmentName());
        if(null != dpt)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Department name already exists"));
    }
    */
    
    public void createDepartmentFromDialog() {
        if("".equals(newDepartment.getDepartmentName()))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Department name is required"));
        else {
            Department dpt = DepartmentDAO.findDepartmentByName(sessionFactory, newDepartment.getDepartmentName());
            if(null != dpt)
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Department name already exists"));
            else {
                DepartmentDAO.addDepartment(sessionFactory, newDepartment);

                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('createDepartment').hide()");

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Department: " + newDepartment.getDepartmentName() + " created"));

                newDepartment = new Department();
            }
        }
    }
    
    public void openEditDepartmentDialog() {
        if(null == selectedDepartment) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have to select a row by clicking on it"));
        } else {
            // PF('editDepartment').show()
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editDepartment').show()");
        }    
    }
    
    public void editDepartmentFromDialog() {
        if(selectedEmployees.size() > 0)
            selectedDepartment.setEmployees(selectedEmployees);
        
        DepartmentDAO.updateDepartment(sessionFactory, selectedDepartment);
        
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editDepartment').hide()");
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Department: " + selectedDepartment.getDepartmentName() + " updated"));
    }
    
    public void deleteDepartment() {
        if(null == selectedDepartment)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have to select a department"));
        else {
            String deptName = selectedDepartment.getDepartmentName();

            DepartmentDAO.deleteDepartment(sessionFactory, selectedDepartment);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Department: " + deptName + " deleted"));

            selectedDepartment = new Department();
        }
    }
    
    public void showDepartments() {
        if(departments.isEmpty())
            departments = DepartmentDAO.listDepartments(sessionFactory);
    }
    
    public void updateData() {
        departments.clear();
        departments = DepartmentDAO.listDepartments(sessionFactory);
    }
    
    public void changeDepartment(SelectEvent event) {
        selectedDepartment = (Department) event.getObject();
        deptName = "( " + selectedDepartment.getDepartmentName() + " )";
        //showEmployees();
    }
    
    public void showEmployees() {
        if(null == selectedDepartment)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have to select a row by clicking on it"));
        else
            selectedEmployees = EmployeeDAO.findEmployeesInDepartment(sessionFactory, selectedDepartment);
        
        selectedDepartment = new Department();
    }
    
    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Employees> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<Employees> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
}
