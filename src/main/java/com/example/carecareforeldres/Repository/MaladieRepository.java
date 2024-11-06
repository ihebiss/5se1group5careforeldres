package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Maladie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaladieRepository extends JpaRepository<Maladie,Integer> {
    Optional<Maladie> findByNom(String nomMaladie);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM maladie_patients WHERE maladies_id = :maladies_id", nativeQuery = true)
    void deleteMaladie(@Param("maladies_id") Integer maladies_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ingredient_maladies WHERE maladies_id = :maladies_id", nativeQuery = true)
    void deleteMaladiee(@Param("maladies_id") Integer maladies_id);
}
