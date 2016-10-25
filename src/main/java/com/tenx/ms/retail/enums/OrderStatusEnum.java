package com.tenx.ms.retail.enums;

import com.tenx.ms.commons.rest.IntValueEnum;
import io.swagger.annotations.ApiModel;

import java.util.Arrays;
import java.util.stream.Collectors;

@ApiModel(value = "Order Status")
public enum OrderStatusEnum implements IntValueEnum<OrderStatusEnum> {
    ORDERED(0),
    PACKING(1),
    SHIPPED(2);

    private final int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public static OrderStatusEnum fromValue(int value) {
        if (!Arrays.asList(OrderStatusEnum.values()).stream().map(OrderStatusEnum::convert).
                collect(Collectors.toList()).contains(value)) {
            throw new IndexOutOfBoundsException("value " + value + " is out of range");
        }
        return OrderStatusEnum.values()[value];
    }

    public static int convert(OrderStatusEnum status) {
        return status.getValue();
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
