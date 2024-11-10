package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.*;
import com.example.carecareforeldres.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@Slf4j
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ServicePatientTest {
    @Autowired
     AmbulanceRepository ambulanceRepository;
    @Autowired
     EtablissementRepository etablissementRepository;
    @Autowired
     PatientRepository patientRepository;
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    InfrimerRepository infrimerRepository;
    @Autowired
    ServiceMedecin serviceMedecin;
    @Autowired
    ServiceInfermier serviceInfermier;
    @Autowired
     ServiceAmbulance serviceAmbulance;
    @Autowired
     ServicePatient servicePatient;
    @Autowired
    ServiceEtablissement serviceEtablissement;

    @Test
    @Order(1)
    public void testAssignPatientToAmbulance() {
        Ambulance ambulance = Ambulance.builder().busy(false).x(16.08).y(22.0).marque("VW").matricule("245TUN1999").build();
        ambulance = ambulanceRepository.save(ambulance);


        Patient patient = Patient.builder().nom("Mohamed Amine").x(56.78).y(9.0).prenom("Brahmi").typatient(TypePatient.URGENT).build();
        patient = patientRepository.save(patient);

        Patient patientToTest = servicePatient.AssignPatientToAmbulance(patient.getIdpatient(), ambulance.getIdAmb());

        Assertions.assertNotNull(patientToTest.getIdpatient());
        Assertions.assertNotNull(patientToTest.getAmbulance().getIdAmb());

        Assertions.assertEquals(TypePatient.URGENT, patientToTest.getTypatient());
        Assertions.assertEquals(ambulance.getIdAmb(), patientToTest.getAmbulance().getIdAmb());
        Assertions.assertTrue(patientToTest.getAmbulance().getBusy());

        log.info("Id du Ambulance Initial "+ ambulance.getIdAmb());
        log.info("Id  Ambulance Patient  "+ patientToTest.getAmbulance().getIdAmb());
        log.info("état du Patient  "+ patientToTest.getTypatient());

        Patient patientSaved = patientRepository.save(patient);

        Etablissement etablissement = Etablissement.builder().nomEtab("Clinique Al Majed").nbLits(5).build();
        etablissement = etablissementRepository.save(etablissement);

        Medecin medecin = Medecin.builder().nom("Ok").prenom("CC").build();
        medecin = medecinRepository.save(medecin);


        Infermier infermier = Infermier.builder().nom("Infermier").prenom("YYYYYY").build();
        infermier = infrimerRepository.save(infermier);

        Assertions.assertNotNull(medecin.getIdMedecin());
        Assertions.assertNotNull(etablissement.getIdEtab());
        Assertions.assertNotNull(infermier.getIdInfermier());


        Patient updatedPatient = servicePatient.UnassignPatientFromAmbulanceAndAffectToEtab(
                patientSaved.getIdpatient() , etablissement.getIdEtab(), medecin.getIdMedecin(), infermier.getIdInfermier());

        Patient lastPatient=patientRepository.save(updatedPatient);



        Assertions.assertEquals(etablissement.getIdEtab(), updatedPatient.getEtablissement().getIdEtab());
        Assertions.assertEquals(lastPatient.getMedecin().getIdMedecin(), medecin.getIdMedecin());
        Assertions.assertEquals(lastPatient.getInfermier().getIdInfermier(), infermier.getIdInfermier());
        Assertions.assertEquals(4, etablissementRepository.findById(etablissement.getIdEtab()).get().getNbLits());

        log.info("nbr Lits "+etablissementRepository.findById(etablissement.getIdEtab()).get().getNbLits());


        servicePatient.removePatient(patientToTest.getIdpatient());
        servicePatient.removePatient(patientSaved.getIdpatient());
        servicePatient.removePatient(lastPatient.getIdpatient());

        serviceAmbulance.removeAmbulance(ambulance.getIdAmb());
        serviceInfermier.removeInfermier(infermier.getIdInfermier());
        serviceMedecin.removeMedecin(medecin.getIdMedecin());
        serviceEtablissement.removeEtablissement(etablissement.getIdEtab());


    }


}