package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.AmbulanceRepository;
import com.example.carecareforeldres.Repository.EtablissementRepository;
import com.example.carecareforeldres.Repository.MedecinRepository;
import com.example.carecareforeldres.Repository.MorgueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceEtablissement implements IServiceEtablissement {
    EtablissementRepository etablissementRepository;
    MorgueRepository morgueRepository;
    AmbulanceRepository ambulanceRepository;
    IServiceMapbox mapbox;
    MedecinRepository medecinRepository;
    @Override
    public Etablissement addEtablissement(Etablissement etablissement) throws IOException {
        etablissement.setAdresse( mapbox.getAddressFromCoordinates(etablissement.getY(),etablissement.getX()));
        return etablissementRepository.save(etablissement);
    }

    @Override
    public List<Etablissement> retrieveAllEtablissements() {
        return etablissementRepository.findAll();
    }

    @Override
    public Etablissement updateEtablissement(Etablissement etablissement) throws IOException{
        etablissement.setAdresse( mapbox.getAddressFromCoordinates(etablissement.getY(),etablissement.getX()));

        return etablissementRepository.save(etablissement);
    }

    @Override
    public Etablissement retrieveEtablissement(Long idEtab) {
        return etablissementRepository.findById(idEtab).get();
    }

    @Override
    public void removeEtablissement(Long idEtab) {
        etablissementRepository.deleteById(idEtab);

    }

  //  @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void Changement_Etat_Ambul() {
        LocalDate date=LocalDate.now();
        List<Etablissement>etablissements=etablissementRepository.findAll();
        for (Etablissement e:etablissements){
            for (Ambulance ambulance:e.getAmbulances()){
                if (date.equals(ambulance.getDateDernEntret().plusYears(1))){
                    ambulance.setEtatAmb(EtatAmb.MAINTENANCE);
                    ambulance.setBusy(true);
                    ambulance.setDateDernEntret(date);
                    ambulanceRepository.save(ambulance);

                }
                if (ambulance.getEtatAmb()==EtatAmb.PANNE){
                    ambulance.setBusy(true);
                    ambulanceRepository.save(ambulance);

                }
            }
        }

    }
   // @Scheduled(cron = "0 0 0 *  * *")
    @Override
    public void MaintenanceToFonctionelle() {
        LocalDate date=LocalDate.now();
        List<Etablissement> etablissements =etablissementRepository.findAll();
        for (Etablissement e:etablissements){
            for (Ambulance ambulance:e.getAmbulances()){
                if (ambulance.getEtatAmb()==EtatAmb.MAINTENANCE){
                    if (date.equals(ambulance.getDateDernEntret().plusDays(1))){
                        ambulance.setEtatAmb(EtatAmb.FONCTIONNELLE);
                        ambulance.setBusy(false);
                        ambulanceRepository.save(ambulance);
                    }
                }
            }
        }

    }
   // @Scheduled(cron = "* * * * * *")
    @Override
    public void EtatAmbulance() {
        List<Etablissement> etablissements=etablissementRepository.findAll();
        for (Etablissement e:etablissements){
            for (Ambulance ambulance:e.getAmbulances()){
                if (ambulance.getEtatAmb()==EtatAmb.FONCTIONNELLE && ambulance.getPatients().isEmpty()){
                    ambulance.setBusy(false);
                    ambulanceRepository.save(ambulance);
                }
                else if (ambulance.getEtatAmb()==EtatAmb.FONCTIONNELLE && !ambulance.getPatients().isEmpty()){
                    ambulance.setBusy(true);
                    ambulanceRepository.save(ambulance);
                }}
        }

    }

    @Override
    public List<Medecin> getMedecinsByEtab(Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        return etablissement.getMedecins();
    }

    @Override
    public List<Infermier> getInfirmiersByEtab(Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        return etablissement.getInfermiers();
    }

    @Override
    public List<Patient> getPatientsByEtabMedecin(Integer idMed) {
        Medecin med = medecinRepository.findById(idMed).get();
        Etablissement etablissement = med.getEtablissement();
        return etablissement.getPatients();
    }
    //@Scheduled(cron = "* * * * * *")
    @Override
    public void AdresseVérification() {
        for (Etablissement e:etablissementRepository.findAll()){
            for (Ambulance a:ambulanceRepository.findAll()){
                if (a.getBusy()==false){
                    a.setX(e.getX());
                    a.setY(e.getY());
                    a.setAdresse(e.getAdresse());
                    ambulanceRepository.save(a);
                }
                else {
                    a.setX(null);
                    a.setY(null);
                    a.setAdresse(null);
                }
                ambulanceRepository.save(a);

            }
        }

    }


}
