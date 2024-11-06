package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Reclamation;
import com.example.carecareforeldres.Entity.ReponseReclamation;

import java.util.List;

public interface IReponseReclamationService {
    public ReponseReclamation ajouterReponseAReclamation(Long idReclamation, ReponseReclamation reponseReclamation);

    public List<Reclamation> getReclamationsWithResponsesByUserId(Integer Id);


}
