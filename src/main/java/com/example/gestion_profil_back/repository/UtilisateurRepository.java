package com.example.gestion_profil_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestion_profil_back.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
