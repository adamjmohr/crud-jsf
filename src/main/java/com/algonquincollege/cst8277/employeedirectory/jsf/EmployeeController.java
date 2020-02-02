/***************************************************************************f******************u************zz*******y**
 * File: EmployeeController.java
 * Course materials (20W) CST 8277
 * @author (original) Mike Norman
 * @author Adam Mohr 040669681
 *
 */
package com.algonquincollege.cst8277.employeedirectory.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.SessionMap;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.algonquincollege.cst8277.employeedirectory.dao.EmployeeDao;
import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
 * Description: Responsible for collection of Employee DTO's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behaviour to DAO
 */

@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {
    
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** inject external context for this managed bean */
    @Inject
    protected ExternalContext externalContext;
    /** inject session map to hold objects between pages */
    @Inject
    @SessionMap
    protected Map<String, Object> sessionMap;
    /** interface DAO to manipulate database */
    protected EmployeeDao employeeDao;
    /** list of employees in application */
    protected List<EmployeeDTO> employees;

    /**
     * Consturctor.
     * 
     * @param employeeDao injected to connect controller with DAO class.
     */
    @Inject
    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * Log messages to console for monitoring application
     * 
     * @param msg the message
     */
    private void logMsg(String msg) {
        ((ServletContext) externalContext.getContext()).log(msg);
    }

    /**
     * Load all the employees from database DAO to the view.
     */
    @PostConstruct
    public void loadEmployees() {
        logMsg("loading all employees");
        setEmployees(employeeDao.readAllEmployees());
    }

    /**
     * Get employees from application.
     * 
     * @return list of employees.
     */
    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    /**
     * Set the employees in application.
     * 
     * @param employees to set.
     */
    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    /**
     * Delete employee using DAO.
     * 
     * @param empId of employee to bne deleted.
     */
    public void deleteEmployee(int empId) {
        employeeDao.deleteEmployeeById(empId);
        loadEmployees();
    }

    /**
     * Edit employee using DAO to the view.
     * 
     * @return edit-employees view.
     * @param id of employee to view in edit layer.
     */
    public String editEmployee(int id) {
        EmployeeDTO e1 = employeeDao.readEmployeeById(id);
        sessionMap.put("editEmployee", e1);
        return "edit-employees?faces-redirect=true";
    }

    /**
     * Save edits to employee database using DAO.
     * 
     * @return back to home page.
     * @param emp to save edits to.
     */
    public String saveEdit(EmployeeDTO emp) {
        employeeDao.updateEmployee(emp);
        return "list-employees?faces-redirect=true";
    }

    /**
     * Transition to create new employee page.
     * 
     * @return new-employees view.
     */
    public String createNewEmployee() {
        sessionMap.put("newEmployee", new EmployeeDTO());
        return "new-employees?faces-redirect=true";
    }

    /**
     * Save new employee to database using DAO.
     * 
     * @return back to home page.
     * @param emp to save.
     */
    public String saveNewEmployee(EmployeeDTO emp) {
        employeeDao.createEmployee(emp);
        return "list-employees?faces-redirect=true";
    }

}