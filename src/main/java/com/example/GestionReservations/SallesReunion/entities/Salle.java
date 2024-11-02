package com.example.GestionReservations.SallesReunion.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private int capacite;

    @NotBlank(message = "Les équipements sont obligatoires")
    private String equipements;

    public Salle() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    // Getter public pour la propriété equipements
    public String getEquipements() {
        return equipements;
    }

    // Setter pour la propriété equipements
    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }
}
