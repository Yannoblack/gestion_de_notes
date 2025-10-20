-- Database initialization script for Student Grade Management System
-- This script creates sample data for demonstration purposes

-- Create sample users
INSERT INTO users (id, first_name, last_name, email, password, role, is_active, created_at, updated_at) VALUES
(1, 'John', 'Doe', 'admin@demo.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', true, NOW(), NOW()),
(2, 'Jane', 'Smith', 'teacher@demo.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'TEACHER', true, NOW(), NOW()),
(3, 'Bob', 'Johnson', 'student@demo.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'STUDENT', true, NOW(), NOW());

-- Create sample teacher
INSERT INTO teachers (user_id, employee_id, department, specialization, hire_date) VALUES
(2, 'T001', 'Computer Science', 'Software Engineering', '2020-01-15');

-- Create sample student
INSERT INTO students (user_id, student_id, enrollment_date) VALUES
(3, 'S001', '2023-09-01');

-- Create sample subjects
INSERT INTO subjects (id, name, code, description, coefficient, credits, is_active, teacher_id) VALUES
(1, 'Mathematics', 'MATH101', 'Basic Mathematics', 3.0, 4, true, 2),
(2, 'Computer Science', 'CS101', 'Introduction to Computer Science', 4.0, 4, true, 2),
(3, 'Physics', 'PHYS101', 'Basic Physics', 3.0, 3, true, 2);

-- Create sample class
INSERT INTO classes (id, name, academic_year, semester, is_active, class_teacher_id) VALUES
(1, 'Class A', '2023-2024', 1, true, 2);

-- Create sample grades
INSERT INTO grades (id, grade_value, max_grade, exam_type, exam_date, comment, student_id, subject_id, teacher_id, created_at, updated_at) VALUES
(1, 18.5, 20.0, 'FINAL', '2023-12-15 10:00:00', 'Excellent work', 3, 1, 2, NOW(), NOW()),
(2, 16.0, 20.0, 'MIDTERM', '2023-11-15 10:00:00', 'Good performance', 3, 2, 2, NOW(), NOW()),
(3, 14.5, 20.0, 'QUIZ', '2023-10-15 10:00:00', 'Needs improvement', 3, 3, 2, NOW(), NOW());

-- Create sample notifications
INSERT INTO notifications (id, title, message, type, is_read, user_id, created_at) VALUES
(1, 'New Grade Posted', 'Your final exam grade for Mathematics has been posted.', 'GRADE_UPDATE', false, 3, NOW()),
(2, 'Welcome to the System', 'Welcome to the Student Grade Management System!', 'GENERAL', false, 3, NOW());
