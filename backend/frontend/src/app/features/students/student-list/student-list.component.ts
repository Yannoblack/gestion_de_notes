import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../../core/services/student.service';
import { Student } from '../../../core/models/student.model';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.scss']
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];
  isLoading = false;
  searchTerm = '';
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;

  // Expose Math to template
  Math = Math;

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.isLoading = true;
    
    // Use search if searchTerm is provided, otherwise get all students
    const observable = this.searchTerm 
      ? this.studentService.searchStudents(this.searchTerm)
      : this.studentService.getAllStudents();
    
    observable.subscribe({
      next: (students) => {
        this.students = students || [];
        this.totalElements = students.length;
        this.totalPages = Math.ceil(students.length / this.pageSize);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading students:', error);
        this.isLoading = false;
      }
    });
  }

  onSearch(): void {
    this.currentPage = 0;
    this.loadStudents();
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadStudents();
  }

  deleteStudent(student: Student): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer l'étudiant ${student.firstName} ${student.lastName} ?`)) {
      this.studentService.deleteStudent(student.id).subscribe({
        next: () => {
          this.loadStudents();
        },
        error: (error) => {
          console.error('Error deleting student:', error);
          alert('Erreur lors de la suppression de l\'étudiant');
        }
      });
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    for (let i = 0; i < this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
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
}
