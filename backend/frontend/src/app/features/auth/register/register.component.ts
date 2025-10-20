import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { UserRole, StudentRegisterRequest, TeacherRegisterRequest } from '../../../core/models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  userRoles = UserRole;
  
  // Pour gérer l'affichage des champs conditionnels
  selectedRole: UserRole | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      role: ['', Validators.required],
      
      // Student fields
      studentId: [''],
      
      // Teacher fields
      employeeId: [''],
      department: [''],
      specialization: [''],
      
      // Common optional fields
      address: ['', Validators.maxLength(100)],
      phoneNumber: ['', Validators.maxLength(20)],
      dateOfBirth: ['']
    }, { validators: this.passwordMatchValidator });

    // Écouter les changements de rôle
    this.registerForm.get('role')?.valueChanges.subscribe((role: UserRole) => {
      this.selectedRole = role;
      this.updateValidators(role);
    });
  }

  // Validateur personnalisé pour vérifier que les mots de passe correspondent
  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    
    return null;
  }

  // Mettre à jour les validateurs selon le rôle
  updateValidators(role: UserRole) {
    const studentId = this.registerForm.get('studentId');
    const employeeId = this.registerForm.get('employeeId');

    // Réinitialiser tous les validateurs
    studentId?.clearValidators();
    employeeId?.clearValidators();

    // Ajouter les validateurs selon le rôle
    if (role === UserRole.STUDENT) {
      studentId?.setValidators([Validators.required, Validators.maxLength(20)]);
    } else if (role === UserRole.TEACHER) {
      employeeId?.setValidators([Validators.required, Validators.maxLength(20)]);
    }

    // Mettre à jour la validation
    studentId?.updateValueAndValidity();
    employeeId?.updateValueAndValidity();
  }

  onSubmit() {
    console.log('=== DEBUT INSCRIPTION ===');
    console.log('Form valid:', this.registerForm.valid);
    console.log('Form value:', this.registerForm.value);
    
    if (this.registerForm.invalid) {
      console.log('Form is invalid!');
      Object.keys(this.registerForm.controls).forEach(key => {
        const control = this.registerForm.controls[key];
        if (control.invalid) {
          console.log(`Field ${key} is invalid:`, control.errors);
        }
        this.registerForm.controls[key].markAsTouched();
      });
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const formValue = this.registerForm.value;
    console.log('Selected role:', formValue.role);

    // Utiliser les nouveaux endpoints spécifiques selon le rôle
    if (formValue.role === UserRole.STUDENT) {
      const studentData: StudentRegisterRequest = {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        email: formValue.email,
        password: formValue.password,
        studentId: formValue.studentId,
        address: formValue.address || undefined,
        phoneNumber: formValue.phoneNumber || undefined,
        dateOfBirth: formValue.dateOfBirth || undefined
      };

      console.log('Sending student data:', studentData);
      console.log('POST URL:', `${this.authService['API_URL']}/auth/register/student`);

      this.authService.registerStudent(studentData).subscribe({
        next: (response) => {
          console.log('✅ SUCCESS - Student registered:', response);
          this.isLoading = false;
          this.successMessage = 'Inscription réussie ! Redirection vers la page de connexion...';
          
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (error) => {
          console.error('❌ ERROR - Registration failed');
          console.error('Error status:', error.status);
          console.error('Error statusText:', error.statusText);
          console.error('Error body:', error.error);
          console.error('Full error object:', error);
          console.error('Error type:', typeof error.error);
          
          this.isLoading = false;
          this.errorMessage = this.extractErrorMessage(error);
          console.error('Displayed error message:', this.errorMessage);
        }
      });

    } else if (formValue.role === UserRole.TEACHER) {
      const teacherData: TeacherRegisterRequest = {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        email: formValue.email,
        password: formValue.password,
        employeeId: formValue.employeeId,
        address: formValue.address || undefined,
        phoneNumber: formValue.phoneNumber || undefined,
        dateOfBirth: formValue.dateOfBirth || undefined,
        department: formValue.department || undefined,
        specialization: formValue.specialization || undefined
      };

      console.log('Sending teacher data:', teacherData);
      console.log('POST URL:', `${this.authService['API_URL']}/auth/register/teacher`);

      this.authService.registerTeacher(teacherData).subscribe({
        next: (response) => {
          console.log('✅ SUCCESS - Teacher registered:', response);
          this.isLoading = false;
          this.successMessage = 'Inscription réussie ! Redirection vers la page de connexion...';
          
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (error) => {
          console.error('❌ ERROR - Registration failed');
          console.error('Error status:', error.status);
          console.error('Error statusText:', error.statusText);
          console.error('Error body:', error.error);
          console.error('Full error object:', error);
          console.error('Error type:', typeof error.error);
          
          this.isLoading = false;
          this.errorMessage = this.extractErrorMessage(error);
          console.error('Displayed error message:', this.errorMessage);
        }
      });

    } else {
      // Bloquer l'enregistrement des administrateurs
      this.isLoading = false;
      this.errorMessage = 'L\'enregistrement des administrateurs n\'est pas autorisé.';
    }
  }

  // Méthodes helper pour le template
  isFieldInvalid(fieldName: string): boolean {
    const field = this.registerForm.get(fieldName);
    return !!(field && field.invalid && field.touched);
  }

  getFieldError(fieldName: string): string {
    const field = this.registerForm.get(fieldName);
    if (!field || !field.errors || !field.touched) return '';

    if (field.errors['required']) return 'Ce champ est requis';
    if (field.errors['email']) return 'Email invalide';
    if (field.errors['minlength']) return `Minimum ${field.errors['minlength'].requiredLength} caractères`;
    if (field.errors['maxlength']) return `Maximum ${field.errors['maxlength'].requiredLength} caractères`;
    if (field.errors['passwordMismatch']) return 'Les mots de passe ne correspondent pas';

    return 'Champ invalide';
  }

  showStudentFields(): boolean {
    return this.selectedRole === UserRole.STUDENT;
  }

  showTeacherFields(): boolean {
    return this.selectedRole === UserRole.TEACHER;
  }

  // Méthode pour extraire le message d'erreur de manière appropriée
  private extractErrorMessage(error: any): string {
    // Si error.error est une chaîne de caractères
    if (typeof error.error === 'string') {
      return error.error;
    }
    
    // Si error.error est un objet avec une propriété message
    if (error.error && typeof error.error === 'object' && error.error.message) {
      return error.error.message;
    }
    
    // Si error.error est un objet avec une propriété error
    if (error.error && typeof error.error === 'object' && error.error.error) {
      return error.error.error;
    }
    
    // Si error.message existe
    if (error.message) {
      return error.message;
    }
    
    // Si error.statusText existe
    if (error.statusText) {
      return error.statusText;
    }
    
    // Message par défaut
    return 'Une erreur est survenue lors de l\'inscription. Veuillez réessayer.';
  }
}

