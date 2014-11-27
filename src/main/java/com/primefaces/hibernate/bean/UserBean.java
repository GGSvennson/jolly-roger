package com.primefaces.hibernate.bean;

import com.primefaces.hibernate.criteria.AddressDAO;
import com.primefaces.hibernate.criteria.CityDAO;
import com.primefaces.hibernate.criteria.CountryDAO;
import com.primefaces.hibernate.criteria.DepartmentDAO;
import com.primefaces.hibernate.criteria.EmployeeDAO;
import com.primefaces.hibernate.criteria.UserDAO;
import com.primefaces.hibernate.model.Address;
import com.primefaces.hibernate.model.City;
import com.primefaces.hibernate.model.Country;
import com.primefaces.hibernate.model.Department;
import com.primefaces.hibernate.model.Employees;
import com.primefaces.hibernate.model.Roles;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
 
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.primefaces.hibernate.model.Users;
import com.primefaces.hibernate.util.HttpSessionUtil;
import com.primefaces.hibernate.util.LoginConverter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {
    
    private Users newUser = new Users();
    private Employees newEmployee = new Employees();
    private Department newDepartment = new Department();
    private Address newAddress = new Address();
    private City newCity = new City();
    private Country newCountry = new Country();
    private Users selectedUser = new Users();
    private Employees selectedEmployee = new Employees();
    private Department selectedDepartment = new Department();
    private Address selectedAddress = new Address();
    private City selectedCity = new City();
    private Country selectedCountry = new Country();
    private List<Users> users = new ArrayList<>();
    private List<Employees> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    private Roles[] roleses;
    
    private Users tmpUser = new Users();
    
    private final Roles roleAdmin = Roles.ADMINISTRATOR;
    private final Roles roleUser = Roles.USER;
    
    private int employeeId;
    
    private String password;
    private String newPassword;
    private String repeatedPassword;
    
    private SessionFactory sessionFactory;
    
    public UserBean() {
    }
    
    @PostConstruct
    public void initUsers() {
        HttpSession session = HttpSessionUtil.getSession(false);
        sessionFactory = (SessionFactory) session.getAttribute("sessionFactory");
        
        selectedUser = (Users) session.getAttribute("user");
        //selectedEmployee = selectedUser.getEmployee();
        
        users = UserDAO.listUsers(sessionFactory);
        
        employees = EmployeeDAO.listEmployees(sessionFactory);
        departments = DepartmentDAO.listDepartmentsExceptAdministration(sessionFactory);
        addresses = AddressDAO.listAddress(sessionFactory);
        cities = CityDAO.listCities(sessionFactory);
        countries = CountryDAO.listCountries(sessionFactory);
    }
    
    public void changePasswordFromDialog() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(!LoginConverter.hash256(password).equals(selectedUser.getPassword())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Employee passwords not matched"));
        } else if(!newPassword.equals(repeatedPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Employee new password is not correct"));
        } else {
            selectedUser.setPassword(LoginConverter.hash256(newPassword));
            UserDAO.updateUser(sessionFactory, selectedUser);
            
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('changePasswordEmployee').hide()");
        
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Employee password changed"));
        }
    }
    
    public void checkUsername() {
        Users user = UserDAO.findUserByUsername(sessionFactory, newUser.getUsername());
        if(null != user)
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Username already exists"));
    }
    
    public void createEmployeeFromDialog() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if( ("".equals(newUser.getUsername())) ||
                ("".equals(newUser.getPassword()))
                || ("".equals(password))
                || (null == newUser.getRoles())
                || ("".equals(newEmployee.getName()))
                || ("".equals(newEmployee.getJobRole()))
                || (null == newEmployee.getInsertTime())
                || (null == newCountry)
                || (null == newCity)
                || (null == newAddress)
                || (null == newDepartment) ) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You can not leave any field empty"));
        } else if(!password.equals(newUser.getPassword()))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Passwords not match"));
        else {
            UserDAO.addUser(sessionFactory, newAddress, newDepartment, newEmployee, newUser);

            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('createEmployee').hide()");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Employee: " + newUser.getEmployee().getName() + " created"));

            reset();
        }
    }
    
    public void openEditEmployeeDialog() {
        // PF('editEmployee').show()
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editEmployee').show()");
    }
    
    public void closeEditEmployeeDialog() {
        reset();
        // PF('editEmployee').hide()
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('editEmployee').hide()");
    }
    
    public void editEmployeeFromDialog() {
        if( ("".equals(selectedEmployee.getName()))
                || ("".equals(selectedEmployee.getJobRole()))
                || (null == selectedEmployee.getInsertTime())
                || (null == newCountry)
                || (null == newCity)
                || (null == newAddress)
                || (null == newDepartment) ) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("You can not leave any input field empty"));
            
        } else {
            EmployeeDAO.updateEmployee(sessionFactory, selectedEmployee);

            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('editEmployee').hide()");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Employee: " + selectedEmployee.getName() + " updated"));

            reset();
        }
    }
    
    public void deleteEmployee() {
        if(selectedEmployee.equals(selectedUser.getEmployee())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("The logged in user can not be deleted"));
        } else {
            String name = selectedEmployee.getName();
            EmployeeDAO.deleteEmployee(sessionFactory, selectedEmployee);
        
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Employee: " + name + " deleted"));
        
            reset();
        }
    }
    
    public void showEmployees() {
        if(employees.isEmpty())
            employees = EmployeeDAO.listEmployees(sessionFactory);
    }
    
    public void updateData() {
        users.clear();
        employees.clear();
        users = UserDAO.listUsers(sessionFactory);
        employees = EmployeeDAO.listEmployees(sessionFactory);
    }
    
    public void changeEmployee(SelectEvent event) {
        selectedEmployee = (Employees) event.getObject();
        Users user = UserDAO.findUserByEmployee(sessionFactory, selectedEmployee);
        if(user.getRoles() == Roles.USER) {
            tmpUser = user;
            selectedDepartment = DepartmentDAO.findDepartmentOfEmployee(sessionFactory, selectedEmployee);
            Short addressId = AddressDAO.findAddressOfEmployee(sessionFactory, selectedEmployee);
            selectedAddress = AddressDAO.findAddressById(sessionFactory, addressId);
            selectedCity = CityDAO.findCityOfAddress(sessionFactory, selectedAddress);
            selectedCountry = CountryDAO.findCountryOfCity(sessionFactory, selectedCity);
            //openEditEmployeeDialog();
        } else {
            selectedEmployee = new Employees();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("The admin user can not be edited"));
        }
    }
    
    public void loadCities() {
        cities = CityDAO.findCitiesOfCountry(sessionFactory, newCountry);
    }
    
    public void loadAddresses() {
        addresses = AddressDAO.findAddressesOfCity(sessionFactory, newCity);
    }
    
    private void reset() {
        tmpUser = new Users();
        selectedUser = new Users();
        newUser = new Users();
        selectedEmployee = new Employees();
        newEmployee = new Employees();
        selectedDepartment = new Department();
        newDepartment = new Department();
        selectedAddress = new Address();
        newAddress = new Address();
        selectedCity = new City();
        newCity = new City();
        selectedCountry = new Country();
        newCountry = new Country();
    }
    
    public Users getNewUser() {
        return newUser;
    }

    public void setNewUser(Users newUser) {
        this.newUser = newUser;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Employees getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employees selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public Employees getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employees newEmployee) {
        this.newEmployee = newEmployee;
    }

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public City getNewCity() {
        return newCity;
    }

    public void setNewCity(City newCity) {
        this.newCity = newCity;
    }

    public Country getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }
    
    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Roles[] getRoleses() {
        roleses = Roles.values();
        return roleses;
    }

    public void setRoleses(Roles[] roleses) {
        this.roleses = roleses;
    }

    public Users getTmpUser() {
        return tmpUser;
    }

    public void setTmpUser(Users tmpUser) {
        this.tmpUser = tmpUser;
    }

    public Roles getRoleAdmin() {
        return roleAdmin;
    }

    public Roles getRoleUser() {
        return roleUser;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
}
