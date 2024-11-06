package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Patient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IServicePatient {
    public Patient addPatient(Patient patient);
    public List<Patient> retrieveAllPatients();

    public Patient updatePatient(Patient patient);

    public Patient retrievePatient(Integer idpatient);
    void    removePatient(Integer idpatient);
    public Patient AssignPatientToAmbulance (Integer idpatient, Long idAmb);
    public Patient UnassignPatientFromAmbulanceAndAffectToEtab (Integer idpatient,Long idEtab,Integer idMedecin,Integer idInfermier );
    public Patient UnassignPatientFromMorgue (Integer idpatient);
    public Patient AssignInfermierTopPatient (Integer idpatient,Integer idInfermier);

    public Long retrieveidAmbulance(Integer idpatient)throws IOException;

    public List<Patient> GetPatientsALlInAmbulance();

    public Patient miseAjourPatientEtablisement_Genric(Patient patient);

    public List<Patient> GetPatientsDeath();
    public LocalDateTime DateDEDrnierRdv(Integer idpatient);
    public Map<String, Float> FindPatirntInEtab(Long idEtab);

    Map<Long, Float> getAmbulance(Patient p) throws IOException;







}


