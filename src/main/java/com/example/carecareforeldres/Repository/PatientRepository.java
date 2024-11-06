package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Optional<?> findPatientByUser(Integer user);
    @Query("SELECT YEAR(CURRENT_DATE()) - YEAR(p.datedeNais) FROM Patient p WHERE p.idpatient = :patientId")
    Integer calculatePatientAgeById(@Param("patientId") Integer patientId);
    @Query("SELECT p FROM Patient p JOIN p.rdvs r WHERE p.ambulance IS NOT NULL AND r.dateRDV BETWEEN :startDate AND :endDate")
    List<Patient> findPatientAmbulance(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT p FROM Patient p WHERE p.medecin.idMedecin IS NOT NULL AND  p.etablissement.idEtab = :idEtab ")
    List<Patient> findPatientInEtab(@Param("idEtab")Long idEtab);

}
