le rapport complet   [gestin des projets.pdf](https://github.com/user-attachments/files/24459332/gestin.des.projets.pdf)
# 🚀 ProjectManager Pro - Application de Gestion de Projets

Bienvenue dans **ProjectManager Pro**, une application web complète basée sur Spring Boot pour la gestion collaborative des projets informatiques.

Ce projet a été réalisé dans le cadre de notre formation académique. Il permet aux responsables de planifier des tâches et aux développeurs de suivre leur avancement en temps réel.

---

## 📑 Rapport de Projet
Vous pouvez consulter le rapport détaillé du projet ici :
👉 **[gestin des projets.pdf](https://github.com/user-attachments/files/24459342/gestin.des.projets.pdf)
**

---

## 🛠️ Prérequis Techniques
Avant de lancer l'application, assurez-vous d'avoir installé :
* **Java 17** (JDK)
* **MySQL** (Serveur de base de données)
* **Maven** (Optionnel, si vous utilisez un IDE comme VS Code ou IntelliJ)

---

## ⚙️ Installation et Configuration

### 1. Base de Données
1.  Ouvrez **phpMyAdmin** ou votre client MySQL.
2.  Créez une nouvelle base de données nommée exactement : `planification_db`
3.  Importez le fichier SQL fourni dans le dossier `database` de ce dépôt (ou laissez Hibernate générer les tables automatiquement via `update`).
4.  **Important :** L'application est configurée pour se connecter au port **3306** avec l'utilisateur `root` et sans mot de passe.
    * *Si votre configuration est différente, modifiez le fichier `src/main/resources/application.properties`.*

### 2. Démarrage de l'Application
Vous pouvez lancer l'application de deux manières :

**Option A : Via Terminal (Recommandé)**
Ouvrez un terminal à la racine du projet et tapez :
```bash
./mvnw spring-boot:run
