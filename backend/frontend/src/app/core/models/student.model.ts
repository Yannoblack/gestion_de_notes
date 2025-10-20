import { User } from './user.model';

export interface Student extends User {
  studentId: string;
  classId?: number;
  className?: string;
  averageGrade?: number;
  totalCredits?: number;
  enrollmentDate: Date;
}

export interface StudentCreateRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  studentId: string;
  classId?: number;
}

export interface StudentUpdateRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
  studentId?: string;
  classId?: number;
  isActive?: boolean;
}
