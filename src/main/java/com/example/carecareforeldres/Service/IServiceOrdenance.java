package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.DTO.OrdennanceDTO;
import com.example.carecareforeldres.Entity.Ordonnance;

import java.time.LocalDateTime;
import java.util.List;

public interface IServiceOrdenance {

    LocalDateTime getProchainRendezVousDuMedecin(Integer medecinId);
    List<Ordonnance> getOrdonnancesByPatient(Integer idPatient);
    Ordonnance addOrd(Ordonnance med,Integer idMedecin) ;

    List<Ordonnance> getAllOrd();

    void remove(int idf);

    Ordonnance update(Ordonnance med);
    OrdennanceDTO addOrdDTO(OrdennanceDTO med, Integer idMedecin);
}
