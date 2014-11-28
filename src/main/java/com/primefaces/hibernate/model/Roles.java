<<<<<<< HEAD
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
=======
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
>>>>>>> 6a06d0359bb3aa53f4d8274e549eaf3f8c381949
