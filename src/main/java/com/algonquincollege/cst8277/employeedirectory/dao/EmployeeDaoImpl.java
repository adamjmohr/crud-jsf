/***************************************************************************f******************u************zz*******y**
 * File: EmployeeDaoImpl.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * @author Adam Mohr 040669681
 *
 */
package com.algonquincollege.cst8277.employeedirectory.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
* Description: Implements the C-R-U-D API for the database
*/

@Named
@ApplicationScoped
public class EmployeeDaoImpl implements EmployeeDao, Serializable {
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** directory for resources */
    private static final String EMPLOYEE_DS_JNDI = 
        "java:app/jdbc/employeeDirectory";
    /** sql for reading all employees */
    private static final String READ_ALL = 
        "select * from employee";
    /** sql for reading a specifc employee */
    private static final String READ_EMPLOYEE_BY_ID =
        "select * from employee where id = ?";
    /** sql for updating a specifc employee */
    private static final String UPDATE_EMPLOYEE_ALL_FIELDS =
        "update employee set updated_date = ?, fname = ?, lname = ?, email = ?, title = ?, salary = ? where id = ?";
    /** sql for deleting a specifc employee */
    private static final String DELETE_EMPLOYEE_BY_ID =
        "delete from employee where id = ?";
    /** sql for creating a new employee */
    private static final String CREATE_NEW_EMPLOYEE =
        "insert into employee (created_date, fname, lname, email, title, salary) values (?, ?, ?, ?, ?, ?)";

    /** inject external context for this managed bean */
    @Inject
    protected ExternalContext externalContext;
    
    /**
     * Log messages to console for monitoring application
     * @param msg the message
     */
    private void logMsg(String msg) {
        ((ServletContext)externalContext.getContext()).log(msg);
    }

    /** datasource for database */
    @Resource(lookup = EMPLOYEE_DS_JNDI)
    protected DataSource employeeDS;
    /** connect to employee database */
    protected Connection conn;
    /** sql statement to retrieve all employees from database */
    protected PreparedStatement readAllPstmt;
    /** sql statement to retrieve a specific employee from database */
    protected PreparedStatement readByIdPstmt;
    /** sql statement to create new employee in database */
    protected PreparedStatement createPstmt;
    /** sql statement to update an employee from database */
    protected PreparedStatement updatePstmt;
    /** sql statement to delete an employee from database */
    protected PreparedStatement deleteByIdPstmt;

    /**
     * Build connection to database and prepare sql statements.
     */
    @PostConstruct
    protected void buildConnectionAndStatements() {
        try {
            logMsg("building connection and stmts");
            conn = employeeDS.getConnection();
            readAllPstmt = conn.prepareStatement(READ_ALL);
            readByIdPstmt = conn.prepareStatement(READ_EMPLOYEE_BY_ID);
            updatePstmt = conn.prepareStatement(UPDATE_EMPLOYEE_ALL_FIELDS);
            deleteByIdPstmt = conn.prepareStatement(DELETE_EMPLOYEE_BY_ID);
            createPstmt = conn.prepareStatement(CREATE_NEW_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
        }
    }

    /**
     * Close connection to database and close sql statements.
     */
    @PreDestroy
    protected void closeConnectionAndStatements() {
        try {
            logMsg("closing stmts and connection");
            readAllPstmt.close();
            readByIdPstmt.close();
            createPstmt.close();
            updatePstmt.close();
            deleteByIdPstmt.close();

            conn.close();
        } catch (Exception e) {
            logMsg("something went wrong closing stmts or connection: " + e.getLocalizedMessage());
        }
    }

    /**
     * Read all the employees from database.
     * 
     * @return the list of employees retrieved from database.
     */
    @Override
    public List<EmployeeDTO> readAllEmployees() {
        logMsg("reading all employees");
        List<EmployeeDTO> employees = new ArrayList<>();
        try {
            ResultSet rs = readAllPstmt.executeQuery();
            while (rs.next()) {
                EmployeeDTO newEmployee = new EmployeeDTO();
                newEmployee.setId(rs.getInt("id"));
                newEmployee.setFirstName(rs.getString("fname"));
                newEmployee.setLastName(rs.getString("lname"));
                newEmployee.setEmail(rs.getString("email"));
                newEmployee.setSalary(rs.getDouble("salary"));
                newEmployee.setTitle(rs.getString("title"));
                employees.add(newEmployee);
            }
            try {
                rs.close();
            } catch (Exception e) {
                logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
            }
        } catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        return employees;
    }

    /**
     * Create a new employee in the database.
     * 
     * @return The newly created employee.
     * @param employee to manipulate.
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        logMsg("creating an employee");
        try {
            createPstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            createPstmt.setString(2, employee.getFirstName());
            createPstmt.setString(3, employee.getLastName());
            createPstmt.setString(4, employee.getEmail());
            createPstmt.setString(5, employee.getTitle());
            createPstmt.setDouble(6, employee.getSalary());

            createPstmt.executeUpdate();
        } catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        return employee;
    }

    /**
     * Retrieve a specific employee in the database to view.
     * 
     * @return The specified employee.
     * @param employeeId to view.
     */
    @Override
    public EmployeeDTO readEmployeeById(int employeeId) {
        logMsg("read a specific employee");
        EmployeeDTO foundEmployee = new EmployeeDTO();
        try {
            readByIdPstmt.setInt(1, employeeId);
            ResultSet rs = readByIdPstmt.executeQuery();
            while (rs.next()) {
                foundEmployee.setId(rs.getInt("id"));
                foundEmployee.setFirstName(rs.getString("fname"));
                foundEmployee.setLastName(rs.getString("lname"));
                foundEmployee.setEmail(rs.getString("email"));
                foundEmployee.setSalary(rs.getDouble("salary"));
                foundEmployee.setTitle(rs.getString("title"));
            }
            rs.close();
        } catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
        return foundEmployee;
    }

    /**
     * Update a specific employee in the database.
     * 
     * @param employee to update.
     */
    @Override
    public void updateEmployee(EmployeeDTO employee) {
        logMsg("updating a specific employee");
        try {
            updatePstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            updatePstmt.setString(2, employee.getFirstName());
            updatePstmt.setString(3, employee.getLastName());
            updatePstmt.setString(4, employee.getEmail());
            updatePstmt.setString(5, employee.getTitle());
            updatePstmt.setDouble(6, employee.getSalary());
            updatePstmt.setInt(7, employee.getId());

            updatePstmt.executeUpdate();
        } catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
    }

    /**
     * Delete a specific employee in the database.
     * 
     * @param employeeId to delete.
     */
    @Override
    public void deleteEmployeeById(int employeeId) {
        logMsg("deleting a specific employee");
        try {
            deleteByIdPstmt.setInt(1, employeeId);

            deleteByIdPstmt.executeUpdate();
        } catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
        }
    }

}