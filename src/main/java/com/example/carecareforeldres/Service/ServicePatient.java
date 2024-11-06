package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.Configurations.CapaciteMax;
import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ServicePatient implements IServicePatient {
    PatientRepository patientReopository;
    AmbulanceRepository ambulanceRepository;
    MedecinRepository medecinRepository;
    InfrimerRepository infermierRepository;
    EtablissementRepository etablissementRepository;
    MorgueRepository morgueRepository;
    RdvRepository rdvRepository;
    ServiceMapBox box;

    @Override
    public Patient addPatient(Patient patient) {
        return patientReopository.save(patient);
    }

    @Override
    public List<Patient> retrieveAllPatients() {
        return patientReopository.findAll();
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientReopository.save(patient);
    }

    @Override
    public Patient retrievePatient(Integer idMed) {
        return patientReopository.findById(idMed).get();
    }

    @Override
    public void removePatient(Integer idMedecin) {
        patientReopository.deleteById(idMedecin);

    }

    @Override
    public Patient AssignPatientToAmbulance(Integer idpatient, Long idAmb) {
        idAmb=ambulanceRepository.findAmbulance().get(0).getIdAmb();
        Ambulance ambulance=ambulanceRepository.findById(idAmb).get();
        Patient patient=patientReopository.findById(idpatient).get();
        if (patient.getTypatient()== TypePatient.URGENT&&ambulance.getBusy()==false){
            patient.setAmbulance(ambulance);
            ambulance.setBusy(true);
        }
        return patientReopository.save(patient);

    }

    @Override
    public Patient UnassignPatientFromAmbulanceAndAffectToEtab(Integer idpatient,Long idEtab, Integer idMedecin, Integer idInfermier) {
        Patient patient=patientReopository.findById(idpatient).get();
        Etablissement etablissement=etablissementRepository.findById(idEtab).get();
        Medecin medecin=medecinRepository.findById(idMedecin).get();
        Infermier infermier=infermierRepository.findById(idInfermier).get();
        if (etablissement.getNbLits()==0){
            throw new CapaciteMax("Capacité Maximal atteinte");
        }
        else {
            patient.setEtablissement(etablissement);
            patient.setMedecin(medecin);
            patient.setInfermier(infermier);
            etablissement.setNbLits(etablissement.getNbLits()-1);
            patient.setAmbulance(null);
            return patientReopository.save(patient);
        }}



    @Override
    public Patient UnassignPatientFromMorgue(Integer idpatient) {
        Patient patient = patientReopository.findById(idpatient).get();
        Morgue morgue=patient.getMorgue();
        morgue.setNbCadavre(morgue.getNbCadavre()-1);
        patient.setMorgue(null);
        patient.setEtablissement(null);
        return patientReopository.save(patient);
    }


    @Override
    public Patient miseAjourPatientEtablisement_Genric( Patient patient){
        Etablissement etablissement = patient.getEtablissement();
        Morgue morgue=etablissement.getMorgue();
        if (patient.getTypatient()==TypePatient.NORMAL) {
            etablissement.setNbLits(etablissement.getNbLits() + 1);
            patient.setMedecin(null);
            patient.setInfermier(null);
            patient.setEtablissement(null);
        }
        if (patient.getTypatient()==TypePatient.DECEDE) {
            patient.setMorgue(morgue);
            patient.setInfermier(null);
            patient.setMedecin(null);
            patient.setArchiver(true);
            morgue.setEtablissement(etablissement);
            patient.getEtablissement().setNbLits(patient.getEtablissement().getNbLits()+1);
            morgue.setNbCadavre(morgue.getNbCadavre()+1);
        }
        return patientReopository.save(patient);
    }

    @Override
    public List<Patient> GetPatientsDeath() {
        List <Patient> patients= new ArrayList<>();
        for (Patient p:patientReopository.findAll()){
            if(p.getMorgue()!=null){
                patients.add(p);
            }
        }

        return patients;
    }

    @Override
    public LocalDateTime DateDEDrnierRdv(Integer idpatient) {
        List<Rdv> rdvs=rdvRepository.findRdvByPatient(idpatient);
        Rdv rdv=rdvs.get(0);
        return rdv.getDateRDV() ;
    }

    @Override
    public Map<String, Float> FindPatirntInEtab(Long idEtab) {
        List<Patient> patients = patientReopository.findPatientInEtab(idEtab);
        List<Patient> hommes = new ArrayList<>();
        List<Patient> femmes = new ArrayList<>();

        for (Patient p : patients) {
            if (p.getSexe() == Sexe.HOMME) {
                hommes.add(p);
            } else if (p.getSexe() == Sexe.FEMME) {
                femmes.add(p);
            }
        }

        float pourcentHommes = (float) hommes.size() / patients.size() * 100;
        float pourcentFemmes = (float) femmes.size() / patients.size() * 100;

        Map<String, Float> percentages = new HashMap<>();
        percentages.put("Pourcentage Hommes", pourcentHommes);
        percentages.put("Pourcentage Femmes", pourcentFemmes);

        return percentages;
    }




    @Override
    public Patient AssignInfermierTopPatient( Integer idpatient,Integer idInfermier) {
        Patient patient=patientReopository.findById(idpatient).get();
        Infermier infermier= infermierRepository.findById(idInfermier).get();
        patient.setInfermier(infermier);
        return patientReopository.save(patient);
    }

    @Override
    public Long retrieveidAmbulance(Integer idpatient)throws IOException {
        Patient patient = patientReopository.findById(idpatient).get();

        Map<Long, Float> ambulanceDistances = getAmbulance(patient);
        Long closestAmbulanceId = null;

        if (!ambulanceDistances.isEmpty()) {
            Map.Entry<Long, Float> closestAmbulanceEntry = ambulanceDistances.entrySet().iterator().next();
            closestAmbulanceId = closestAmbulanceEntry.getKey();
        } else {
            System.err.println("No ambulances found or distance information missing.");
        }

        return closestAmbulanceId;

    }
    @Override
    public Map<Long, Float> getAmbulance(Patient p) throws IOException {
        List<Ambulance> ambulances = ambulanceRepository.findAmbulance();
        Map<Long, Float> ambulanceDistances = new HashMap<>();

        for (Ambulance ambulance : ambulances) {
            Map<String, String> infoMap = box.getInfo(String.valueOf(p.getX()), String.valueOf(p.getY()), String.valueOf(ambulance.getX()), String.valueOf(ambulance.getY()));
            String distanceStr = infoMap.get("Distance");
            if (distanceStr != null) {
                distanceStr = distanceStr.replaceAll("[^0-9.]", "");
                float distance = Float.parseFloat(distanceStr);
                ambulanceDistances.put(ambulance.getIdAmb(), distance);
            } else {
                System.err.println("Distance information missing for Ambulance ID: " + ambulance.getIdAmb());
            }
        }
        List<Map.Entry<Long, Float>> sortedEntries = ambulanceDistances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        Map<Long, Float> closestDistances = new HashMap<>();
        int count = 0;
        for (Map.Entry<Long, Float> entry : sortedEntries) {
            closestDistances.put(entry.getKey(), entry.getValue());
            count++;
            if (count >= 3) {
                break;
            }
        }

        return closestDistances;
    }

    @Override
    public List<Patient> GetPatientsALlInAmbulance() {
        LocalDateTime startDate = LocalDate.now().atStartOfDay();
        LocalDateTime endDate = startDate.plusHours(23).plusMinutes(59).plusSeconds(59);

        return patientReopository.findPatientAmbulance(startDate, endDate);
    }
}


