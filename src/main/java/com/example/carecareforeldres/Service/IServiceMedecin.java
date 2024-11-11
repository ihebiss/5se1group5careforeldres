package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Specialite;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceMedecin {
    public Medecin addMedecin(Medecin medecin);

    public List<Medecin> retrieveAllMedecins();

    public Medecin updateMedecin(Medecin medecin);

    public Medecin retrieveMedecin(Integer idMedecin);

    void removeMedecin(Integer idMedecin);

    public Medecin retrieveMedecinBySpecialite(Specialite specialite);

    public Medecin addMedecinAndAssignToEtabliss(Integer idMedecin, Long idEtab);
    public Medecin desactfMedecinEtabliss(Integer idMedecin);


    public List<Medecin> ajouterMedecinToEtab(List<Medecin> medecins, Long idEtab);

    public int getNbrRndvBetweenTwoDates(Integer idMedecin, LocalDateTime startDate, LocalDateTime endDate);

    List<Medecin> findTop3MedecinBenevole(LocalDateTime startDate, LocalDateTime endDate);

}







