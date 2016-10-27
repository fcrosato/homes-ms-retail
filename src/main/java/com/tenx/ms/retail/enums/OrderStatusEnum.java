package com.tenx.ms.retail.enums;

import com.tenx.ms.commons.rest.IntValueEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public enum OrderStatusEnum implements IntValueEnum<OrderStatusEnum> {
    ORDERED(0),
    PACKING(1),
    SHIPPED(2);

    private static final Map<OrderStatusEnum, String> STATUS_STRING_MAP;

    static {
        Map<OrderStatusEnum, String> enumMap = new HashMap<>();

        enumMap.put(OrderStatusEnum.ORDERED, "Ordered");
        enumMap.put(OrderStatusEnum.PACKING, "Packing");
        enumMap.put(OrderStatusEnum.SHIPPED, "Shipped");

        STATUS_STRING_MAP = enumMap;
    }

    private final int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public static OrderStatusEnum fromString(String name) {
        if (!STATUS_STRING_MAP.containsValue(name)) {
            throw new IllegalArgumentException("name");
        }

        return STATUS_STRING_MAP.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(name))
                .findFirst().get().getKey();
    }

    public static OrderStatusEnum fromValue(int value) {
        if (!Arrays.asList(OrderStatusEnum.values()).stream().map(OrderStatusEnum::convert).collect(Collectors.toList())
                .contains(value)) {
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

    @Override
    public String toString() {
        return STATUS_STRING_MAP.get(this);
    }
}
