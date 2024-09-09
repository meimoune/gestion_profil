package com.example.gestion_profil_back.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestion_profil_back.entity.Utilisateur;
import com.example.gestion_profil_back.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs/")
public class UtilisateurController {
      @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("create")
    public ResponseEntity<Utilisateur> creerUtilisateur(
            @RequestParam("nom") String nom,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "cv", required = false) MultipartFile cv) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);

        try {
            Utilisateur nouveauUtilisateur = utilisateurService.creerUtilisateur(utilisateur, photo, cv);


            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauUtilisateur);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "cv", required = false) MultipartFile cv) {
        Utilisateur utilisateurDetails = new Utilisateur();
        utilisateurDetails.setNom(nom);

        try {
            Utilisateur utilisateurMisAJour = utilisateurService.updateUtilisateur(id, utilisateurDetails, photo, cv);
            return ResponseEntity.ok(utilisateurMisAJour);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
