import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Subject {
  id?: number;
  name: string;
  code: string;
  description?: string;
  credits?: number;
}

export interface AdminStatistics {
  totalStudents: number;
  totalTeachers: number;
  totalSubjects: number;
  totalUsers: number;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private readonly API_URL = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // ========== SUBJECTS ==========
  
  getAllSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.API_URL}/admin/subjects`);
  }

  createSubject(subject: Subject): Observable<Subject> {
    return this.http.post<Subject>(`${this.API_URL}/admin/subjects`, subject);
  }

  updateSubject(id: number, subject: Subject): Observable<Subject> {
    return this.http.put<Subject>(`${this.API_URL}/admin/subjects/${id}`, subject);
  }

  deleteSubject(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/admin/subjects/${id}`, { responseType: 'text' });
  }

  // ========== STUDENTS ==========
  
  getAllStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/admin/students`);
  }

  getStudentById(id: number): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/admin/students/${id}`);
  }

  updateStudent(id: number, student: any): Observable<any> {
    return this.http.put<any>(`${this.API_URL}/admin/students/${id}`, student);
  }

  deleteStudent(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/admin/students/${id}`, { responseType: 'text' });
  }

  // ========== TEACHERS ==========
  
  getAllTeachers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/admin/teachers`);
  }

  getTeacherById(id: number): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/admin/teachers/${id}`);
  }

  updateTeacher(id: number, teacher: any): Observable<any> {
    return this.http.put<any>(`${this.API_URL}/admin/teachers/${id}`, teacher);
  }

  deleteTeacher(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/admin/teachers/${id}`, { responseType: 'text' });
  }

  // ========== USERS ==========
  
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/admin/users`);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/admin/users/${id}`, { responseType: 'text' });
  }

  // ========== STATISTICS ==========
  
  getStatistics(): Observable<AdminStatistics> {
    return this.http.get<AdminStatistics>(`${this.API_URL}/admin/statistics`);
  }

  // ========== USER MANAGEMENT ==========
  
  toggleUserStatus(id: number, active: boolean): Observable<any> {
    return this.http.put(`${this.API_URL}/admin/users/${id}/status?active=${active}`, null, { responseType: 'text' });
  }
}

