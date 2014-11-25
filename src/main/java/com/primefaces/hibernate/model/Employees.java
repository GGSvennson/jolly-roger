/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.model;

import java.io.Serializable;
import java.util.Date;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;

/**
 *
 * @author Administrador
 */
public class Employees implements Serializable {
    
    private Integer id;
    private String name;
    private String jobRole;
    private Date insertTime;
    
    //@JoinColumn(name = "address_id", referencedColumnName = "address_id")
    //@ManyToOne(optional = false)
    private Address address;
    
    //@JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    //@OneToOne(optional = false)
    private Users user;
    
    //@JoinColumn(name = "department_id", referencedColumnName = "department_id")
    //@ManyToOne
    private Department department;
    
    public Employees() {
    }
     
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Date getInsertTime() {
        return insertTime;
    }
    
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Employee[%d, %s]", id, name);
    }
}
