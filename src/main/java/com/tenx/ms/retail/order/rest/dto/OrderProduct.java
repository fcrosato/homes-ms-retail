package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Order Product")
public class OrderProduct {
    @ApiModelProperty(value = "Order Id", required = true)
    private Long orderId;
    @ApiModelProperty(value = "Product Id", required = true)
    private Long productId;
    @ApiModelProperty(value = "Order Date", required = true)
    @NotNull
    private Long count;
}