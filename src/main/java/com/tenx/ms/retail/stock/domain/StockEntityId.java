package com.tenx.ms.retail.stock.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockEntityId implements Serializable {
    private Long productId;
    private Long storeId;
}
