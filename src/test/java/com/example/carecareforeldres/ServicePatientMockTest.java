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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    @Autowired
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

        log.info("état du Patient  " + patientToTest.getTypatient());
        log.info("Id du Ambulance Initial " + ambulance.getIdAmb());
        log.info("Id  Ambulance Patient  " + patientToTest.getAmbulance().getIdAmb());

        Assertions.assertEquals(TypePatient.URGENT, patientToTest.getTypatient());
        Assertions.assertEquals(ambulance.getIdAmb(), patientToTest.getAmbulance().getIdAmb());
        Assertions.assertTrue(patientToTest.getAmbulance().getBusy());

        Mockito.verify(ambulanceRepository).save(Mockito.any(Ambulance.class));
        Mockito.verify(patientRepository).save(Mockito.any(Patient.class));


    }
}
