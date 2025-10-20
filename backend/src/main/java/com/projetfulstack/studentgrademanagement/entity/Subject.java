package com.projetfulstack.studentgrademanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * Subject entity representing academic subjects/courses
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Coefficient is required")
    @Positive(message = "Coefficient must be positive")
    @Column(name = "coefficient", nullable = false)
    private Double coefficient;

    @NotNull(message = "Credits is required")
    @Positive(message = "Credits must be positive")
    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Many-to-one relationship with teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Many-to-many relationship with classes
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<Class> classes;

    // One-to-many relationship with grades
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grade> grades;

    // Constructors
    public Subject() {
    }

    public Subject(String name, String code, String description, Double coefficient, Integer credits) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.coefficient = coefficient;
        this.credits = credits;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", coefficient=" + coefficient +
                ", credits=" + credits +
                '}';
    }
}
