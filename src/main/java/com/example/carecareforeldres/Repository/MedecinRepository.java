package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin,Integer> {

    Optional<?> findMedecinByUser(Integer user);
    Medecin findBySpecialite(Specialite specialite);
    //@Query("SELECT (m) FROM Medecin m join m.rdvs r WHERE m.idMedecin = :idMedecin AND r.dateRDV BETWEEN :startDate AND :endDate ORDER BY count (m.rdvs)DESC")
    //List<Medecin> findTop3MedecinBenevole(@Param("idMedecin") Integer idMedecin, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT m FROM Medecin m JOIN m.rdvs r WHERE r.dateRDV BETWEEN :startDate AND :endDate GROUP BY m ORDER BY count (r) DESC")
    List<Medecin> findTop3MedecinBenevole(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}

