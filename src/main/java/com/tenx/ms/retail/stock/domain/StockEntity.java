package com.tenx.ms.retail.stock.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "stock")
@Access(AccessType.FIELD)
public class StockEntity {
    @Id
    @GeneratedValue
    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @OneToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private ProductEntity product;

    @NotNull
    @Column(name = "count", nullable = false)
    private Long count;
}
