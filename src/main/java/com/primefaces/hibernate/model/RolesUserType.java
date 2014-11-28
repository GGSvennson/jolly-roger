<<<<<<< HEAD
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
=======
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
>>>>>>> 6a06d0359bb3aa53f4d8274e549eaf3f8c381949
