package com.projetfulstack.studentgrademanagement.controller;

import com.projetfulstack.studentgrademanagement.dto.JwtAuthenticationResponse;
import com.projetfulstack.studentgrademanagement.dto.LoginRequest;
import com.projetfulstack.studentgrademanagement.dto.RegisterRequest;
import com.projetfulstack.studentgrademanagement.dto.StudentRegisterRequest;
import com.projetfulstack.studentgrademanagement.dto.TeacherRegisterRequest;
import com.projetfulstack.studentgrademanagement.dto.AdminRegisterRequest;
import com.projetfulstack.studentgrademanagement.entity.Student;
import com.projetfulstack.studentgrademanagement.entity.Teacher;
import com.projetfulstack.studentgrademanagement.entity.User;
import com.projetfulstack.studentgrademanagement.entity.UserRole;
import com.projetfulstack.studentgrademanagement.repository.StudentRepository;
import com.projetfulstack.studentgrademanagement.repository.TeacherRepository;
import com.projetfulstack.studentgrademanagement.repository.UserRepository;
import com.projetfulstack.studentgrademanagement.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user authentication and registration
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Student registration endpoint
     */
    @PostMapping("/register/student")
    @Operation(summary = "Student registration", description = "Register a new student")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegisterRequest registerRequest) {
        
        // Check if email already exists
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Check if studentId already exists
        if (studentRepository.existsByStudentId(registerRequest.getStudentId())) {
            return ResponseEntity.badRequest().body("Error: Student ID is already in use!");
        }
        
        Student student = new Student();
        student.setFirstName(registerRequest.getFirstName());
        student.setLastName(registerRequest.getLastName());
        student.setEmail(registerRequest.getEmail());
        student.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        student.setRole(UserRole.STUDENT);
        student.setStudentId(registerRequest.getStudentId());
        student.setAddress(registerRequest.getAddress());
        student.setPhoneNumber(registerRequest.getPhoneNumber());
        student.setDateOfBirth(registerRequest.getDateOfBirth());
        
        studentRepository.save(student);
        return ResponseEntity.ok().body("Student registered successfully!");
    }

    /**
     * Teacher registration endpoint
     */
    @PostMapping("/register/teacher")
    @Operation(summary = "Teacher registration", description = "Register a new teacher")
    public ResponseEntity<?> registerTeacher(@Valid @RequestBody TeacherRegisterRequest registerRequest) {
        
        // Check if email already exists
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Check if employeeId already exists
        if (teacherRepository.existsByEmployeeId(registerRequest.getEmployeeId())) {
            return ResponseEntity.badRequest().body("Error: Employee ID is already in use!");
        }
        
        Teacher teacher = new Teacher();
        teacher.setFirstName(registerRequest.getFirstName());
        teacher.setLastName(registerRequest.getLastName());
        teacher.setEmail(registerRequest.getEmail());
        teacher.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        teacher.setRole(UserRole.TEACHER);
        teacher.setEmployeeId(registerRequest.getEmployeeId());
        teacher.setAddress(registerRequest.getAddress());
        teacher.setPhoneNumber(registerRequest.getPhoneNumber());
        teacher.setDateOfBirth(registerRequest.getDateOfBirth());
        teacher.setDepartment(registerRequest.getDepartment());
        teacher.setSpecialization(registerRequest.getSpecialization());
        
        teacherRepository.save(teacher);
        return ResponseEntity.ok().body("Teacher registered successfully!");
    }

    /**
     * Admin registration endpoint (deprecated - kept for admin registration only)
     * @deprecated Use /register/student or /register/teacher for students and teachers
     */
    @PostMapping("/register")
    @Operation(summary = "Admin registration (deprecated)", description = "Register a new administrator - Use /register/student or /register/teacher for other roles")
    @Deprecated
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRegisterRequest adminRequest) {
        
        // Check if email already exists
        if (userRepository.findByEmail(adminRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create admin user
        User admin = new User();
        admin.setFirstName(adminRequest.getFirstName());
        admin.setLastName(adminRequest.getLastName());
        admin.setEmail(adminRequest.getEmail());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setRole(UserRole.ADMIN);
        
        // Note: Admin users don't have address, phoneNumber, dateOfBirth in the User entity
        // These fields are only available in Student and Teacher entities
        
        userRepository.save(admin);
        
        return ResponseEntity.ok().body("Admin registered successfully!");
    }

    /**
     * User login endpoint
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new JwtAuthenticationResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        ));
    }

    /**
     * Get current user info
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get current authenticated user information")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new JwtAuthenticationResponse(
                null, // Don't return token for security
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        ));
    }

    /**
     * User logout endpoint (client-side token removal)
     */
    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user (client should remove token)")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("User logged out successfully");
    }
}
