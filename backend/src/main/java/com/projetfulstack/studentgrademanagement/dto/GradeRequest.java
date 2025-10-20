package com.projetfulstack.studentgrademanagement.dto;

import com.projetfulstack.studentgrademanagement.entity.ExamType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Grade Request DTO
 * Data transfer object for grade creation/update requests
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
public class GradeRequest {
    
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Grade value is required")
    @DecimalMin(value = "0.0", message = "Grade must be at least 0.0")
    @DecimalMax(value = "20.0", message = "Grade must be at most 20.0")
    private Double gradeValue;

    @NotNull(message = "Max grade is required")
    private Double maxGrade = 20.0;

    private ExamType examType;

    private LocalDateTime examDate;

    private String comment;

    // Constructors
    public GradeRequest() {
    }

    public GradeRequest(Long studentId, Long subjectId, Double gradeValue, Double maxGrade, ExamType examType) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.gradeValue = gradeValue;
        this.maxGrade = maxGrade;
        this.examType = examType;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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
}
