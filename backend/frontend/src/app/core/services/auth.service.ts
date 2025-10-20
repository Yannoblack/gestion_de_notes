import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User, LoginRequest, LoginResponse, RegisterRequest, StudentRegisterRequest, TeacherRegisterRequest, UserRole } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = environment.apiUrl;
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.initializeAuth();
  }

  private initializeAuth(): void {
    const token = localStorage.getItem(this.TOKEN_KEY);
    const userStr = localStorage.getItem(this.USER_KEY);
    
    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        this.currentUserSubject.next(user);
      } catch (error) {
        this.logout();
      }
    }
  }

  register(registerData: RegisterRequest): Observable<any> {
    return this.http.post(`${this.API_URL}/auth/register`, registerData);
  }

  registerStudent(studentData: StudentRegisterRequest): Observable<any> {
    const url = `${this.API_URL}/auth/register/student`;
    console.log('[AuthService] Registering student to:', url);
    console.log('[AuthService] Student data:', studentData);
    return this.http.post(url, studentData, { responseType: 'text' });
  }

  registerTeacher(teacherData: TeacherRegisterRequest): Observable<any> {
    const url = `${this.API_URL}/auth/register/teacher`;
    console.log('[AuthService] Registering teacher to:', url);
    console.log('[AuthService] Teacher data:', teacherData);
    return this.http.post(url, teacherData, { responseType: 'text' });
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<any>(`${this.API_URL}/auth/login`, credentials)
      .pipe(
        tap(response => {
          console.log('[AuthService] Login response:', response);
          
          // Le backend retourne "accessToken" et non "token"
          const token = response.accessToken || response.token;
          
          // Construire l'objet user à partir de la réponse
          const user: User = {
            id: response.userId || response.id,
            username: response.email.split('@')[0], // Extraire username de l'email
            email: response.email,
            firstName: response.firstName,
            lastName: response.lastName,
            role: response.role as UserRole,
            isActive: true,
            createdAt: new Date(),
            updatedAt: new Date()
          };
          
          console.log('[AuthService] Saving token:', token);
          console.log('[AuthService] Saving user:', user);
          
          localStorage.setItem(this.TOKEN_KEY, token);
          localStorage.setItem(this.USER_KEY, JSON.stringify(user));
          this.currentUserSubject.next(user);
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUserSubject.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  hasRole(role: UserRole): boolean {
    const user = this.getCurrentUser();
    return user?.role === role;
  }

  hasAnyRole(roles: UserRole[]): boolean {
    const user = this.getCurrentUser();
    return user ? roles.includes(user.role) : false;
  }

  isAdmin(): boolean {
    return this.hasRole(UserRole.ADMIN);
  }

  isTeacher(): boolean {
    return this.hasRole(UserRole.TEACHER);
  }

  isStudent(): boolean {
    return this.hasRole(UserRole.STUDENT);
  }
}
