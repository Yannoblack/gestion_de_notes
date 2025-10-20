import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { StudentService } from '../../../core/services/student.service';
import { Student } from '../../../core/models/student.model';

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.scss']
})
export class StudentDetailComponent implements OnInit {
  student: Student | null = null;
  isLoading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private studentService: StudentService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadStudent(+id);
    }
  }

  loadStudent(id: number): void {
    this.isLoading = true;
    this.studentService.getStudentById(id).subscribe({
      next: (student) => {
        this.student = student;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading student:', error);
        this.isLoading = false;
        this.router.navigate(['/students']);
      }
    });
  }

  getFullName(student: Student): string {
    return `${student.firstName} ${student.lastName}`;
  }

  getStatusBadge(student: Student): string {
    return student.isActive ? 'Actif' : 'Inactif';
  }

  getStatusClass(student: Student): string {
    return student.isActive ? 'status-active' : 'status-inactive';
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }
}
