/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.model;

/**
 *
 * @author Administrador
 */
public enum Roles implements PersistentEnum {
    
    ADMINISTRATOR("Administrator"), USER("User");
    
    private final String role;
    
    Roles(String role) {
        this.role = role;
    }
    
    @Override
    public String getRole() {
        return role;
    }
}
