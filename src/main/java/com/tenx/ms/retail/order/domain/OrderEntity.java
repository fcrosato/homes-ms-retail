package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.enums.OrderStatusEnum;
import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    private Long storeId;
    private Date orderDate;
    private OrderStatusEnum status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> products;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}