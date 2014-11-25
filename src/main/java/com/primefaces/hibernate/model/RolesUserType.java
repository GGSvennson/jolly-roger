package com.primefaces.hibernate.model;

public class RolesUserType extends PersistentEnumUserType<Roles> {
    
    public RolesUserType() {     
        super(Roles.class);
    }
    
    @Override
    public Class<Roles> returnedClass() {
        return Roles.class;
    }
}
