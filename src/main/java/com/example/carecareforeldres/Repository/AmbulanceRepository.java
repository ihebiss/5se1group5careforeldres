package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmbulanceRepository extends JpaRepository<Ambulance,Long> {
    @Query("SELECT a FROM Ambulance a WHERE a.busy = false")
    List<Ambulance> findAmbulance();
}
