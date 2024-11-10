package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.*;
import com.example.carecareforeldres.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.InjectMocks;


import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ServicePatientMockTest {
    @MockBean
    AmbulanceRepository ambulanceRepository;
    @MockBean
    PatientRepository patientRepository;
    @MockBean
    EtablissementRepository etablissementRepository;
    @MockBean
    MedecinRepository medecinRepository;
    @MockBean
    InfrimerRepository infrimerRepository;
    @InjectMocks
    ServicePatient servicePatient;
    @Test
    public void testAssignPNerastatientToAmbulance() {
        Ambulance ambulance = Ambulance.builder().idAmb(1L).busy(false).x(16.08).y(22.0).marque("VW").matricule("245TUN1999").build();
        Mockito.when(ambulanceRepository.findById(1L)).thenReturn(Optional.ofNullable(ambulance));
        Mockito.when(ambulanceRepository.save(Mockito.any(Ambulance.class))).thenReturn(ambulance);

        Patient patient = Patient.builder().idpatient(1).nom("Mohamed Amine ").x(56.78).y(9.0).prenom("Brahmi").typatient(TypePatient.URGENT).build();
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.ofNullable(patient));
        Mockito.when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);

        Patient patientToTest = servicePatient.AssignPatientToAmbulance(1, 1L);

        log.info("état du Patient  "+ patientToTest.getTypatient());
        log.info("Id du Ambulance Initial " + ambulance.getIdAmb());
        log.info("Id  Ambulance Patient  " + patientToTest.getAmbulance().getIdAmb());

        Assertions.assertEquals(TypePatient.URGENT, patientToTest.getTypatient());
        Assertions.assertEquals(ambulance.getIdAmb(), patientToTest.getAmbulance().getIdAmb());
        Assertions.assertTrue(patientToTest.getAmbulance().getBusy());

        Mockito.verify(ambulanceRepository).save(Mockito.any(Ambulance.class));
        Mockito.verify(patientRepository).save(Mockito.any(Patient.class));


        Etablissement etablissement = Etablissement.builder().idEtab(1L).nomEtab("Clinique Al Majed").nbLits(5).build();
        Mockito.when(etablissementRepository.findById(1L)).thenReturn(Optional.ofNullable(etablissement));
        Mockito.when(etablissementRepository.save(Mockito.any(Etablissement.class))).thenReturn(etablissement);


        Medecin medecin = Medecin.builder().idMedecin(1).nom("Ok").prenom("CC").build();
        Mockito.when(medecinRepository.findById(1)).thenReturn(Optional.ofNullable(medecin));
        Mockito.when(medecinRepository.save(Mockito.any(Medecin.class))).thenReturn(medecin);

        Infermier infermier = Infermier.builder().idInfermier(1).nom("Infermier").prenom("YYYYYY").build();
        Mockito.when(infrimerRepository.findById(1)).thenReturn(Optional.ofNullable(infermier));
        Mockito.when(infrimerRepository.save(Mockito.any(Infermier.class))).thenReturn(infermier);



        Patient updatedPatient = servicePatient.UnassignPatientFromAmbulanceAndAffectToEtab(1,1L,1,1);



        Assertions.assertEquals(etablissement.getIdEtab(), updatedPatient.getEtablissement().getIdEtab());
        Assertions.assertEquals(updatedPatient.getMedecin().getIdMedecin(), medecin.getIdMedecin());
        Assertions.assertEquals(updatedPatient.getInfermier().getIdInfermier(), infermier.getIdInfermier());
        Assertions.assertEquals(4, etablissementRepository.findById(etablissement.getIdEtab()).get().getNbLits());

        log.info("nbr Lits "+etablissementRepository.findById(etablissement.getIdEtab()).get().getNbLits());

        log.info("ID Med Init "+ medecin.getIdMedecin());
        log.info("ID Med Affecté  "+ updatedPatient.getMedecin().getIdMedecin());

        log.info("ID Inf Init "+ infermier.getIdInfermier());
        log.info("ID Inf Affecté  "+ updatedPatient.getInfermier().getIdInfermier());

        log.info("ID Etab Init "+ etablissement.getIdEtab());
        log.info("ID Etab Affecté  "+ updatedPatient.getEtablissement().getIdEtab());

        Mockito.verify(etablissementRepository).save(Mockito.any(Etablissement.class));
        Mockito.verify(medecinRepository).save(Mockito.any(Medecin.class));
        Mockito.verify(infrimerRepository).save(Mockito.any(Infermier.class));


    }

}
