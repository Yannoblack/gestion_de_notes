package com.projetfulstack.studentgrademanagement.controller;

import com.projetfulstack.studentgrademanagement.entity.Subject;
import com.projetfulstack.studentgrademanagement.entity.Student;
import com.projetfulstack.studentgrademanagement.entity.Teacher;
import com.projetfulstack.studentgrademanagement.entity.User;
import com.projetfulstack.studentgrademanagement.repository.SubjectRepository;
import com.projetfulstack.studentgrademanagement.repository.StudentRepository;
import com.projetfulstack.studentgrademanagement.repository.TeacherRepository;
import com.projetfulstack.studentgrademanagement.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Admin Controller
 * Handles admin-related API endpoints
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Admin management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all subjects
     */
    @GetMapping("/subjects")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all subjects", description = "Get all subjects in the system")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(subjects);
    }

    /**
     * Create a new subject
     */
    @PostMapping("/subjects")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create subject", description = "Create a new subject")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);
    }

    /**
     * Update a subject
     */
    @PutMapping("/subjects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update subject", description = "Update an existing subject")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        existingSubject.setName(subject.getName());
        existingSubject.setCode(subject.getCode());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setCredits(subject.getCredits());
        
        Subject updatedSubject = subjectRepository.save(existingSubject);
        return ResponseEntity.ok(updatedSubject);
    }

    /**
     * Delete a subject
     */
    @DeleteMapping("/subjects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete subject", description = "Delete a subject by ID")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return ResponseEntity.ok().body("Subject deleted successfully");
    }

    /**
     * Get all students
     */
    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all students", description = "Get all students in the system")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    /**
     * Get all teachers
     */
    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all teachers", description = "Get all teachers in the system")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return ResponseEntity.ok(teachers);
    }

    /**
     * Get all users
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users in the system")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Get system statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get statistics", description = "Get system statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalTeachers", teacherRepository.count());
        stats.put("totalSubjects", subjectRepository.count());
        stats.put("totalUsers", userRepository.count());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * Delete a user (admin only)
     */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete a user by ID (admin only)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }

    /**
     * Get a specific student by ID
     */
    @GetMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get student by ID", description = "Get a specific student by ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ResponseEntity.ok(student);
    }

    /**
     * Get a specific teacher by ID
     */
    @GetMapping("/teachers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get teacher by ID", description = "Get a specific teacher by ID")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return ResponseEntity.ok(teacher);
    }

    /**
     * Update a teacher
     */
    @PutMapping("/teachers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update teacher", description = "Update teacher information")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherData) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        teacher.setFirstName(teacherData.getFirstName());
        teacher.setLastName(teacherData.getLastName());
        teacher.setEmail(teacherData.getEmail());
        teacher.setAddress(teacherData.getAddress());
        teacher.setPhoneNumber(teacherData.getPhoneNumber());
        teacher.setDepartment(teacherData.getDepartment());
        teacher.setSpecialization(teacherData.getSpecialization());
        
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    /**
     * Delete a teacher
     */
    @DeleteMapping("/teachers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete teacher", description = "Delete a teacher by ID")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        teacherRepository.deleteById(id);
        return ResponseEntity.ok().body("Teacher deleted successfully");
    }

    /**
     * Update a student
     */
    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update student", description = "Update student information")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentData) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        student.setFirstName(studentData.getFirstName());
        student.setLastName(studentData.getLastName());
        student.setEmail(studentData.getEmail());
        student.setAddress(studentData.getAddress());
        student.setPhoneNumber(studentData.getPhoneNumber());
        student.setStudentId(studentData.getStudentId());
        
        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Delete a student
     */
    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete student", description = "Delete a student by ID")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().body("Student deleted successfully");
    }

    /**
     * Activate/Deactivate a user
     */
    @PutMapping("/users/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Toggle user status", description = "Activate or deactivate a user account")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setIsActive(active);
        userRepository.save(user);
        
        return ResponseEntity.ok().body("User status updated successfully");
    }
}

