package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RdvRepository extends JpaRepository<Rdv,Long> {

    @Query("SELECT (r) FROM Rdv r join r.medecin m WHERE m.idMedecin = :idMedecin  ORDER BY r.dateRDV DESC")
    List<Rdv> findLastByMedecin(@Param("idMedecin") Integer idMedecin);

    @Query("SELECT (r) FROM Rdv r join r.patient p WHERE p.idpatient = :idpatient  ORDER BY r.dateRDV DESC")
    List<Rdv> findRdvByPatient(@Param("idpatient") Integer idpatient);



    @Query("SELECT (r) FROM Rdv r join r.medecin p WHERE p.idMedecin = :idpatient  ORDER BY r.dateRDV DESC")
    List<Rdv> findByMedecinidMedecin(@Param("idpatient") Integer idpatient);
}
