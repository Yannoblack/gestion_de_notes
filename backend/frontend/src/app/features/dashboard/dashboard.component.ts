import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { StudentService } from '../../core/services/student.service';
import { GradeService } from '../../core/services/grade.service';
import { User, UserRole } from '../../core/models/user.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  stats = {
    totalStudents: 0,
    totalGrades: 0,
    totalSubjects: 0,
    averageGrade: 0
  };
  isLoading = true;

  constructor(
    private authService: AuthService,
    private studentService: StudentService,
    private gradeService: GradeService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      if (user) {
        this.loadDashboardData();
      }
    });
  }

  private loadDashboardData(): void {
    if (!this.currentUser) return;

    // Load different data based on user role
    if (this.currentUser.role === UserRole.ADMIN || this.currentUser.role === UserRole.TEACHER) {
      this.loadAdminTeacherData();
    } else if (this.currentUser.role === UserRole.STUDENT) {
      this.loadStudentData();
    }
  }

  private loadAdminTeacherData(): void {
    // Load students count
    this.studentService.getAllStudents().subscribe(students => {
      this.stats.totalStudents = students.length || 0;
    });

    // Load subjects count (only for teachers)
    if (this.currentUser?.role === UserRole.TEACHER) {
      this.gradeService.getTeacherSubjects().subscribe(subjects => {
        this.stats.totalSubjects = subjects.length;
        this.isLoading = false;
      });
    } else {
      // For admins, we'll need an admin endpoint later
      this.stats.totalSubjects = 0;
      this.isLoading = false;
    }
  }

  private loadStudentData(): void {
    // Get student statistics
    this.studentService.getStudentStatistics().subscribe(stats => {
      this.stats.averageGrade = stats.overallAverage || 0;
      this.stats.totalGrades = stats.totalGrades || 0;
      this.isLoading = false;
    });
  }

  get welcomeMessage(): string {
    if (!this.currentUser) return '';
    
    const roleMessages = {
      [UserRole.ADMIN]: 'Administrateur',
      [UserRole.TEACHER]: 'Enseignant',
      [UserRole.STUDENT]: 'Étudiant'
    };

    return `Bienvenue, ${this.currentUser.firstName} ${this.currentUser.lastName} (${roleMessages[this.currentUser.role]})`;
  }

  get dashboardCards(): any[] {
    if (!this.currentUser) return [];

    if (this.currentUser.role === UserRole.STUDENT) {
      return [
        {
          title: 'Ma moyenne générale',
          value: this.stats.averageGrade.toFixed(2),
          icon: '📊',
          color: 'primary'
        },
        {
          title: 'Total des notes',
          value: this.stats.totalGrades,
          icon: '📝',
          color: 'secondary'
        }
      ];
    } else {
      return [
        {
          title: 'Total des étudiants',
          value: this.stats.totalStudents,
          icon: '👥',
          color: 'primary',
          link: '/students'
        },
        {
          title: 'Total des notes',
          value: this.stats.totalGrades,
          icon: '📝',
          color: 'secondary',
          link: '/grades'
        },
        {
          title: 'Total des matières',
          value: this.stats.totalSubjects,
          icon: '📚',
          color: 'success',
          link: '/subjects'
        }
      ];
    }
  }
}
