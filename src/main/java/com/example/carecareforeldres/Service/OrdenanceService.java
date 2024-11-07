package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.DTO.OrdennanceDTO;
import com.example.carecareforeldres.Entity.Medicament;
import com.example.carecareforeldres.Entity.Ordonnance;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Repository.MedicamentRepository;
import com.example.carecareforeldres.Repository.OrdenanceRepository;
import com.example.carecareforeldres.Repository.RdvRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrdenanceService implements IServiceOrdenance{

    OrdenanceRepository ordenanceRepository;
    MedicamentRepository medicamentRepository;
    RdvRepository rdvRepository;
    @Override
    public Ordonnance addOrd(Ordonnance med,Integer idMedecin) {
        med.setDateAjout(LocalDate.now());
        med.setIdMedecin(idMedecin);
        return ordenanceRepository.save(med);}
   @Override
   @Transactional
   public OrdennanceDTO addOrdDTO(OrdennanceDTO med, Integer idMedecin) {
       Ordonnance ordonnance = new Ordonnance();
       ordonnance.setTitre(med.getTitre());
       ordonnance.setDosage(med.getDosage());
       ordonnance.setFrequence(med.getFrequence());
       ordonnance.setDuree(med.getDuree());
       ordonnance.setDateAjout(med.getDateAjout());
       ordonnance.setInstructions(med.getInstructions());
       ordonnance.setPatient(med.getPatient());
       ordonnance.setIdMedecin(idMedecin);

       List<Medicament> medicaments = new ArrayList<>();
       for (Integer medicaId : med.getMdicamentsIds()) {
           Optional<Medicament> medicamentOptional = medicamentRepository.findById(medicaId);
           medicamentOptional.ifPresent(medicaments::add);
       }
       ordonnance.setMedicaments(medicaments);

        ordenanceRepository.save(ordonnance);
        return med;
   }



    @Override
    public List<Ordonnance> getAllOrd(){return ordenanceRepository.findAll();}
    public List<Ordonnance> getAllOrdByMed(Integer id){
       List<Ordonnance> ordonnanceList=new ArrayList<>();
        for (Ordonnance o:ordenanceRepository.findAll()){
            if (o.getIdMedecin()==id)
                ordonnanceList.add(o);
        }
    return ordonnanceList;
    }
    @Override
    public void remove(int idf) {
        ordenanceRepository.deleteOrdonnanceMedicament(idf);
        ordenanceRepository.deleteById(idf);}

    @Override
    public Ordonnance update(Ordonnance med) {

        return ordenanceRepository.save(med);
    }

    @Override
    public List<Ordonnance> getOrdonnancesByPatient(Integer idPatient){
    return ordenanceRepository.getOrdonnancesByPatient(idPatient);
    }
    @Override
    public LocalDateTime getProchainRendezVousDuMedecin(Integer medecinId) {
        return ordenanceRepository.findProchainRendezVousDuMedecin(medecinId);
    }

    public List<Rdv> getRendezVousDuMedecin(Integer medecinId) {
        return rdvRepository.findByMedecinidMedecin(medecinId);
    }
}
