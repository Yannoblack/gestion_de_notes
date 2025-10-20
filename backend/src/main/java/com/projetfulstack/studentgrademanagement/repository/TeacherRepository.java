package com.projetfulstack.studentgrademanagement.repository;

import com.projetfulstack.studentgrademanagement.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Teacher Repository Interface
 * Data access layer for Teacher entity
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    /**
     * Find teacher by employee ID
     */
    Optional<Teacher> findByEmployeeId(String employeeId);

    /**
     * Check if teacher exists by employee ID
     */
    Boolean existsByEmployeeId(String employeeId);

    /**
     * Find teachers by department
     */
    List<Teacher> findByDepartment(String department);

    /**
     * Find active teachers
     */
    @Query("SELECT t FROM Teacher t WHERE t.isActive = true")
    List<Teacher> findActiveTeachers();

    /**
     * Find teachers by department and active status
     */
    @Query("SELECT t FROM Teacher t WHERE t.department = :department AND t.isActive = :isActive")
    List<Teacher> findByDepartmentAndIsActive(@Param("department") String department, @Param("isActive") Boolean isActive);

    /**
     * Search teachers by name or employee ID
     */
    @Query("SELECT t FROM Teacher t WHERE " +
           "(LOWER(t.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(t.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(t.employeeId) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Teacher> findByNameContaining(@Param("name") String name);

    /**
     * Find teachers by specialization
     */
    List<Teacher> findBySpecialization(String specialization);

    /**
     * Find teachers by department and specialization
     */
    List<Teacher> findByDepartmentAndSpecialization(String department, String specialization);
}
