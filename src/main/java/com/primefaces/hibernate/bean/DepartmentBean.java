package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.dao.DepartmentDAO;
import com.primefaces.hibernate.dao.EmployeesDAO;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    
    public DepartmentBean() {
    }
    
    @PostConstruct
    public void init() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departments = departmentDAO.list();
    }
    
    public void checkDepartmentName() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        Department dpt = departmentDAO.findByName(newDepartment.getDepartmentName());
        if(null != dpt)
            if(dpt.getDepartmentName().equals("Administration"))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("The Administration department can not be deleted"));
    }
    
    public void createDepartmentFromDialog() {
        if("".equals(newDepartment.getDepartmentName()))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Department name is required"));
        else {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            Department dpt = departmentDAO.findByName(newDepartment.getDepartmentName());
            
            if(null != dpt)
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Department name already exists"));
            else {
                departmentDAO.create(newDepartment);

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
        } else if(selectedDepartment.getDepartmentName().equals("Administration")) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("The Administration department can not be edited"));
        } else {
            // PF('editDepartment').show()
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editDepartment').show()");
        }    
    }
    
    public void editDepartmentFromDialog() {
        if(selectedEmployees.size() > 0)
            selectedDepartment.setEmployees(selectedEmployees);
        
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.update(selectedDepartment);
        
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editDepartment').hide()");
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Department: " + selectedDepartment.getDepartmentName() + " updated"));
    }
    
    public void openDeleteDepartmentDialog() {
        if(null == selectedDepartment)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have to select a department"));
        else {
            String depmtName = selectedDepartment.getDepartmentName();
            if("Administration".equals(depmtName))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("The Administration department can not be deleted"));
            else {
                // PF('deleteDepartment').show()
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('deleteDepartment').show()");
            }
        }
    }
    
    public void deleteDepartment() {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        
        String depmtName = selectedDepartment.getDepartmentName();
        departmentDAO.delete(selectedDepartment);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Department: " + depmtName + " deleted"));

        selectedDepartment = new Department();
    }
    
    public void showDepartments() {
        if(departments.isEmpty()) {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            departments = departmentDAO.list();
        }
    }
    
    public void updateData() {
        departments.clear();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departments = departmentDAO.list();
    }
    
    public void changeDepartment(SelectEvent event) {
        selectedDepartment = (Department) event.getObject();
        deptName = "( " + selectedDepartment.getDepartmentName() + " )";
    }
    
    public void showEmployees() {
        if(null == selectedDepartment) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have to select a row by clicking on it"));
        } else {
            EmployeesDAO employeesDAO = new EmployeesDAO();
            selectedEmployees = employeesDAO.findFromDepartment(selectedDepartment);
        }
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
}
