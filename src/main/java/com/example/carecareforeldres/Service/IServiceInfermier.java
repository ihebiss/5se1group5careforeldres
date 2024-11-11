package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Patient;

import java.util.List;


public interface IServiceInfermier {
    public Infermier addInfermier(Infermier infermier);
    public List<Infermier> retrieveAllInfermiers();

    public Infermier updateInfermier(Infermier infermier);

    public Infermier retrieveInfermier(Integer idInfermier);
    void removeInfermier(Integer idInfermier);
    public Infermier addInfermierAndAssignToEtabliss (Integer idinfermier, Long idEtab);
    public Infermier InfermierUnAssignToEtabliss (Integer idinfermier);

    public List<Infermier> ajouterInfermierToEtab (List<Infermier> infermiers, Long idEtab);

    List<Patient> findPatientEtabEqualInfEtab(Integer idInfermier);



}
