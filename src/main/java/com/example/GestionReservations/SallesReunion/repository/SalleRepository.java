package com.example.GestionReservations.SallesReunion.repository;


import com.example.GestionReservations.SallesReunion.entities.Salle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends CrudRepository<Salle, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici, si nécessaire
}

