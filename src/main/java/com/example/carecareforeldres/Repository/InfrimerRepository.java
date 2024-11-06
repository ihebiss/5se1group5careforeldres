package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InfrimerRepository extends JpaRepository<Infermier,Integer> {
    Optional<?> findInfermierByUser(Integer user);
    @Query("SELECT p FROM Patient p JOIN p.infermier i  WHERE i.idInfermier=:idinf AND i.etablissement.idEtab=p.etablissement.idEtab AND p.infermier.idInfermier=i.idInfermier")
    List<Patient> findPatientEtabEqualInfEtab(@Param("idinf")Integer idInfermier);

}
