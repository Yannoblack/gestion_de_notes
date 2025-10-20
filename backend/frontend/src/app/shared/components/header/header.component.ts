import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { User, UserRole } from '../../../core/models/user.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  currentUser: User | null = null;

  constructor(public authService: AuthService) {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
  }

  logout(): void {
    this.authService.logout();
  }

  get userRole(): string {
    if (!this.currentUser) return '';
    
    switch (this.currentUser.role) {
      case UserRole.ADMIN:
        return 'Administrateur';
      case UserRole.TEACHER:
        return 'Enseignant';
      case UserRole.STUDENT:
        return 'Étudiant';
      default:
        return 'Utilisateur';
    }
  }
}
