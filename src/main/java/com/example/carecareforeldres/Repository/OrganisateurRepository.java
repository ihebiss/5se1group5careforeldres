package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Organisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganisateurRepository extends JpaRepository<Organisateur,Integer> {
    Optional<?> findOrganisateurByUser(Integer user);
}
