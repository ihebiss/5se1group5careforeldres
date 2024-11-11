package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.DTO.UserDto;
import com.example.carecareforeldres.Entity.Reclamation;
import com.example.carecareforeldres.Entity.ReponseReclamation;
import com.example.carecareforeldres.Entity.TypeReclamation;
import com.example.carecareforeldres.Entity.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IReclamationService {
    public List<UserDto> deuxUtilisateursAvecPlusReclamationsImportant();
    public void envoyerMessageUtilisateur(Integer userId);
    Reclamation ajouterReclamation(Integer id, Reclamation reclamation);
    void supprimerReclamation(Long idReclamation);
    public List<Reclamation> getAllReclamations();
    public List<Reclamation> getReclamationsByUserId(Integer Id);
    public int getTotalReclamationsByDate(LocalDate date);
    public Map<Reclamation, ReponseReclamation> getReclamationsWithResponsesByUserId(Integer idUser);
    public List<Reclamation> getReclamationsByType(TypeReclamation type);
    public List<Reclamation> classerReclamationsParImportance();

    public boolean reclamationEstRepondue(Reclamation reclamation);
    public List<Reclamation> obtenirReclamationsNonReponduesDepuisDeuxJours();
}
