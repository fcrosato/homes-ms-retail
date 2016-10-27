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
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(unique = true, name = "sku", length = 10)
    private String sku;

    @Column(name = "price")
    private Double price;
}
