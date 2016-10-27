package com.tenx.ms.retail.product.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final EntityConverter<Product, ProductEntity> CONVERTER = new EntityConverter<>(Product.class, ProductEntity.class);

    @Autowired
    private ProductRepository repository;


    @Transactional
    public Long create(Product product) {
        return repository.save(CONVERTER.toT2(product)).getProductId();
    }

    public Optional<Product> getByProductIdAndStoreId(long productId, long storeId) {
        return repository.findByProductIdAndStoreId(productId, storeId).map(CONVERTER::toT1);
    }

    public Optional<ProductEntity> getEntityByProductIdAndStoreId(long productId, long storeId) {
        return repository.findByProductIdAndStoreId(productId, storeId);
    }

    public List<Product> getAllByStoreId(long storeId) {
        return repository.findAllByStoreId(storeId).stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }
}
