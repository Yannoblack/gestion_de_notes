import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grade, GradeCreateRequest, GradeUpdateRequest, Subject } from '../models/grade.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  private readonly API_URL = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Teacher: Add a new grade
  createGrade(grade: GradeCreateRequest): Observable<Grade> {
    return this.http.post<Grade>(`${this.API_URL}/teacher/grades`, grade);
  }

  // Teacher: Update a grade
  updateGrade(id: number, grade: GradeUpdateRequest): Observable<Grade> {
    return this.http.put<Grade>(`${this.API_URL}/teacher/grades/${id}`, grade);
  }

  // Teacher: Delete a grade
  deleteGrade(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/teacher/grades/${id}`);
  }

  // Student: Get grades for current student
  getStudentGrades(): Observable<Grade[]> {
    return this.http.get<Grade[]>(`${this.API_URL}/student/grades`);
  }

  // Student: Get grades by subject
  getGradesBySubject(subjectId: number): Observable<Grade[]> {
    return this.http.get<Grade[]>(`${this.API_URL}/student/grades/subject/${subjectId}`);
  }

  // Teacher: Get students in a subject
  getStudentsInSubject(subjectId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/teacher/subjects/${subjectId}/students`);
  }

  // Teacher: Get statistics for a subject
  getSubjectStatistics(subjectId: number): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/teacher/subjects/${subjectId}/statistics`);
  }

  // Teacher: Get teacher's subjects
  getTeacherSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.API_URL}/teacher/subjects`);
  }

  // Teacher: Get teacher's classes
  getTeacherClasses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/teacher/classes`);
  }

  // Admin: Get all subjects
  getAllSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.API_URL}/admin/subjects`);
  }

  // Admin: Create a subject
  createSubject(subject: Partial<Subject>): Observable<Subject> {
    return this.http.post<Subject>(`${this.API_URL}/admin/subjects`, subject);
  }

  // Admin: Update a subject
  updateSubject(id: number, subject: Partial<Subject>): Observable<Subject> {
    return this.http.put<Subject>(`${this.API_URL}/admin/subjects/${id}`, subject);
  }

  // Admin: Delete a subject
  deleteSubject(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/admin/subjects/${id}`, { responseType: 'text' });
  }
}
