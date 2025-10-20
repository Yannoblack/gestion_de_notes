package com.projetfulstack.studentgrademanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * Student entity extending User
 * Represents students in the system with academic-specific information
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @NotBlank(message = "Student ID is required")
    @Size(max = 20, message = "Student ID must not exceed 20 characters")
    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(max = 100, message = "Address must not exceed 100 characters")
    @Column(name = "address")
    private String address;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Class studentClass;

    // One-to-many relationship with grades
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grade> grades;

    // One-to-many relationship with attendance
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendanceRecords;

    // Constructors
    public Student() {
        super();
        this.setRole(UserRole.STUDENT);
        this.enrollmentDate = LocalDate.now();
    }

    public Student(String firstName, String lastName, String email, String password, String studentId) {
        super(firstName, lastName, email, password, UserRole.STUDENT);
        this.studentId = studentId;
        this.enrollmentDate = LocalDate.now();
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<Attendance> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    // Helper methods
    public boolean isGraduated() {
        return graduationDate != null;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", studentId='" + studentId + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
