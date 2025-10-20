package com.projetfulstack.studentgrademanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Grade entity representing student grades for subjects
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Grade value is required")
    @DecimalMin(value = "0.0", message = "Grade must be at least 0.0")
    @DecimalMax(value = "20.0", message = "Grade must be at most 20.0")
    @Column(name = "grade_value", nullable = false)
    private Double gradeValue;

    @Column(name = "max_grade", nullable = false)
    private Double maxGrade = 20.0;

    @Column(name = "exam_type")
    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Column(name = "exam_date")
    private LocalDateTime examDate;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Many-to-one relationship with student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Many-to-one relationship with subject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // Many-to-one relationship with teacher who graded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Constructors
    public Grade() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Grade(Double gradeValue, Double maxGrade, ExamType examType, Student student, Subject subject, Teacher teacher) {
        this();
        this.gradeValue = gradeValue;
        this.maxGrade = maxGrade;
        this.examType = examType;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
    }

    // Lifecycle callbacks
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods
    public Double getPercentage() {
        if (maxGrade == null || maxGrade == 0) {
            return 0.0;
        }
        return (gradeValue / maxGrade) * 100;
    }

    public String getLetterGrade() {
        Double percentage = getPercentage();
        if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B";
        else if (percentage >= 70) return "C";
        else if (percentage >= 60) return "D";
        else return "F";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", gradeValue=" + gradeValue +
                ", maxGrade=" + maxGrade +
                ", examType=" + examType +
                ", student=" + (student != null ? student.getStudentId() : null) +
                ", subject=" + (subject != null ? subject.getName() : null) +
                '}';
    }
}
