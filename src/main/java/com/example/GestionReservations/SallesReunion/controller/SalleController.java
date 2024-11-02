package com.example.GestionReservations.SallesReunion.controller;


import com.example.GestionReservations.SallesReunion.entities.Salle;
import com.example.GestionReservations.SallesReunion.repository.SalleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salle")
public class SalleController {

    @Autowired
    private SalleRepository salleRepository;

    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("salle", new Salle());
        return "add-salle";
    }

    @PostMapping("/ajouter")
    public String ajouterSalle(@Valid @ModelAttribute("salle") Salle salle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-salle";
        }
        salleRepository.save(salle);
        return "redirect:/salle/liste";
    }

    @GetMapping("/liste")
    public String afficherListeSalles(Model model) {
        model.addAttribute("salles", salleRepository.findAll());
        return "liste-salles";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable("id") long id, Model model) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de salle invalide : " + id));
        model.addAttribute("salle", salle);
        return "modifier-salle"; // Assurez-vous que le nom de la vue est correct
    }

    @PostMapping("/modifier/{id}")
    public String modifierSalle(@PathVariable("id") long id, @Valid Salle salle, BindingResult result, Model model) {
        if (result.hasErrors()) {
            salle.setId(id);
            return "modifier-salle"; // Assurez-vous que le nom de la vue est correct
        }
        salleRepository.save(salle);
        return "redirect:/salle/liste";
    }


    @GetMapping("/supprimer/{id}")
    public String supprimerSalle(@PathVariable("id") long id, Model model) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de salle invalide : " + id));
        salleRepository.delete(salle);
        return "redirect:/salle/liste";
    }
}




