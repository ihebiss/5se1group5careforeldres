package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Etablissement;
import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Repository.EtablissementRepository;
import com.example.carecareforeldres.Repository.InfrimerRepository;
import com.example.carecareforeldres.Repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceInfermier implements IServiceInfermier {
    InfrimerRepository infermierRepository;
    EtablissementRepository etablissementRepository;
    PatientRepository patientReopository;
    @Override
    public Infermier addInfermier(Infermier infermier) {
        return infermierRepository.save(infermier);
    }

    @Override
    public List<Infermier> retrieveAllInfermiers() {
        return infermierRepository.findAll();
    }

    @Override
    public Infermier updateInfermier(Infermier infermier) {
        return infermierRepository.save(infermier);
    }

    @Override
    public Infermier retrieveInfermier(Integer idInfermier) {
        return infermierRepository.findById(idInfermier).get();
    }

    @Override
    public void removeInfermier(Integer idInfermier) {
        infermierRepository.deleteById(idInfermier);

    }

    @Override
    public Infermier addInfermierAndAssignToEtabliss(Integer idinfermier, Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        Infermier infermier=infermierRepository.findById(idinfermier).get();
        infermier.setEtablissement(etablissement);
        return infermierRepository.save(infermier);
    }

    @Override
    public Infermier InfermierUnAssignToEtabliss(Integer idinfermier) {
        Infermier infermier=infermierRepository.findById(idinfermier).get();
        infermier.setEtablissement(null);
        return infermierRepository.save(infermier);
    }

    @Override
    public List<Infermier> ajouterInfermierToEtab(List<Infermier> infermiers, Long idEtab) {
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        for (Infermier i:infermiers) {
            i.setEtablissement(etablissement);
        }

        return infermierRepository.saveAll(infermiers);
    }

    @Override
    public List<Patient> findPatientEtabEqualInfEtab(Integer idInfermier) {
        return infermierRepository.findPatientEtabEqualInfEtab(idInfermier);
    }


}
