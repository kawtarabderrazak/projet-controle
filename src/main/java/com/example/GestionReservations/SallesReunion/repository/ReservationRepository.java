package com.example.GestionReservations.SallesReunion.repository;

import com.example.GestionReservations.SallesReunion.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici, si nécessaire
    List<Reservation> findBySalleIdAndDateAndHeureDebutLessThanEqualAndHeureFinGreaterThanEqual(
            Long salleId, LocalDate date, LocalTime heureDebut, LocalTime heureFin);
    List<Reservation> findByUtilisateur(String utilisateur);
    List<Reservation> findBySalleId(Long salleId);



}


