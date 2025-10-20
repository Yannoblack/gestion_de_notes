package com.projetfulstack.studentgrademanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * Class entity representing academic classes/groups
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Class name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Academic year is required")
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Many-to-one relationship with class teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_teacher_id")
    private Teacher classTeacher;

    // One-to-many relationship with students
    @OneToMany(mappedBy = "studentClass", fetch = FetchType.LAZY)
    private List<Student> students;

    // Many-to-many relationship with subjects
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "class_subjects",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    // Constructors
    public Class() {
    }

    public Class(String name, String academicYear, Integer semester) {
        this.name = name;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Teacher classTeacher) {
        this.classTeacher = classTeacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Helper methods
    public int getStudentCount() {
        return students != null ? students.size() : 0;
    }

    public int getSubjectCount() {
        return subjects != null ? subjects.size() : 0;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", semester=" + semester +
                '}';
    }
}
