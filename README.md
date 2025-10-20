# 📚 Student Grade Management System

Un système complet de gestion des notes d'étudiants développé avec **Spring Boot** (Backend) et **Angular** (Frontend).

## 🚀 Aperçu du Projet

Ce système permet aux administrateurs, enseignants et étudiants de gérer efficacement les notes, matières et informations académiques dans un environnement sécurisé et moderne.

### ✨ Fonctionnalités Principales

- 🔐 **Authentification JWT** avec rôles (Admin, Teacher, Student)
- 👥 **Gestion des utilisateurs** (étudiants, enseignants, administrateurs)
- 📝 **Gestion des notes** avec calcul automatique des moyennes
- 📚 **Gestion des matières** et programmes
- 📊 **Tableaux de bord** personnalisés par rôle
- 📧 **Notifications par email**
- 🔍 **Recherche et filtrage** avancés
- 📱 **Interface responsive** et moderne

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.x
- **Base de données**: PostgreSQL
- **Sécurité**: Spring Security + JWT
- **Documentation**: Swagger/OpenAPI 3
- **ORM**: Spring Data JPA + Hibernate

### Frontend (Angular)
- **Framework**: Angular 17+
- **UI**: Angular Material + Bootstrap
- **Routing**: Angular Router avec guards
- **HTTP**: Angular HttpClient avec interceptors
- **State**: Services Angular

## 📁 Structure du Projet

```
gestion_note/
├── backend/                    # API Spring Boot
│   ├── src/main/java/
│   │   └── com/projetfulstack/studentgrademanagement/
│   │       ├── config/        # Configuration (Swagger, Security)
│   │       ├── controller/    # Contrôleurs REST
│   │       ├── dto/           # Data Transfer Objects
│   │       ├── entity/        # Entités JPA
│   │       ├── repository/    # Repositories JPA
│   │       └── security/      # Configuration sécurité
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data-sample.sql
│   └── pom.xml
└── frontend/                   # Application Angular
    ├── src/app/
    │   ├── core/              # Services, guards, interceptors
    │   ├── features/          # Modules fonctionnels
    │   │   ├── auth/          # Authentification
    │   │   ├── dashboard/     # Tableaux de bord
    │   │   ├── grades/        # Gestion des notes
    │   │   ├── students/      # Gestion étudiants
    │   │   └── subjects/      # Gestion matières
    │   └── shared/            # Composants partagés
    ├── src/environments/
    └── package.json
```

## 🛠️ Technologies Utilisées

### Backend
- **Java 17+**
- **Spring Boot 3.2+**
- **Spring Security 6**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT (JSON Web Tokens)**
- **Swagger/OpenAPI 3**
- **Maven**

### Frontend
- **Angular 17+**
- **TypeScript 5+**
- **Angular Material**
- **Bootstrap 5**
- **RxJS**
- **Node.js 18+**

## 🚀 Installation et Configuration

### Prérequis
- Java 17+
- Node.js 18+
- PostgreSQL 13+
- Maven 3.8+
- Git

### 1. Cloner le projet
```bash
git clone <repository-url>
cd gestion_note
```

### 2. Configuration de la base de données
```sql
-- Créer la base de données
CREATE DATABASE student_grade_management;

-- Créer un utilisateur (optionnel)
CREATE USER student_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE student_grade_management TO student_user;
```

### 3. Configuration Backend

#### 3.1. Variables d'environnement
Modifiez `src/main/resources/application.properties` :

```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/student_grade_management
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe

# JWT
jwt.secret=votre_secret_jwt_ultra_securise
jwt.expiration=86400000

# Email (optionnel)
spring.mail.host=smtp.gmail.com
spring.mail.username=votre-email@gmail.com
spring.mail.password=votre_mot_de_passe_app
```

#### 3.2. Démarrer le backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Le serveur sera disponible sur : `http://localhost:8081`

### 4. Configuration Frontend

#### 4.1. Installer les dépendances
```bash
cd frontend
npm install
```

#### 4.2. Configuration de l'environnement
Modifiez `src/environments/environment.ts` :

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/api'
};
```

#### 4.3. Démarrer le frontend
```bash
ng serve
```

L'application sera disponible sur : `http://localhost:4200`

## 📖 Documentation API

### Swagger UI
Une fois le backend démarré, accédez à la documentation interactive :
- **URL**: `http://localhost:8081/api/swagger-ui.html`
- **API Docs**: `http://localhost:8081/api/v3/api-docs`

### Endpoints Principaux

#### Authentification
- `POST /api/auth/login` - Connexion
- `POST /api/auth/register/student` - Inscription étudiant
- `POST /api/auth/register/teacher` - Inscription enseignant

#### Étudiants
- `GET /api/student/profile` - Profil étudiant
- `GET /api/student/grades` - Notes de l'étudiant
- `GET /api/student/grades/subject/{id}` - Notes par matière

#### Administration
- `GET /api/admin/students` - Liste des étudiants
- `GET /api/admin/teachers` - Liste des enseignants
- `GET /api/admin/subjects` - Liste des matières
- `POST /api/admin/subjects` - Créer une matière

## 🔐 Système d'Authentification

### Rôles
- **ADMIN**: Accès complet au système
- **TEACHER**: Gestion des notes et étudiants
- **STUDENT**: Consultation de ses notes

### JWT Token
```bash
# Exemple d'utilisation
curl -H "Authorization: Bearer <your-jwt-token>" \
     http://localhost:8081/api/student/profile
```

## 🎨 Interface Utilisateur

### Pages Principales
- **Login/Register**: Authentification
- **Dashboard**: Vue d'ensemble personnalisée
- **Students**: Gestion des étudiants
- **Grades**: Gestion des notes
- **Subjects**: Gestion des matières
- **Profile**: Profil utilisateur

### Responsive Design
- Compatible mobile et desktop
- Interface moderne avec Angular Material
- Navigation intuitive

## 🧪 Tests

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
ng test
```

## 🚀 Déploiement

### Backend (Production)
```bash
# Build
mvn clean package -Pproduction

# Docker
docker build -t student-grade-backend .
docker run -p 8081:8081 student-grade-backend
```

### Frontend (Production)
```bash
# Build
ng build --configuration=production

# Serveur web
# Déployer le contenu de dist/ sur votre serveur web
```

## 📊 Base de Données

### Entités Principales
- **User**: Utilisateurs du système
- **Student**: Étudiants
- **Teacher**: Enseignants
- **Subject**: Matières
- **Grade**: Notes
- **Class**: Classes

### Relations
- Student → Grade (One-to-Many)
- Subject → Grade (One-to-Many)
- Teacher → Subject (One-to-Many)

## 🔧 Configuration Avancée

### CORS
```properties
app.cors.allowed-origins=http://localhost:4200
app.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
app.cors.allowed-headers=*
```

### Logging
```properties
logging.level.com.projetfulstack.studentgrademanagement=DEBUG
logging.level.org.springframework.security=DEBUG
```
## 📝 Changelog

### Version 1.0.0
- ✅ Authentification JWT
- ✅ Gestion des utilisateurs
- ✅ Gestion des notes
- ✅ Interface Angular moderne
- ✅ Documentation Swagger

## 🐛 Problèmes Connus

- [ ] Configuration email à finaliser
- [ ] Tests d'intégration à compléter
- [ ] Optimisation des requêtes

---

**Développé avec ❤️ par Bomgnin Pategou Yannic**
