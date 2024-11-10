package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Reclamation;
import com.example.carecareforeldres.Entity.ReponseReclamation;
import com.example.carecareforeldres.Repository.ReclamationRepository;
import com.example.carecareforeldres.Repository.ReponseReclamationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReponseReclamationService implements IReponseReclamationService {
    private ReclamationRepository reclamationRepository;
    private ReponseReclamationRepository reponseReclamationRepository;

    @Override
    public ReponseReclamation ajouterReponseAReclamation(Long idReclamation, ReponseReclamation reponseReclamation) {
        Reclamation reclamation = reclamationRepository.findById(idReclamation)
                .orElseThrow(() -> new EntityNotFoundException("Réclamation non trouvée avec l'ID : " + idReclamation));
        reponseReclamation.setReclamation(reclamation);
        reponseReclamation.setDateReponse(new Date()); // Remplir avec la date actuelle
        return reponseReclamationRepository.save(reponseReclamation);
    }

    @Override
    public List<Reclamation> getReclamationsWithResponsesByUserId(Integer Id) {
        List<Reclamation> reclamations = reclamationRepository.findByUserId(Id);
        List<Reclamation> reclamationsWithResponses = new ArrayList<>();

        for (Reclamation reclamation : reclamations) {
            ReponseReclamation reponse = reponseReclamationRepository.findByReclamationIdReclamation (reclamation.getIdReclamation());
            if (reponse != null) {
                reclamation.setReponseReclamation(reponse);
            }
            reclamationsWithResponses.add(reclamation);
        }

        return reclamationsWithResponses;
    }
}





