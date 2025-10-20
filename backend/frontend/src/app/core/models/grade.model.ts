import { Student } from './student.model';

export interface Grade {
  id: number;
  studentId: number;
  subjectId: number;
  teacherId: number;
  examType: ExamType;
  score: number;
  maxScore: number;
  coefficient: number;
  examDate: Date;
  comments?: string;
  createdAt: Date;
  updatedAt: Date;
  student?: Student;
  subject?: Subject;
  teacher?: any; // Teacher interface
}

export interface Subject {
  id: number;
  name: string;
  code: string;
  description?: string;
  credits: number;
  isActive: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export enum ExamType {
  QUIZ = 'QUIZ',
  MIDTERM = 'MIDTERM',
  FINAL = 'FINAL',
  ASSIGNMENT = 'ASSIGNMENT',
  PROJECT = 'PROJECT'
}

export interface GradeCreateRequest {
  studentId: number;
  subjectId: number;
  examType: ExamType;
  score: number;
  maxScore: number;
  coefficient: number;
  examDate: Date;
  comments?: string;
}

export interface GradeUpdateRequest {
  score?: number;
  maxScore?: number;
  coefficient?: number;
  examDate?: Date;
  comments?: string;
}
