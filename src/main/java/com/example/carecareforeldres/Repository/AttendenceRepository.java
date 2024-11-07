package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Attendence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendenceRepository extends JpaRepository<Attendence,Long> {
}
