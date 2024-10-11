# Projet Etude de cas de fin de bloc (Iscod)

## Prérequis

- Docker
- Git

## Information importante

Dans le cadre de ce projet, le cahier des charges a explicitement informé de la non-nécessité d'avoir une sécurité accrue, car cela n'a pas encore été vu en cours.
Ce projet n'a pas non plus vocation à entrer en production et à être disponible en ligne. Si cela devait changer un jour, les dispositions de sécurité seraient mises en place.
C'est pourquoi les mots de passe d'utilisateurs en base de données ne sont pas cryptés. De la même manière, l'authentification en frontend est validée par un cookie contenant l'id de l'utilisateur connecté.

## Installation

- Ouvrir un terminal de commande
- Lancer la commande ```git clone https://github.com/ReVolTPFE/iscod-b3-edc-spring-angular-app.git``` depuis le répertoire où vous souhaitez installer le projet.
- Lancer la commande ```cd iscod-b3-edc-spring-angular-app``` pour entrer dans le projet.
- Lancer la commande ```docker compose up --build```dans le terminal.

## Accès à l'application

- Base de données (phpMyAdmin -> user = root, password = root) : http://localhost:8000
- Backend Spring Boot (API donc pas de web configuré) : http://localhost:8080
- Frontend Angular : http://localhost:4200
- Interface mail catcher (maildev) : http://localhost:8081