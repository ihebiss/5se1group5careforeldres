package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Message;
import com.example.carecareforeldres.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {


    List<Message> findByMedecinAndPatient(Medecin medecin, Patient patient);

    List<Message> findByPatientAndMedecin(Patient patient, Medecin medecin);
}
