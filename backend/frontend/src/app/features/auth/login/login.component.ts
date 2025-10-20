import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { LoginRequest } from '../../../core/models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    console.log('=== DEBUT CONNEXION ===');
    console.log('Form valid:', this.loginForm.valid);
    console.log('Form value:', this.loginForm.value);
    
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      const loginRequest: LoginRequest = this.loginForm.value;
      console.log('Sending login request:', loginRequest);

      this.authService.login(loginRequest).subscribe({
        next: (response) => {
          console.log('✅ LOGIN SUCCESS:', response);
          this.isLoading = false;
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error('❌ LOGIN ERROR');
          console.error('Error status:', error.status);
          console.error('Error statusText:', error.statusText);
          console.error('Error body:', error.error);
          console.error('Full error:', error);
          
          this.isLoading = false;
          
          // Message d'erreur plus détaillé
          if (error.status === 401 || error.status === 403) {
            this.errorMessage = 'Email ou mot de passe incorrect';
          } else if (error.status === 0) {
            this.errorMessage = 'Impossible de se connecter au serveur';
          } else {
            this.errorMessage = error.error?.message || error.error || 'Une erreur est survenue';
          }
          
          console.error('Displayed error:', this.errorMessage);
        }
      });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs correctement';
    }
  }

  getFieldError(fieldName: string): string {
    const field = this.loginForm.get(fieldName);
    if (field?.errors && field.touched) {
      if (field.errors['required']) {
        return `${fieldName === 'email' ? 'Email' : 'Mot de passe'} requis`;
      }
      if (field.errors['email']) {
        return 'Email invalide';
      }
      if (field.errors['minlength']) {
        return 'Mot de passe trop court (minimum 6 caractères)';
      }
    }
    return '';
  }
}
