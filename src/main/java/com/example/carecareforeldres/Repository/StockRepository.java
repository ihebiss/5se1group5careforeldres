package com.example.carecareforeldres.Repository;

import com.example.carecareforeldres.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
