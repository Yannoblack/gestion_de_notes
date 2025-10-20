import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'dashboard',
    canActivate: [AuthGuard],
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'students',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEACHER', 'ADMIN'] },
    loadComponent: () => import('./features/students/student-list/student-list.component').then(m => m.StudentListComponent)
  },
  {
    path: 'students/:id',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEACHER', 'ADMIN'] },
    loadComponent: () => import('./features/students/student-detail/student-detail.component').then(m => m.StudentDetailComponent)
  },
  {
    path: 'grades',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEACHER', 'ADMIN'] },
    loadComponent: () => import('./features/grades/grade-list/grade-list.component').then(m => m.GradeListComponent)
  },
  {
    path: 'subjects',
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEACHER', 'ADMIN'] },
    loadComponent: () => import('./features/subjects/subject-list/subject-list.component').then(m => m.SubjectListComponent)
  },
  {
    path: 'profile',
    canActivate: [AuthGuard],
    loadComponent: () => import('./features/profile/profile.component').then(m => m.ProfileComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./shared/components/not-found/not-found.component').then(m => m.NotFoundComponent)
  }
];
