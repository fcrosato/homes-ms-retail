package com.tenx.ms.retail.stock.domain;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;

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
    Long count;
}
