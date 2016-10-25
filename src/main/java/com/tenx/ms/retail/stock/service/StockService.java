package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.commons.util.converter.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;

import java.util.Optional;

@Service
public class StockService {

    private static final EntityConverter<Stock, StockEntity> CONVERTER = new EntityConverter<>(Stock.class, StockEntity.class);

    @Autowired
    private StockRepository repository;


    @Transactional
    public void create(Stock stock)
    {
        repository.save(CONVERTER.toT2(stock));
    }

    public Optional<Stock> findByProductIdAndStoreId(Long productId, Long storeId)
    {
        return repository.findByProductIdAndStoreId(productId, storeId).map(CONVERTER::toT1);
    }
}
