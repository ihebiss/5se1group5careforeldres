package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Repository.MedecinRepository;
import com.example.carecareforeldres.Service.IServiceMedecin;
import com.example.carecareforeldres.Service.IServicePatient;
import com.example.carecareforeldres.Service.IserviceAlert;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest

public class AlertTestservice {

    @Autowired
    IServicePatient servicePatient;
    @Autowired
    IServiceMedecin serviceMedecin;
    @Autowired
    IserviceAlert serviceAlert;
    @Test
    public void testGetMedecinsDistance() throws IOException {

        Patient patient = Patient.builder()
                .nom("John")
                .user(46)
                .prenom("Doe")
                .mail("john.doe@example.com")
                .x(36.523763459387425)
                .y(10.278808493167164)
                .adresse("123 Main St")
                .build();
         servicePatient.addPatient(patient);


        Medecin medecin1 = Medecin.builder()


                .user(47)
                .nom("Alice")
                .prenom("Smith")
                .mail("alice@example.com")
                .x(36.081070109369485)
                .y(9.329589977860453)
                .adresse("456 Elm St")
                .build();
         serviceMedecin.addMedecin(medecin1);

        Medecin medecin2 = Medecin.builder()

                .user(48)
                .nom("Bob")
                .prenom("Johnson")
                .mail("bob@example.com")
                .x(34.57171577593229)
                .y(9.25488241016865)
                .adresse("789 Oak St")
                .build();
        serviceMedecin.addMedecin(medecin2);

        Medecin medecin3 = Medecin.builder()

                .user(49)
                .nom("Carol")
                .prenom("Williams")
                .mail("carol@example.com")
                .x(34.936382699910176)
                .y(9.940429553389551)
                .adresse("321 Pine St")
                .build();
        serviceMedecin.addMedecin(medecin3);

        Medecin medecin4 = Medecin.builder()

                .user(50)
                .nom("David")
                .prenom("Brown")
                .mail("david@example.com")
                .x(36.54494944148322)
                .y(10.55126912891865)
                .adresse("654 Cedar St")
                .build();
        serviceMedecin.addMedecin(medecin4);


        Map<Integer, Float> result = serviceAlert.getMedecinsDistance(patient);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.containsKey(4));
        Assertions.assertTrue(result.containsKey(2));
        Assertions.assertTrue(result.containsKey(1));

        Assertions.assertEquals(1291.145751953125f, result.get(4), 0.01);
        Assertions.assertEquals(1360.78955078125f, result.get(2), 0.01);
        Assertions.assertEquals(1360.78955078125f, result.get(1), 0.01);
        serviceMedecin.removeMedecin(medecin1.getIdMedecin());
        serviceMedecin.removeMedecin(medecin2.getIdMedecin());
        serviceMedecin.removeMedecin(medecin3.getIdMedecin());
        serviceMedecin.removeMedecin(medecin4.getIdMedecin());
        servicePatient.removePatient(patient.getIdpatient());
    }

}
