/***************************************************************************f******************u************zz*******y**
 * File: EmployeeDTO.java
 * Course materials (20W) CST 8277
 *
 * @author (original) Mike Norman
 * @author Adam Mohr 040669681
 *
 */
package com.algonquincollege.cst8277.employeedirectory.model;

import java.io.Serializable;

import javax.faces.view.ViewScoped;

import com.algonquincollege.cst8277.employeedirectory.dao.Employee;

/**
* Description: model for the employee object
*/
@ViewScoped
public class EmployeeDTO implements Serializable, Employee {

    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;
    /** id from database */
    protected int id;
    /** employee first name */
    protected String firstName;
    /** employee last name */
    protected String lastName;
    /** employee email */
    protected String email;
    /** employee title */
    protected String title;
    /** employee salary */
    protected Double salary;
    
    /**
     * Default Constructor
     */
    public EmployeeDTO() {
    }

    /**
     * Parametized Constructor for new employees to be created
     */
    public EmployeeDTO(int id, String firstName, String lastName, String email, String title, Double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.salary = salary;
    }

    /**
     * @return the value for id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id new value for id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the value for firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the value for email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the value for title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title new value for title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the value for salary
     */
    public Double getSalary() {
        return salary;
    }
    /**
     * @param salary new value for salary
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmployeeDTO)) {
            return false;
        }
        EmployeeDTO other = (EmployeeDTO) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Employee [id=")
            .append(id)
            .append(", ");
        if (firstName != null) {
            builder
                .append("firstName=")
                .append(firstName)
                .append(", ");
        }
        if (lastName != null) {
            builder
                .append("lastName=")
                .append(lastName)
                .append(", ");
        }
        if (email != null) {
            builder
                .append("email=")
                .append(email)
                .append(", ");
        }
        if (salary != null) {
            builder
                .append("salary=")
                .append(salary)
                .append(", ");
        }
        if (title != null) {
            builder
                .append("title=")
                .append(title);
        }
        builder.append("]");
        return builder.toString();
    }

}