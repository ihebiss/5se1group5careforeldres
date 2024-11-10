package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.Entity.Etablissement;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Entity.Specialite;
import com.example.carecareforeldres.Repository.EtablissementRepository;
import com.example.carecareforeldres.Repository.MedecinRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceMedecin implements IServiceMedecin{
    MedecinRepository medecinRepository;
    EtablissementRepository etablissementRepository;
    PatientRepository patientReopository;
    @Override
    public Medecin addMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public List<Medecin> retrieveAllMedecins() {
        return medecinRepository.findAll();
    }

    @Override
    public Medecin updateMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public Medecin retrieveMedecin(Integer idMedecin) {
        return medecinRepository.findById(idMedecin).get();
    }

    @Override
    public void removeMedecin(Integer idMedecin) {
        medecinRepository.deleteById(idMedecin);
    }

    @Override
    public Medecin retrieveMedecinBySpecialite(Specialite specialite) {
        return medecinRepository.findBySpecialite(specialite);
    }

    @Override
    public Medecin addMedecinAndAssignToEtabliss(Integer idMedecin, Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        Medecin medecin=medecinRepository.findById(idMedecin).get();
        medecin.setEtablissement(etablissement);
        return medecinRepository.save(medecin);

    }

    @Override
    public Medecin desactfMedecinEtabliss(Integer idMedecin) {
        Medecin medecin=medecinRepository.findById(idMedecin).get();
        medecin.setEtablissement(null);
        return medecinRepository.save(medecin);
    }

    @Override
    public List<Medecin> ajouterMedecinToEtab(List<Medecin> medecins, Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        for (Medecin m:medecins){
            m.setEtablissement(etablissement);}
        return medecinRepository.saveAll(medecins);
    }

    @Override
    public int getNbrRndvBetweenTwoDates(Integer idMedecin, LocalDateTime startDate, LocalDateTime endDate) {
        int a=0;
        List<Rdv> rendezvous=new ArrayList<>();
        Medecin medecin=medecinRepository.findById(idMedecin).get();
        for (Rdv rdv: medecin.getRdvs()){
            if (rdv.getDateRDV().isAfter(startDate) && rdv.getDateRDV().isBefore(endDate)){
                rendezvous.add(rdv);
                a=a+1;
            }
        }
        return a;
    }

    @Override
    public List<Medecin> findTop3MedecinBenevole(LocalDateTime startDate, LocalDateTime endDate) {
        List<Map.Entry<Medecin, Integer>> top = new ArrayList<>();

        List<Medecin> medecins = medecinRepository.findTop3MedecinBenevole(startDate, endDate);

        Map<Medecin, Integer> medecinRdvsCount = new HashMap<>();
        for (Medecin medecin : medecins) {
            int rdvsCount = medecin.getRdvs().size();
            medecinRdvsCount.put(medecin, rdvsCount);
        }

        List<Map.Entry<Medecin, Integer>> sortedEntries = new ArrayList<>(medecinRdvsCount.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        top.add(sortedEntries.get(0));
        top.add(sortedEntries.get(1));
        top.add(sortedEntries.get(2));

        Medecin m1 = top.get(0).getKey();
        Medecin m2 = top.get(1).getKey();
        Medecin m3 = top.get(2).getKey();

        List<Medecin> top3Medecins = new ArrayList<>();
        top3Medecins.add(m1);
        top3Medecins.add(m2);
        top3Medecins.add(m3);

        return top3Medecins;
    }



}