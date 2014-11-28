package com.primefaces.hibernate.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public abstract class PersistentEnumUserType<T extends PersistentEnum> implements UserType {
    
    private Class<T> clazz = null;
    private static final int[] SQL_TYPES = { Types.VARCHAR };

    protected PersistentEnumUserType(Class<T> c) {
        this.clazz = c;
    }

    @Override
    public int[] sqlTypes() {
       return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return clazz;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor si, Object owner)
         throws HibernateException, SQLException {

        String name = resultSet.getString(names[0]);

        if (resultSet.wasNull())
            return null;
        
        for(Object value : returnedClass().getEnumConstants()) {
            if(name.equals(((PersistentEnum)value).getRole())) {
                return value;
            }
        }
        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value,
         int index, SessionImplementor si) throws HibernateException, SQLException {

        if (null == value) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, ((PersistentEnum)value).getRole());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
         throws HibernateException {
        return original;
    }
    
    public String objectToSQLString(Object value) {
        return '\'' + ((Enum) value).name() + '\'';
    }
 
    public String toXMLString(Object value) {
        return ((Enum) value).name();
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }
}
