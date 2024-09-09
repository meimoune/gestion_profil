package com.example.gestion_profil_back.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestion_profil_back.entity.Utilisateur;
import com.example.gestion_profil_back.exceptions.ResourceNotFoundException;
import com.example.gestion_profil_back.repository.UtilisateurRepository;


@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Dossier pour stocker les fichiers téléchargés
    private static final String UPLOAD_DIR = "uploads/";

    public Utilisateur creerUtilisateur(Utilisateur utilisateur, MultipartFile photo, MultipartFile cv) throws IOException {
        // Sauvegarder les fichiers dans le dossier de téléchargement
        if (photo != null && !photo.isEmpty()) {
            Path photoPath = Paths.get(UPLOAD_DIR + photo.getOriginalFilename());
            Files.createDirectories(photoPath.getParent());
            Files.write(photoPath, photo.getBytes());
            utilisateur.setPhotoProfil(photoPath.toString());
        }

        if (cv != null && !cv.isEmpty()) {
            Path cvPath = Paths.get(UPLOAD_DIR + cv.getOriginalFilename());
            Files.createDirectories(cvPath.getParent());
            Files.write(cvPath, cv.getBytes());
            utilisateur.setCv(cvPath.toString());
        }

        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    // Nouvelle méthode pour mettre à jour l'utilisateur
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails, MultipartFile photo, MultipartFile cv) throws IOException {
        Utilisateur utilisateur = getUtilisateurById(id);

        utilisateur.setNom(utilisateurDetails.getNom()); // Mise à jour des informations de base

        // Mise à jour de la photo de profil si une nouvelle est envoyée
        if (photo != null && !photo.isEmpty()) {
            Path photoPath = Paths.get(UPLOAD_DIR + photo.getOriginalFilename());
            Files.createDirectories(photoPath.getParent());
            Files.write(photoPath, photo.getBytes());
            utilisateur.setPhotoProfil(photoPath.toString());
        }

        // Mise à jour du CV si un nouveau est envoyé
        if (cv != null && !cv.isEmpty()) {
            Path cvPath = Paths.get(UPLOAD_DIR + cv.getOriginalFilename());
            Files.createDirectories(cvPath.getParent());
            Files.write(cvPath, cv.getBytes());
            utilisateur.setCv(cvPath.toString());
        }

        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
