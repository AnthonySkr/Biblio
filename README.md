# Système de Gestion de Bibliothèque

Application console Java pour gérer une bibliothèque avec persistance des données.

## Fonctionnalités

### Gestion des Livres
- Ajouter, modifier et supprimer des livres
- Lister tous les livres, les livres disponibles ou empruntés
- Rechercher par titre, auteur ou genre

### Gestion des Utilisateurs
- Ajouter, modifier et supprimer des utilisateurs
- Lister tous les utilisateurs
- Consulter l'historique des emprunts d'un utilisateur

### Gestion des Emprunts
- Emprunter et retourner des livres
- Lister tous les emprunts ou seulement les emprunts actifs
- Traçabilité complète des emprunts avec dates

## Base de données

L'application utilise **SQLite** pour la persistance des données.
- La base de données `library.db` est créée automatiquement au premier lancement
- Les données sont conservées entre les exécutions
- Le DatabaseManager utilise le pattern Singleton

### Structure de la base
- **books** : id, title, author, genre, is_available
- **users** : id, name
- **loans** : id, book_id, user_id, loan_date, return_date

## Architecture

```
src/
├── Main.java                    # Point d'entrée avec menus
├── database/
│   └── DatabaseManager.java     # Gestion de la connexion SQLite (Singleton)
├── models/
│   ├── Book.java               # Modèle Livre
│   ├── User.java               # Modèle Utilisateur
│   └── Loan.java               # Modèle Emprunt
└── services/
    ├── BookService.java        # Logique métier des livres
    ├── UserService.java        # Logique métier des utilisateurs
    └── LoanService.java        # Logique métier des emprunts
```
