package com.projetfulstack.studentgrademanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * Teacher entity extending User
 * Represents teachers in the system with teaching-specific information
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Entity
@Table(name = "teachers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    @NotBlank(message = "Employee ID is required")
    @Size(max = 20, message = "Employee ID must not exceed 20 characters")
    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(max = 100, message = "Address must not exceed 100 characters")
    @Column(name = "address")
    private String address;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    @Column(name = "department")
    private String department;

    @Size(max = 100, message = "Specialization must not exceed 100 characters")
    @Column(name = "specialization")
    private String specialization;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "salary")
    private Double salary;

    // One-to-many relationship with subjects taught
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;

    // One-to-many relationship with classes taught
    @OneToMany(mappedBy = "classTeacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Class> classes;

    // Constructors
    public Teacher() {
        super();
        this.setRole(UserRole.TEACHER);
        this.hireDate = LocalDate.now();
    }

    public Teacher(String firstName, String lastName, String email, String password, String employeeId) {
        super(firstName, lastName, email, password, UserRole.TEACHER);
        this.employeeId = employeeId;
        this.hireDate = LocalDate.now();
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", employeeId='" + employeeId + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}
