package com.projetfulstack.studentgrademanagement.repository;

import com.projetfulstack.studentgrademanagement.entity.Grade;
import com.projetfulstack.studentgrademanagement.entity.Student;
import com.projetfulstack.studentgrademanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Grade Repository Interface
 * Data access layer for Grade entity
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    /**
     * Find grades by student
     */
    List<Grade> findByStudentId(Long studentId);

    /**
     * Find grades by student and subject
     */
    List<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    /**
     * Find grades by subject
     */
    List<Grade> findBySubjectId(Long subjectId);

    /**
     * Find grades by student and exam type
     */
    List<Grade> findByStudentIdAndExamType(Long studentId, String examType);

    /**
     * Find grades by subject and exam type
     */
    List<Grade> findBySubjectIdAndExamType(Long subjectId, String examType);

    /**
     * Find grades by date range
     */
    @Query("SELECT g FROM Grade g WHERE g.examDate BETWEEN :startDate AND :endDate")
    List<Grade> findByExamDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find grades by student and date range
     */
    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.examDate BETWEEN :startDate AND :endDate")
    List<Grade> findByStudentIdAndExamDateBetween(@Param("studentId") Long studentId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);

    /**
     * Calculate average grade for a student in a subject
     */
    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.student.id = :studentId AND g.subject.id = :subjectId")
    Optional<Double> findAverageGradeByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    /**
     * Calculate average grade for a student across all subjects
     */
    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.student.id = :studentId")
    Optional<Double> findAverageGradeByStudent(@Param("studentId") Long studentId);

    /**
     * Find latest grade for a student in a subject
     */
    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.subject.id = :subjectId ORDER BY g.examDate DESC")
    List<Grade> findLatestGradesByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    /**
     * Count grades by student and subject
     */
    @Query("SELECT COUNT(g) FROM Grade g WHERE g.student.id = :studentId AND g.subject.id = :subjectId")
    Long countGradesByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    /**
     * Find grades with specific grade value range
     */
    @Query("SELECT g FROM Grade g WHERE g.gradeValue BETWEEN :minGrade AND :maxGrade")
    List<Grade> findByGradeValueBetween(@Param("minGrade") Double minGrade, @Param("maxGrade") Double maxGrade);
}
