package com.projetfulstack.studentgrademanagement.repository;

import com.projetfulstack.studentgrademanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Student Repository Interface
 * Data access layer for Student entity
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by student ID
     */
    Optional<Student> findByStudentId(String studentId);

    /**
     * Check if student exists by student ID
     */
    Boolean existsByStudentId(String studentId);

    /**
     * Find students by class
     */
    List<Student> findByStudentClassId(Long classId);

    /**
     * Find active students
     */
    @Query("SELECT s FROM Student s WHERE s.isActive = true")
    List<Student> findActiveStudents();

    /**
     * Find students by class and active status
     */
    @Query("SELECT s FROM Student s WHERE s.studentClass.id = :classId AND s.isActive = :isActive")
    List<Student> findByClassIdAndIsActive(@Param("classId") Long classId, @Param("isActive") Boolean isActive);

    /**
     * Search students by name
     */
    @Query("SELECT s FROM Student s WHERE " +
           "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Student> findByNameContaining(@Param("name") String name);

    /**
     * Find students by academic year
     */
    @Query("SELECT s FROM Student s WHERE s.studentClass.academicYear = :academicYear")
    List<Student> findByAcademicYear(@Param("academicYear") String academicYear);

    /**
     * Find students by semester
     */
    @Query("SELECT s FROM Student s WHERE s.studentClass.semester = :semester")
    List<Student> findBySemester(@Param("semester") Integer semester);

    /**
     * Find students by academic year and semester
     */
    @Query("SELECT s FROM Student s WHERE s.studentClass.academicYear = :academicYear AND s.studentClass.semester = :semester")
    List<Student> findByAcademicYearAndSemester(@Param("academicYear") String academicYear, @Param("semester") Integer semester);
}
