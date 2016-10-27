package com.tenx.ms.retail.enums;

import com.tenx.ms.commons.rest.IntValueEnum;

import java.util.Arrays;
import java.util.List;


public enum OrderStatusEnum implements IntValueEnum<OrderStatusEnum> {
    ORDERED(0),
    PACKING(1),
    SHIPPED(2);

    private static final List<String> STATUS_NAMES = Arrays.asList("Ordered", "Packing", "Shipped");
    private final int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public static OrderStatusEnum mapNameToEnum(String name) {
        if (STATUS_NAMES.contains(name)) {
            return OrderStatusEnum.fromValue(STATUS_NAMES.indexOf(name));
        }
        throw new IllegalArgumentException("Order status name not valid");
    }

    public static String mapEnumToName(OrderStatusEnum status) {
        int statusValue = status.getValue();

        if (statusValue < 0 || statusValue > STATUS_NAMES.size()) {
            throw new IndexOutOfBoundsException("Invalid order status " + statusValue);
        }

        return STATUS_NAMES.get(statusValue);
    }

    public static OrderStatusEnum fromValue(int value) {
        if (value < 0 || value > STATUS_NAMES.size()) {
            throw new IndexOutOfBoundsException("Invalid order status " + value);
        }
        return OrderStatusEnum.values()[value];
    }

    public static int toValue(OrderStatusEnum status) {
        return status.getValue();
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return mapEnumToName(this);
    }
}
