import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student, StudentCreateRequest, StudentUpdateRequest } from '../models/student.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private readonly API_URL = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Get all students (for teachers and admins)
  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.API_URL}/student/all`);
  }

  // Search students (for teachers and admins)
  searchStudents(query: string): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.API_URL}/student/search`, {
      params: new HttpParams().set('query', query)
    });
  }

  // Get student by ID (for teachers and admins)
  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.API_URL}/student/${id}`);
  }

  // Get current student's profile
  getStudentProfile(): Observable<Student> {
    return this.http.get<Student>(`${this.API_URL}/student/profile`);
  }

  // Get current student's grades
  getStudentGrades(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/student/grades`);
  }

  // Get student grades by subject
  getStudentGradesBySubject(subjectId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/student/grades/subject/${subjectId}`);
  }

  // Get student statistics
  getStudentStatistics(): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/student/grades/statistics`);
  }

  // Get grades by semester
  getStudentGradesBySemester(semester: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/student/grades/semester/${semester}`);
  }

  // Note: Create, Update, Delete students endpoints don't exist in the backend yet
  // They would need to be added to a future AdminController
  createStudent(student: StudentCreateRequest): Observable<Student> {
    console.warn('Endpoint not implemented in backend yet');
    return this.http.post<Student>(`${this.API_URL}/admin/students`, student);
  }

  updateStudent(id: number, student: StudentUpdateRequest): Observable<Student> {
    console.warn('Endpoint not implemented in backend yet');
    return this.http.put<Student>(`${this.API_URL}/admin/students/${id}`, student);
  }

  deleteStudent(id: number): Observable<void> {
    console.warn('Endpoint not implemented in backend yet');
    return this.http.delete<void>(`${this.API_URL}/admin/students/${id}`);
  }
}
