/***************************************************************************f******************u************zz*******y**
 * File: Employee.java
 * Course materials (20W) CST 8277
 * @author Adam Mohr 040669681
 * 
 */
package com.algonquincollege.cst8277.employeedirectory.dao;

public interface Employee {

    /**
     * @return the primary key from database
     */
    public int getId();

    /**
     * @param new id of the employee
     */
    public void setId(int id);

    /**
     * @return the first name of the employee
     */
    public String getFirstName();

    /**
     * @param new first name of the employee
     */
    public void setFirstName(String firstName);

    /**
     * @return the last name of the employee
     */
    public String getLastName();

    /**
     * @param new last name of the employee
     */
    public void setLastName(String lastName);

    /**
     * @return the email of the employee
     */
    public String getEmail();

    /**
     * @param new email of the employee
     */
    public void setEmail(String email);

    /**
     * @return the title of the employee
     */
    public String getTitle();

    /**
     * @param new title of the employee
     */
    public void setTitle(String title);

    /**
     * @return the salary of the employee
     */
    public Double getSalary();

    /**
     * @param new salary of the employee
     */
    public void setSalary(Double salary);
    
}
