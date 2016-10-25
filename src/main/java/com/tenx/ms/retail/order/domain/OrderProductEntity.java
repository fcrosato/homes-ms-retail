package com.tenx.ms.retail.order.domain;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_product")
@Access(AccessType.FIELD)
public class OrderProductEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id", updatable = false)
    private OrderEntity order;
    private Long productId;
    private Long count;
}
