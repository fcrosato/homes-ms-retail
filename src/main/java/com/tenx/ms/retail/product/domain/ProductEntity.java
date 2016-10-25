package com.tenx.ms.retail.product.domain;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product")
@Access(AccessType.FIELD)
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long productId;
    private Long storeId;
    private String name;
    private String description;
    @Column(unique = true)
    private String sku;
    private Double price;
}
