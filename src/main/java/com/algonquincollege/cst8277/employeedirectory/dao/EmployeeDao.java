/***************************************************************************f******************u************zz*******y**
 * File: EmployeeDao.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * @author Adam Mohr 040669681
 *
 */
package com.algonquincollege.cst8277.employeedirectory.dao;

import java.util.List;

import com.algonquincollege.cst8277.employeedirectory.model.EmployeeDTO;

/**
 * Description: API for the database C-R-U-D operations
 */
public interface EmployeeDao {

    /**
     * Create
     * 
     * @return the new employee
     * @param the employee
     */
    public EmployeeDTO createEmployee(EmployeeDTO employee);

    /**
     * Read
     * 
     * @return the specifc employee
     * @param the employee id
     */
    public EmployeeDTO readEmployeeById(int employeeId);

    /**
     * Read
     * 
     * @return list of all the employees
     */
    public List<EmployeeDTO> readAllEmployees();

    /**
     * Update
     * 
     * @param the employee
     */
    public void updateEmployee(EmployeeDTO employee);

    /**
     * Delete
     * 
     * @param the employee id
     */
    public void deleteEmployeeById(int employeeId);

}