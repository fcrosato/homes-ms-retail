package com.tenx.ms.retail.store.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StoreService {

    private static final EntityConverter<Store, StoreEntity> CONVERTER = new EntityConverter<>(Store.class, StoreEntity.class);

    @Autowired
    private StoreRepository repository;


    @Transactional
    public Long create(Store store) {
        return this.repository.save(CONVERTER.toT2(store)).getStoreId();
    }

    public Optional<Store> getByStoreId(long storeId) {
        return this.repository.findByStoreId(storeId).map(CONVERTER::toT1);
    }

    public List<Store> getAll() {
        return this.repository.findAll().stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }
}