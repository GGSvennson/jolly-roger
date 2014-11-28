<<<<<<< HEAD
package com.primefaces.hibernate.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters -----------------------------------------------------------------------------------

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    // Setters -----------------------------------------------------------------------------------

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

=======
package com.primefaces.hibernate.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters -----------------------------------------------------------------------------------

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    // Setters -----------------------------------------------------------------------------------

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

>>>>>>> 6a06d0359bb3aa53f4d8274e549eaf3f8c381949
}