package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Service.AlertService;
import com.example.carecareforeldres.Service.IServiceMedecin;
import com.example.carecareforeldres.Service.IServicePatient;
import com.example.carecareforeldres.Service.IserviceAlert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class AlertServiceTestMockito {
    @Mock
    IServicePatient servicePatient;

    @Mock
    IServiceMedecin serviceMedecin;

    @Mock
    IserviceAlert serviceAlert;

    @Test
    public void testGetMedecinsDistance() throws IOException {
        Patient patient = Patient.builder()
                .nom("iheb")
                .idpatient(55)
                .user(46)
                .prenom("issaui")
                .mail("iheb.iss@gmail.com")
                .x(36.523763459387425)
                .y(10.278808493167164)
                .adresse("123 Main St")
                .build();

        lenient().when(servicePatient.addPatient(Mockito.any(Patient.class))).thenReturn(patient);


        Medecin medecin1 = Medecin.builder()
                .user(60)
                .idMedecin(56)
                .nom("brhmi")
                .prenom("brhmi")
                .mail("alice@example.com")
                .x(36.081070109369485)
                .y(9.329589977860453)
                .adresse("456 Elm St")
                .build();

        Medecin medecin2 = Medecin.builder()
                .user(61)
                .idMedecin(57)

                .nom("Bob")
                .prenom("slim")
                .mail("issam@example.com")
                .x(34.57171577593229)
                .y(9.25488241016865)
                .adresse("789 Oak St")
                .build();

        Medecin medecin3 = Medecin.builder()
                .user(62)
                .idMedecin(58)

                .nom("hhhh")
                .prenom("hhh")
                .mail("hhhh@example.com")
                .x(34.936382699910176)
                .y(9.940429553389551)
                .adresse("321 Pine St")
                .build();

        Medecin medecin4 = Medecin.builder()
                .user(63)
                .idMedecin(59)

                .nom("David")
                .prenom("Brown")
                .mail("david@example.com")
                .x(36.54494944148322)
                .y(10.55126912891865)
                .adresse("654 Cedar St")
                .build();

        lenient().when(serviceMedecin.addMedecin(Mockito.any(Medecin.class))).thenReturn(medecin1)
                .thenReturn(medecin2)
                .thenReturn(medecin3)
                .thenReturn(medecin4);

        Map<Integer, Float> distances = new HashMap<>();
        distances.put(1, 1360.78955078125f);
        distances.put(2, 1291.145751953125f);
        distances.put(3, 1360.78955078125f);
        Mockito.when(serviceAlert.getMedecinsDistance(patient)).thenReturn(distances);
         log.info(distances.toString());
        Map<Integer, Float> result = serviceAlert.getMedecinsDistance(patient);



        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.containsKey(1));
        Assertions.assertTrue(result.containsKey(2));
        Assertions.assertTrue(result.containsKey(3));

        Assertions.assertEquals(1291.145751953125f, result.get(2), 0.01);
        Assertions. assertEquals(1360.78955078125f, result.get(1), 0.01);
        Assertions. assertEquals(1360.78955078125f, result.get(3), 0.01);
        verify(serviceAlert).getMedecinsDistance(Mockito.any(Patient.class));
        serviceMedecin.removeMedecin(medecin1.getIdMedecin());
        serviceMedecin.removeMedecin(medecin2.getIdMedecin());
        serviceMedecin.removeMedecin(medecin3.getIdMedecin());
        serviceMedecin.removeMedecin(medecin4.getIdMedecin());
        servicePatient.removePatient(patient.getIdpatient());

    }
}
