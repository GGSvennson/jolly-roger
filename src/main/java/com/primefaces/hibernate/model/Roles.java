package com.primefaces.hibernate.model;

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
