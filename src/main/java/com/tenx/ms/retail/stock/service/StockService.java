package com.tenx.ms.retail.stock.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StockService {

    private static final EntityConverter<Stock, StockEntity> STOCK_CONVERTER =
            new EntityConverter<>(Stock.class, StockEntity.class);
    private static final EntityConverter<Product, ProductEntity> PRODUCT_CONVERTER =
            new EntityConverter<>(Product.class, ProductEntity.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void createOrUpdate(Stock stock, Long storeId, Long productId) {
        Optional<ProductEntity> product = productRepository.findByProductIdAndStoreId(productId, storeId);
        if (product.isPresent()) {
            Optional<StockEntity> existingStockEntity = stockRepository.findOneByProduct(product.get());
            if (existingStockEntity.isPresent()) {
                existingStockEntity.get().setCount(stock.getCount());
                stockRepository.save(existingStockEntity.get());
            } else {
                StockEntity stockEntity = STOCK_CONVERTER.toT2(stock);
                stockEntity.setProduct(product.get());
                stockRepository.save(stockEntity);
            }
        } else {
            throw new NoSuchElementException(String.format("There is no product %d at store %d", productId, storeId));
        }
    }

    @Transactional
    public Optional<Stock> findByProduct(ProductEntity product) {
        Optional<StockEntity> stockEntity = stockRepository.findOneByProduct(product);
        if (stockEntity.isPresent()) {
            Stock stock = STOCK_CONVERTER.toT1(stockEntity.get());
            stock.setStoreId(product.getStoreId());
            stock.setProductId(product.getStoreId());
            return Optional.of(stock);
        }
        throw new NoSuchElementException(String.format("There is no stock for the product %d at store %d",
                product.getProductId(), product.getStoreId()));
    }
}
