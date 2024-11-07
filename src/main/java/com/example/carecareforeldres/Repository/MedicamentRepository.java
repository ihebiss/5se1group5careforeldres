package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Medicament;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicamentRepository extends JpaRepository<Medicament,Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ordenance_medicament WHERE med_id = :med_id", nativeQuery = true)
    void deleteMedicamentOrdonnance(@Param("med_id") Integer med_id);
    @Transactional
    @Query(value =   "SELECT med_id, COUNT(*) AS consommation_count " +
                     "FROM ordenance_medicament" +
                     " GROUP BY med_id" +
                     " ORDER BY consommation_count" +
                     " DESC LIMIT 3", nativeQuery = true)
    List<Integer> selectTop3ConsumedMedicaments();


}

