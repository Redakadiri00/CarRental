# Car Rental Management System

---

## **Description du Projet**

Le projet **Car Rental Management System** est une application web qui permet de gérer les opérations de location de véhicules. Ce système vise à simplifier la gestion des véhicules, des utilisateurs, des réservations, des factures et des contrats pour une entreprise de location.

Cette application est développée en **Java Spring Boot**, avec une base de données **PostgreSQL**. Elle respecte une architecture **MVC** et expose des API REST pour interagir avec les différentes fonctionnalités.

---

## **Fonctionnalités Principales**

### **1. Gestion des Utilisateurs**
- Enregistrement et authentification des utilisateurs (clients et administrateurs).
- Gestion des profils : mise à jour des informations personnelles (nom, adresse, numéro de téléphone, etc.).

### **2. Gestion des Véhicules**
- Ajouter, modifier et supprimer des véhicules par les administrateurs.
- Consulter la liste des véhicules disponibles avec leurs caractéristiques (marque, modèle, tarif, etc.).

### **3. Réservation de Véhicules**
- Recherche de véhicules disponibles par date, type, ou autres critères.
- Création de réservations pour une période donnée.
- Annulation et suivi des réservations.

### **4. Gestion des Factures et des Contrats**
- Génération automatique des factures après une réservation confirmée.
- Calcul automatique des montants (en fonction de la durée et du tarif journalier).
- Gestion des statuts des factures (PAYEE, NON_PAYEE).
- Génération automatique des contrats après la création d'une facture.
- Suivi des statuts des contrats (EN_ATTENTE, VALIDEE, TERMINEE).

### **5. Rapports et Tableau de Bord**
- Génération de rapports sur :
    - Les réservations (par période, véhicule, client).
    - Les revenus totaux.
- Visualisation des statistiques via une interface admin.

---

## **Environnement Technique**

### **Langages et Frameworks**
- **Backend** : Java (Spring Boot)
- **Frontend** : Swagger (documentation des API REST)
- **Base de données** : PostgreSQL
- **Conteneurisation** : Docker

### **Outils et Dépendances**
- Maven
- Lombok
- Hibernate (JPA)
- Docker
- Swagger

### **Architecture**
- **MVC** (Modèle-Vue-Contrôleur)
- API REST exposée pour les interactions avec l'application.

---

## **Installation et Configuration**

### **1. Prérequis**
- Java 17 ou supérieur
- Maven installé
- Docker installé
- PostgreSQL (optionnel si Docker est utilisé pour la base de données)

### **2. Installation**
1. Clonez le dépôt GitHub :
   ```bash
   git clone https://github.com/Redakadiri00/CarRental.git
   cd CarRental
   ```
2. Configurez la base de données PostgreSQL (via Docker) :
   ```bash
   docker run -d --name postgres-container -p 5432:5432 \
       -e POSTGRES_USER=admin \
       -e POSTGRES_PASSWORD=admin \
       -e POSTGRES_DB=car_rental_db \
       postgres:latest
   ```

3. Modifiez le fichier `application.properties` :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/car_rental_db
   spring.datasource.username=admin
   spring.datasource.password=admin
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.show-sql=true
   ```

4. Compilez et exécutez le projet :
   ```bash
   ./mvnw spring-boot:run
   ```

### **3. Conteneurisation avec Docker**
1. Construisez l'image Docker pour l'application :
   ```bash
   docker build -t CarRental .
   ```
2. Lancez l'application et PostgreSQL dans un réseau Docker :
   ```bash
   docker network create car_rental_network
   docker run -d --name postgres-container --network car_rental_network \
       -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=car_rental_db postgres:latest
   docker run -d --name car_rental_app --network car_rental_network -p 8080:8080 car_rental_app
   ```

---

## **Tests et Validation**

### **1. Tests Unitaires**
- Testez les services et la logique métier avec JUnit et Mockito.

### **2. Tests d’Intégration**
- Vérifiez que les API REST fonctionnent correctement en utilisant Postman.

### **3. Tests de Performance**
- Utilisez des outils comme JMeter pour tester les performances des endpoints critiques.

---

## **Améliorations Futures**
- Ajout d’un frontend pour une interface utilisateur conviviale.
- Génération de PDF pour les factures et contrats.
- Intégration d’une passerelle de paiement en ligne.
- Ajout d’une gestion des permissions pour les administrateurs.

---

## **Contribution**
Les contributions sont les bienvenues ! Veuillez soumettre un pull request avec des explications détaillées des modifications.

---


## **Licence**
Ce projet est sous licence MIT. Vous êtes libre de le modifier et de le distribuer à condition de conserver les informations relatives à la licence originale.
