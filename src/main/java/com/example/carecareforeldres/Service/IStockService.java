package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Stock;

import java.util.List;

public interface IStockService {
    Stock addStock(Stock stock);
    Stock retrieveStock(Long id);
    List<Stock> retrieveAllStock();
}
