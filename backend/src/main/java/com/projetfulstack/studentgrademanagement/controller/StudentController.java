package com.projetfulstack.studentgrademanagement.controller;

import com.projetfulstack.studentgrademanagement.entity.Grade;
import com.projetfulstack.studentgrademanagement.entity.Student;
import com.projetfulstack.studentgrademanagement.repository.GradeRepository;
import com.projetfulstack.studentgrademanagement.repository.StudentRepository;
import com.projetfulstack.studentgrademanagement.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student Controller
 * Handles student-related API endpoints
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Student management APIs")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    /**
     * Get current student's profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @Operation(summary = "Get student profile", description = "Get current student's profile information")
    public ResponseEntity<Student> getStudentProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        Student student = studentRepository.findById(currentUser.getId())
                .map(Student.class::cast)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        return ResponseEntity.ok(student);
    }

    /**
     * Get current student's grades
     */
    @GetMapping("/grades")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @Operation(summary = "Get student grades", description = "Get all grades for current student")
    public ResponseEntity<List<Grade>> getStudentGrades(@AuthenticationPrincipal UserPrincipal currentUser) {
        List<Grade> grades = gradeRepository.findByStudentId(currentUser.getId());
        return ResponseEntity.ok(grades);
    }

    /**
     * Get student grades by subject
     */
    @GetMapping("/grades/subject/{subjectId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @Operation(summary = "Get grades by subject", description = "Get student grades for a specific subject")
    public ResponseEntity<List<Grade>> getGradesBySubject(@PathVariable Long subjectId,
                                                          @AuthenticationPrincipal UserPrincipal currentUser) {
        List<Grade> grades = gradeRepository.findByStudentIdAndSubjectId(currentUser.getId(), subjectId);
        return ResponseEntity.ok(grades);
    }

    /**
     * Get student grade statistics
     */
    @GetMapping("/grades/statistics")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @Operation(summary = "Get grade statistics", description = "Get grade statistics for current student")
    public ResponseEntity<Map<String, Object>> getGradeStatistics(@AuthenticationPrincipal UserPrincipal currentUser) {
        Map<String, Object> statistics = new HashMap<>();
        
        // Overall average
        Double overallAverage = gradeRepository.findAverageGradeByStudent(currentUser.getId()).orElse(0.0);
        statistics.put("overallAverage", overallAverage);
        
        // Grade count
        Long totalGrades = gradeRepository.count();
        statistics.put("totalGrades", totalGrades);
        
        // Latest grades
        List<Grade> latestGrades = gradeRepository.findByStudentId(currentUser.getId());
        statistics.put("latestGrades", latestGrades);
        
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get student grades by semester
     */
    @GetMapping("/grades/semester/{semester}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @Operation(summary = "Get grades by semester", description = "Get student grades for a specific semester")
    public ResponseEntity<List<Grade>> getGradesBySemester(@PathVariable Integer semester,
                                                           @AuthenticationPrincipal UserPrincipal currentUser) {
        // This would need additional filtering based on semester
        List<Grade> grades = gradeRepository.findByStudentId(currentUser.getId());
        return ResponseEntity.ok(grades);
    }

    /**
     * Get all students (for teachers and admins)
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get all students", description = "Get all students (teachers and admins only)")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    /**
     * Get student by ID (for teachers and admins)
     */
    @GetMapping("/{studentId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get student by ID", description = "Get student by ID (teachers and admins only)")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .map(Student.class::cast)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        return ResponseEntity.ok(student);
    }

    /**
     * Search students (for teachers and admins)
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Search students", description = "Search students by name or student ID")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String query) {
        List<Student> students = studentRepository.findByNameContaining(query);
        return ResponseEntity.ok(students);
    }
}
