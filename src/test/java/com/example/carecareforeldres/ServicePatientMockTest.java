package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.*;
import com.example.carecareforeldres.Service.ServicePatient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ServicePatientMockTest {

    @MockBean
    private AmbulanceRepository ambulanceRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private EtablissementRepository etablissementRepository;

    @MockBean
    private MedecinRepository medecinRepository;

    @MockBean
    private InfrimerRepository infrimerRepository;

    @Autowired
    private ServicePatient servicePatient;

    private Ambulance ambulance;
    private Patient patient;
    private Etablissement etablissement;
    private Medecin medecin;
    private Infermier infermier;

    @BeforeEach
    public void setUp() {
        ambulance = Ambulance.builder()
                .idAmb(1L)
                .busy(false)
                .x(16.08)
                .y(22.0)
                .marque("VW")
                .matricule("245TUN1999")
                .build();

        patient = Patient.builder()
                .idpatient(1)
                .nom("Mohamed Amine")
                .x(56.78)
                .y(9.0)
                .prenom("Brahmi")
                .typatient(TypePatient.URGENT)
                .build();

        etablissement = Etablissement.builder()
                .idEtab(1L)
                .nomEtab("Clinique Al Majed")
                .nbLits(5)
                .build();

        medecin = Medecin.builder()
                .idMedecin(1)
                .nom("Ok")
                .prenom("CC")
                .build();

        infermier = Infermier.builder()
                .idInfermier(1)
                .nom("Infermier")
                .prenom("YYYYYY")
                .build();
    }

    @Test
    public void testAssignPatientToAmbulance() {
        Mockito.when(ambulanceRepository.findById(1L)).thenReturn(Optional.of(ambulance));
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        Mockito.when(ambulanceRepository.save(any(Ambulance.class))).thenReturn(ambulance);
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient resultPatient = servicePatient.AssignPatientToAmbulance(1, 1L);

        assertEquals(TypePatient.URGENT, resultPatient.getTypatient());
        assertEquals(ambulance.getIdAmb(), resultPatient.getAmbulance().getIdAmb());
        assertTrue(resultPatient.getAmbulance().getBusy());

        verify(ambulanceRepository).save(any(Ambulance.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    public void testUnassignPatientFromAmbulanceAndAffectToEtab() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        Mockito.when(etablissementRepository.findById(1L)).thenReturn(Optional.of(etablissement));
        Mockito.when(medecinRepository.findById(1)).thenReturn(Optional.of(medecin));
        Mockito.when(infrimerRepository.findById(1)).thenReturn(Optional.of(infermier));
        Mockito.when(etablissementRepository.save(any(Etablissement.class))).thenReturn(etablissement);
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient resultPatient = servicePatient.UnassignPatientFromAmbulanceAndAffectToEtab(1, 1L, 1, 1);

        assertEquals(etablissement.getIdEtab(), resultPatient.getEtablissement().getIdEtab());
        assertEquals(medecin.getIdMedecin(), resultPatient.getMedecin().getIdMedecin());
        assertEquals(infermier.getIdInfermier(), resultPatient.getInfermier().getIdInfermier());
        assertEquals(4, etablissement.getNbLits());

        verify(etablissementRepository).save(any(Etablissement.class));
        verify(patientRepository).save(any(Patient.class));
        verify(medecinRepository).findById(1);
        verify(infrimerRepository).findById(1);
    }
}
