/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primefaces.hibernate.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author Administrador
 */
public class RolesUserType extends PersistentEnumUserType<Roles> {
    
    public RolesUserType() {     
        super(Roles.class);
    }
    
    @Override
    public Class<Roles> returnedClass() {
        return Roles.class;
    }
}
