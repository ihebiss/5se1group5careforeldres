package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meeting,Long> {



}