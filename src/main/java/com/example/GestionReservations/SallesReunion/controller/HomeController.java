package com.example.GestionReservations.SallesReunion.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";  // Le nom de la vue pour la page d'accueil
    }
}

