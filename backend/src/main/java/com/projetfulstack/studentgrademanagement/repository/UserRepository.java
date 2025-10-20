package com.projetfulstack.studentgrademanagement.repository;

import com.projetfulstack.studentgrademanagement.entity.User;
import com.projetfulstack.studentgrademanagement.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository Interface
 * Data access layer for User entity
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by email
     */
    Boolean existsByEmail(String email);

    /**
     * Find users by role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find active users by role
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true")
    List<User> findActiveUsersByRole(@Param("role") UserRole role);

    /**
     * Find users by role and active status
     */
    List<User> findByRoleAndIsActive(UserRole role, Boolean isActive);

    /**
     * Search users by name (first name or last name)
     */
    @Query("SELECT u FROM User u WHERE " +
           "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<User> findByNameContaining(@Param("name") String name);

    /**
     * Find users by role and name
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND " +
           "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<User> findByRoleAndNameContaining(@Param("role") UserRole role, @Param("name") String name);
}
