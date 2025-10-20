package com.projetfulstack.studentgrademanagement.repository;

import com.projetfulstack.studentgrademanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Subject Repository Interface
 * Data access layer for Subject entity
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    /**
     * Find subject by code
     */
    Optional<Subject> findByCode(String code);

    /**
     * Check if subject exists by code
     */
    Boolean existsByCode(String code);

    /**
     * Find active subjects
     */
    @Query("SELECT s FROM Subject s WHERE s.isActive = true")
    List<Subject> findActiveSubjects();

    /**
     * Find subjects by teacher
     */
    List<Subject> findByTeacherId(Long teacherId);

    /**
     * Find active subjects by teacher
     */
    @Query("SELECT s FROM Subject s WHERE s.teacher.id = :teacherId AND s.isActive = true")
    List<Subject> findActiveSubjectsByTeacher(@Param("teacherId") Long teacherId);

    /**
     * Search subjects by name or code
     */
    @Query("SELECT s FROM Subject s WHERE " +
           "(LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.code) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Subject> findByNameOrCodeContaining(@Param("query") String query);

    /**
     * Find subjects by coefficient range
     */
    @Query("SELECT s FROM Subject s WHERE s.coefficient BETWEEN :minCoeff AND :maxCoeff")
    List<Subject> findByCoefficientBetween(@Param("minCoeff") Double minCoeff, @Param("maxCoeff") Double maxCoeff);

    /**
     * Find subjects by credits
     */
    List<Subject> findByCredits(Integer credits);

    /**
     * Find subjects by credits and active status
     */
    List<Subject> findByCreditsAndIsActive(Integer credits, Boolean isActive);
}
