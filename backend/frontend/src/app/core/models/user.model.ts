export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
  isActive: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export enum UserRole {
  STUDENT = 'STUDENT',
  TEACHER = 'TEACHER',
  ADMIN = 'ADMIN'
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: UserRole;
  
  // Student-specific fields
  studentId?: string;
  address?: string;
  phoneNumber?: string;
  dateOfBirth?: string; // Format: YYYY-MM-DD
  
  // Teacher-specific fields
  employeeId?: string;
  department?: string;
  specialization?: string;
}

export interface StudentRegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  studentId: string;
  address?: string;
  phoneNumber?: string;
  dateOfBirth?: string; // Format: YYYY-MM-DD
}

export interface TeacherRegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  employeeId: string;
  address?: string;
  phoneNumber?: string;
  dateOfBirth?: string; // Format: YYYY-MM-DD
  department?: string;
  specialization?: string;
}