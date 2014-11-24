/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.primefaces.hibernate.criteria.UserDAO;
import com.primefaces.hibernate.model.Roles;
import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HibernateUtil;
import com.primefaces.hibernate.util.HttpSessionUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String message;
    private Users user = new Users();
    
    private SessionFactory sessionFactory;
    
    private Roles roleAdmin = Roles.ADMINISTRATOR;
    private Roles roleUser = Roles.USER;
    
    public LoginBean() {
    }
    
    @PostConstruct
    public void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public Roles getRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(Roles roleAdmin) {
        this.roleAdmin = roleAdmin;
    }
    
    public Roles getRoleUser() {
        return roleUser;
    }
    
    public void setRoleUser(Roles roleUser) {
        this.roleUser = roleUser;
    }
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void loginProject() throws IOException, NoSuchAlgorithmException {
        // get Http Session
        HttpSession session = HttpSessionUtil.getSession(true);
        if(session.getAttribute("sessionFactory") == null)
            session.setAttribute("sessionFactory", sessionFactory);
        
        user = UserDAO.findUserByUsernamePassword(sessionFactory, user.getUsername(), user.getPassword());
        
        if (null != user) {
            
            //store user
            session.setAttribute("user", user);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            HttpSessionUtil.redirect(HttpSessionUtil.getRequest().getContextPath() + "/ui/home.jsf");
            
        } else {
            user = new Users();
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Invalid Login!",
                    "Please Try Again!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
 
    public void logout() throws IOException {
        HttpSession session = HttpSessionUtil.getSession(false);
        session.removeAttribute("sessionFactory");
        session.invalidate();
        user = new Users();
        
        HttpSessionUtil.redirect(HttpSessionUtil.getRequest().getContextPath() + "/ui/home.jsf");
    }
    
    /*
    @PreDestroy
    public void destroy() {
        HibernateUtil.closeSessionFactory();
        HttpSession session = HttpSessionUtil.getSession();
        session.invalidate();
    }
    */
}