package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.ReponseReclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReponseReclamationRepository extends JpaRepository<ReponseReclamation,Long> {
    ReponseReclamation findByReclamationIdReclamation (Long idReclamation);
}
