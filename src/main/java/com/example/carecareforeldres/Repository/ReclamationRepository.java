package com.example.carecareforeldres.Repository;


import com.example.carecareforeldres.Entity.Reclamation;
import com.example.carecareforeldres.Entity.TypeReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findByUserId(Integer id);


    int countByDateCreation(LocalDate date);
    @Query("SELECT r FROM Reclamation r JOIN FETCH r.reponseReclamation WHERE r.user.id = ?1 AND r.reponseReclamation IS NOT NULL")
    List<Reclamation> findByUserAndResponseReclamationIsNotNull(UUID userId);

    List<Reclamation> findByTypeReclamation(TypeReclamation type);
}
