package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Stock;
import com.example.carecareforeldres.Repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockService implements IStockService {

    private  StockRepository stockRepository;

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock retrieveStock(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new NullPointerException("Stock not found"));
    }

    @Override
    public List<Stock> retrieveAllStock() {
        return stockRepository.findAll();
    }

    public void deleteStock(Long id){ stockRepository.deleteById(id);}

}

