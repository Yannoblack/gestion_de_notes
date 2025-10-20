import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { GradeService } from '../../../core/services/grade.service';
import { Subject } from '../../../core/models/grade.model';

@Component({
  selector: 'app-subject-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.scss']
})
export class SubjectListComponent implements OnInit {
  subjects: Subject[] = [];
  isLoading = false;

  constructor(private gradeService: GradeService) {}

  ngOnInit(): void {
    this.loadSubjects();
  }

  loadSubjects(): void {
    this.isLoading = true;
    this.gradeService.getAllSubjects().subscribe({
      next: (subjects) => {
        this.subjects = subjects;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading subjects:', error);
        this.isLoading = false;
      }
    });
  }

  deleteSubject(subject: Subject): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer la matière "${subject.name}" ?`)) {
      this.gradeService.deleteSubject(subject.id).subscribe({
        next: () => {
          this.loadSubjects();
        },
        error: (error) => {
          console.error('Error deleting subject:', error);
          alert('Erreur lors de la suppression de la matière');
        }
      });
    }
  }

  getStatusBadge(subject: Subject): string {
    return subject.isActive ? 'Actif' : 'Inactif';
  }

  getStatusClass(subject: Subject): string {
    return subject.isActive ? 'status-active' : 'status-inactive';
  }
}
