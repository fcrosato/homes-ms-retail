package com.tenx.ms.retail.stock.domain;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@IdClass(StockEntityId.class)
@Table(name = "stock")
@Access(AccessType.FIELD)
public class StockEntity {
    @Id
    private Long productId;
    @Id
    private Long storeId;
    private Long count;
}
