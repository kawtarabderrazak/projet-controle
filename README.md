# projet-controle
Gestion des Réservations de Salles de Réunion

Description
Cette application est conçue pour la gestion des réservations de salles de réunion. Elle permet aux utilisateurs de consulter les salles disponibles, d’effectuer des réservations pour des créneaux horaires donnés, et de visualiser l'historique des réservations pour chaque salle et utilisateur.

Fonctionnalités
Consultation des Salles Disponibles : Affichage des salles avec leurs caractéristiques (capacité, équipements).
Réservation de Salles : Les utilisateurs peuvent réserver une salle en spécifiant la date, l'heure de début et l'heure de fin.
Historique des Réservations : Visualisation de l’historique des réservations, par utilisateur et par salle.

Entités
1. Salle
nom : Nom de la salle.
capacité : Capacité maximale de la salle.
équipements : Liste des équipements disponibles dans la salle (ex. : projecteur, tableau blanc).
2. Réservation
date : Date de la réservation.
heure début : Heure de début de la réservation.
heure fin : Heure de fin de la réservation.
salle associée : Salle réservée.
utilisateur : Nom de l’utilisateur qui a effectué la réservation.

Technologies Utilisées
Backend : Spring Boot
Frontend : Thymeleaf pour les templates HTML
Base de Données : MySQL
 
