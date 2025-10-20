import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { GradeService } from '../../../core/services/grade.service';
import { Grade } from '../../../core/models/grade.model';

@Component({
  selector: 'app-grade-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './grade-list.component.html',
  styleUrls: ['./grade-list.component.scss']
})
export class GradeListComponent implements OnInit {
  grades: Grade[] = [];
  isLoading = false;
  searchTerm = '';
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;

  // Expose Math to template
  Math = Math;

  constructor(private gradeService: GradeService) {}

  ngOnInit(): void {
    this.loadGrades();
  }

  loadGrades(): void {
    this.isLoading = true;
    // For students: get their grades
    // For teachers/admins: this would need a different endpoint
    this.gradeService.getStudentGrades().subscribe({
      next: (grades) => {
        this.grades = grades || [];
        this.totalElements = grades.length;
        this.totalPages = Math.ceil(grades.length / this.pageSize);
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading grades:', error);
        this.isLoading = false;
      }
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadGrades();
  }

  deleteGrade(grade: Grade): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer cette note ?`)) {
      this.gradeService.deleteGrade(grade.id).subscribe({
        next: () => {
          this.loadGrades();
        },
        error: (error) => {
          console.error('Error deleting grade:', error);
          alert('Erreur lors de la suppression de la note');
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

  getGradePercentage(grade: Grade): number {
    return (grade.score / grade.maxScore) * 100;
  }

  getGradeClass(grade: Grade): string {
    const percentage = this.getGradePercentage(grade);
    if (percentage >= 80) return 'grade-excellent';
    if (percentage >= 60) return 'grade-good';
    if (percentage >= 40) return 'grade-average';
    return 'grade-poor';
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }
}
