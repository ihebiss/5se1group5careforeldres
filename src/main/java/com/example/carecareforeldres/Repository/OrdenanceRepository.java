package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Medicament;
import com.example.carecareforeldres.Entity.Ordonnance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdenanceRepository extends JpaRepository<Ordonnance,Integer> {

    @Transactional
    @Query(value =   "SELECT * " + "FROM ordonnance" +
            " WHERE patient= :patient" , nativeQuery = true)
    List<Ordonnance> getOrdonnancesByPatient(@Param("patient") Integer patient);



    @Query("SELECT MIN(r.dateRDV) FROM Rdv r WHERE r.medecin.idMedecin = :medecinId AND r.dateRDV > CURRENT_TIMESTAMP")
    LocalDateTime findProchainRendezVousDuMedecin(@Param("medecinId") Integer medecinId);


    @Query("SELECT o.medicaments, COUNT(o.medicaments) " +
            "FROM Ordonnance o " +
            "WHERE (:dateDebut IS NULL OR o.dateAjout >= :dateDebut) " +
            "AND (:dateFin IS NULL OR o.dateAjout <= :dateFin) " +
            "GROUP BY o.medicaments " +
            "ORDER BY COUNT(o.medicaments) DESC")
    List<Medicament> findMostPrescribedMedications(LocalDate dateDebut, LocalDate dateFin);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ordenance_medicament WHERE ord_id = :ord_id", nativeQuery = true)
    void deleteOrdonnanceMedicament(@Param("ord_id") Integer ord_id);

}
