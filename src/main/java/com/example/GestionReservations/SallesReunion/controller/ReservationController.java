package com.example.GestionReservations.SallesReunion.controller;

import com.example.GestionReservations.SallesReunion.entities.Reservation;
import com.example.GestionReservations.SallesReunion.entities.Salle;
import com.example.GestionReservations.SallesReunion.repository.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.GestionReservations.SallesReunion.repository.SalleRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @GetMapping("/reservations")
    public String listeReservation(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());  // Récupère toutes les réservations
        return "indexR";  // Utilise indexR comme vue pour afficher la liste des réservations
    }

    @GetMapping("/reservation/ajouter")
    public String showAddReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation()); // Crée une nouvelle réservation
        model.addAttribute("salles", salleRepository.findAll()); // Ajoutez la liste des salles
        return "add-reservation";  // Vue pour le formulaire d'ajout de réservation
    }

    @PostMapping("/reservation/ajouter")
    public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("salles", salleRepository.findAll());
            return "add-reservation";  // Retourne à la vue de formulaire si la validation échoue
        }
        reservationRepository.save(reservation);
        return "redirect:/reservations";  // Redirige vers la liste des réservations
    }

    // Affiche le formulaire de mise à jour
    @GetMapping("/reservation/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            model.addAttribute("reservation", reservation);
            model.addAttribute("salles", salleRepository.findAll()); // Si vous avez besoin de la liste des salles
            return "update-reservation"; // Vue pour afficher le formulaire de mise à jour
        }
        return "redirect:/reservations/historique"; // Redirige si la réservation n'existe pas
    }

    @PostMapping("/reservation/update/{id}")
    public String updateReservation(@PathVariable("id") Long id,
                                    @RequestParam("salleId") Long salleId,
                                    @RequestParam("date") LocalDate date,
                                    @RequestParam("heureDebut") LocalTime heureDebut,
                                    @RequestParam("heureFin") LocalTime heureFin,
                                    @RequestParam("utilisateur") String utilisateur) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            Salle salle = salleRepository.findById(salleId).orElse(null);
            reservation.setSalle(salle);
            reservation.setDate(date);
            reservation.setHeureDebut(heureDebut);
            reservation.setHeureFin(heureFin);
            reservation.setUtilisateur(utilisateur);
            reservationRepository.save(reservation);
        }
        return "redirect:/reservation/confirmation";  // Redirige vers une page de confirmation
    }


    @GetMapping("/reservation/confirmation")
    public String afficherConfirmation(Model model) {
        // Vous pouvez ajouter des attributs au modèle si nécessaire
        return "confirmation"; // Cela doit correspondre à votre fichier confirmation.html
    }


    @PostMapping("/reservation/creer")
    public String creerReservation(@RequestParam("salleId") Long salleId,
                                   @RequestParam("date") LocalDate date,
                                   @RequestParam("heureDebut") LocalTime heureDebut,
                                   @RequestParam("heureFin") LocalTime heureFin,
                                   @RequestParam("utilisateur") String utilisateur,
                                   Model model) {
        Salle salle = salleRepository.findById(salleId).orElse(null);
        if (salle != null) {
            Reservation reservation = new Reservation();
            reservation.setSalle(salle);
            reservation.setDate(date);
            reservation.setHeureDebut(heureDebut);
            reservation.setHeureFin(heureFin);
            reservation.setUtilisateur(utilisateur);
            reservationRepository.save(reservation);
        }
        return "redirect:/reservation/confirmation";  // Redirige vers une page de confirmation
    }


    @GetMapping("/reservation/disponibles")
    public String getSallesDisponibles(@RequestParam("date") LocalDate date,
                                       @RequestParam("heureDebut") LocalTime heureDebut,
                                       @RequestParam("heureFin") LocalTime heureFin,
                                       Model model) {
        // Récupérer toutes les salles
        List<Salle> toutesSalles = (List<Salle>) salleRepository.findAll();

        // Filtrer les salles disponibles en fonction des réservations existantes
        List<Salle> sallesDisponibles = toutesSalles.stream()
                .filter(salle -> reservationRepository
                        .findBySalleIdAndDateAndHeureDebutLessThanEqualAndHeureFinGreaterThanEqual(
                                salle.getId(), date, heureDebut, heureFin).isEmpty())
                .collect(Collectors.toList());

        // Ajouter les salles disponibles et les informations de réservation au modèle
        model.addAttribute("sallesDisponibles", sallesDisponibles);
        model.addAttribute("date", date);
        model.addAttribute("heureDebut", heureDebut);
        model.addAttribute("heureFin", heureFin);

        // Retourner la vue pour afficher les salles disponibles
        return "disponibilite-salles";
    }

    @GetMapping("/reservations/historique")
    public String afficherHistorique(@RequestParam(required = false) String utilisateur,
                                     @RequestParam(required = false) Long salleId,
                                     Model model) {
        List<Reservation> reservations;

        if (utilisateur != null && !utilisateur.isEmpty()) {
            reservations = reservationRepository.findByUtilisateur(utilisateur);
        } else if (salleId != null) {
            reservations = reservationRepository.findBySalleId(salleId);
        } else {
            reservations = reservationRepository.findAll();
        }

        model.addAttribute("reservations", reservations);
        model.addAttribute("salles", salleRepository.findAll()); // Ajoutez ceci pour la liste des salles
        return "historique"; // Remplacez par le nom de votre template
    }










    @GetMapping("/reservation/delete/{id}")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Réservation supprimée avec succès !");
        return "redirect:/reservations/historique";
    }


}
