package com.projetfulstack.studentgrademanagement.controller;

import com.projetfulstack.studentgrademanagement.dto.GradeRequest;
import com.projetfulstack.studentgrademanagement.entity.Grade;
import com.projetfulstack.studentgrademanagement.entity.Student;
import com.projetfulstack.studentgrademanagement.entity.Subject;
import com.projetfulstack.studentgrademanagement.entity.Teacher;
import com.projetfulstack.studentgrademanagement.repository.GradeRepository;
import com.projetfulstack.studentgrademanagement.repository.StudentRepository;
import com.projetfulstack.studentgrademanagement.repository.SubjectRepository;
import com.projetfulstack.studentgrademanagement.repository.TeacherRepository;
import com.projetfulstack.studentgrademanagement.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teacher Controller
 * Handles teacher-related API endpoints
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Teacher management APIs")
@SecurityRequirement(name = "bearerAuth")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private GradeRepository gradeRepository;

    /**
     * Get current teacher's profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get teacher profile", description = "Get current teacher's profile information")
    public ResponseEntity<Teacher> getTeacherProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        Teacher teacher = teacherRepository.findById(currentUser.getId())
                .map(Teacher.class::cast)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        return ResponseEntity.ok(teacher);
    }

    /**
     * Get teacher's subjects
     */
    @GetMapping("/subjects")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get teacher subjects", description = "Get all subjects taught by current teacher")
    public ResponseEntity<List<Subject>> getTeacherSubjects(@AuthenticationPrincipal UserPrincipal currentUser) {
        Teacher teacher = teacherRepository.findById(currentUser.getId())
                .map(Teacher.class::cast)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        return ResponseEntity.ok(teacher.getSubjects());
    }

    /**
     * Get teacher's classes
     */
    @GetMapping("/classes")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get teacher classes", description = "Get all classes taught by current teacher")
    public ResponseEntity<List<com.projetfulstack.studentgrademanagement.entity.Class>> getTeacherClasses(@AuthenticationPrincipal UserPrincipal currentUser) {
        Teacher teacher = teacherRepository.findById(currentUser.getId())
                .map(Teacher.class::cast)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        return ResponseEntity.ok(teacher.getClasses());
    }

    /**
     * Add grade for a student
     */
    @PostMapping("/grades")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Add grade", description = "Add a new grade for a student")
    public ResponseEntity<Grade> addGrade(@Valid @RequestBody GradeRequest gradeRequest,
                                         @AuthenticationPrincipal UserPrincipal currentUser) {
        
        Teacher teacher = teacherRepository.findById(currentUser.getId())
                .map(Teacher.class::cast)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        Student student = studentRepository.findById(gradeRequest.getStudentId())
                .map(Student.class::cast)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Subject subject = subjectRepository.findById(gradeRequest.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Grade grade = new Grade();
        grade.setGradeValue(gradeRequest.getGradeValue());
        grade.setMaxGrade(gradeRequest.getMaxGrade());
        grade.setExamType(gradeRequest.getExamType());
        grade.setExamDate(gradeRequest.getExamDate() != null ? gradeRequest.getExamDate() : LocalDateTime.now());
        grade.setComment(gradeRequest.getComment());
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setTeacher(teacher);

        Grade savedGrade = gradeRepository.save(grade);
        return ResponseEntity.ok(savedGrade);
    }

    /**
     * Update grade
     */
    @PutMapping("/grades/{gradeId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Update grade", description = "Update an existing grade")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long gradeId,
                                           @Valid @RequestBody GradeRequest gradeRequest,
                                           @AuthenticationPrincipal UserPrincipal currentUser) {
        
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        // Check if teacher owns this grade or is admin
        if (!grade.getTeacher().getId().equals(currentUser.getId()) && !currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Unauthorized to update this grade");
        }

        grade.setGradeValue(gradeRequest.getGradeValue());
        grade.setMaxGrade(gradeRequest.getMaxGrade());
        grade.setExamType(gradeRequest.getExamType());
        grade.setExamDate(gradeRequest.getExamDate());
        grade.setComment(gradeRequest.getComment());

        Grade updatedGrade = gradeRepository.save(grade);
        return ResponseEntity.ok(updatedGrade);
    }

    /**
     * Delete grade
     */
    @DeleteMapping("/grades/{gradeId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Delete grade", description = "Delete a grade")
    public ResponseEntity<?> deleteGrade(@PathVariable Long gradeId,
                                        @AuthenticationPrincipal UserPrincipal currentUser) {
        
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        // Check if teacher owns this grade or is admin
        if (!grade.getTeacher().getId().equals(currentUser.getId()) && !currentUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Unauthorized to delete this grade");
        }

        gradeRepository.delete(grade);
        return ResponseEntity.ok().body("Grade deleted successfully");
    }

    /**
     * Get students in a subject
     */
    @GetMapping("/subjects/{subjectId}/students")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get students in subject", description = "Get all students enrolled in a specific subject")
    public ResponseEntity<List<Student>> getStudentsInSubject(@PathVariable Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        // This would need additional logic to get students enrolled in the subject
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    /**
     * Get grade statistics for a subject
     */
    @GetMapping("/subjects/{subjectId}/statistics")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @Operation(summary = "Get subject statistics", description = "Get grade statistics for a specific subject")
    public ResponseEntity<Map<String, Object>> getSubjectStatistics(@PathVariable Long subjectId) {
        Map<String, Object> statistics = new HashMap<>();
        
        List<Grade> grades = gradeRepository.findBySubjectId(subjectId);
        
        if (!grades.isEmpty()) {
            Double average = grades.stream().mapToDouble(Grade::getGradeValue).average().orElse(0.0);
            Double min = grades.stream().mapToDouble(Grade::getGradeValue).min().orElse(0.0);
            Double max = grades.stream().mapToDouble(Grade::getGradeValue).max().orElse(0.0);
            
            statistics.put("average", average);
            statistics.put("min", min);
            statistics.put("max", max);
            statistics.put("totalGrades", grades.size());
        }
        
        return ResponseEntity.ok(statistics);
    }
}
