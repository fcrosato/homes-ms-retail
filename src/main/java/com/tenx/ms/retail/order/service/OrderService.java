package com.tenx.ms.retail.order.service;

import com.tenx.ms.commons.util.converter.EntityConverter;
import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderProduct;
import com.tenx.ms.retail.order.rest.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class OrderService {

    private static final EntityConverter<Order, OrderEntity> CONVERTER = new EntityConverter<>(Order.class, OrderEntity.class);
    private static final EntityConverter<OrderProduct, OrderProductEntity> ORDER_PRODUCT_CONVERTER =
            new EntityConverter<>(OrderProduct.class, OrderProductEntity.class);

    @Autowired
    private OrderRepository repository;


    @Transactional
    public OrderResponse create(Order order) {
        OrderEntity orderEntity = CONVERTER.toT2(order);
        orderEntity.setProducts(new ArrayList<>());
        order.getProducts().stream().forEach(x -> orderEntity.getProducts().add(ORDER_PRODUCT_CONVERTER.toT2(x)));
        orderEntity.getProducts().stream().forEach(x -> x.setOrder(orderEntity));
        this.repository.save(orderEntity);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderEntity.getOrderId());
        orderResponse.setStatus(orderEntity.getStatus());
        orderResponse.setProducts(order.getProducts());

        return orderResponse;
    }
}