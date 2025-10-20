import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../core/models/user.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  currentUser: User | null = null;
  profileForm: FormGroup;
  isEditing = false;
  isSaving = false;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) {
    this.profileForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      if (user) {
        this.populateForm(user);
      }
    });
  }

  private populateForm(user: User): void {
    this.profileForm.patchValue({
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      username: user.username
    });
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing && this.currentUser) {
      this.populateForm(this.currentUser);
    }
  }

  saveProfile(): void {
    if (this.profileForm.valid) {
      this.isSaving = true;
      // Here you would typically call an API to update the user profile
      // For now, we'll just simulate the save
      setTimeout(() => {
        this.isSaving = false;
        this.isEditing = false;
        alert('Profil mis à jour avec succès!');
      }, 1000);
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    if (this.currentUser) {
      this.populateForm(this.currentUser);
    }
  }

  getFieldError(fieldName: string): string {
    const field = this.profileForm.get(fieldName);
    if (field?.errors && field.touched) {
      if (field.errors['required']) {
        return 'Ce champ est requis';
      }
      if (field.errors['email']) {
        return 'Format d\'email invalide';
      }
      if (field.errors['minlength']) {
        return 'Ce champ est trop court';
      }
    }
    return '';
  }

  getUserRole(): string {
    if (!this.currentUser) return '';
    
    switch (this.currentUser.role) {
      case 'ADMIN':
        return 'Administrateur';
      case 'TEACHER':
        return 'Enseignant';
      case 'STUDENT':
        return 'Étudiant';
      default:
        return 'Utilisateur';
    }
  }

  getFormattedDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }
}
