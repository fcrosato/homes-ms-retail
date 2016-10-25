package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Stock model")
public class Stock {
    @ApiModelProperty(value = "The Id of the stock", readOnly = true, required = true)
    private Long productId;

    @ApiModelProperty(value = "The Id of the Order associated with this stock", readOnly = true, required = true)
    private Long storeId;

    @ApiModelProperty(value = "The total count in stock", required = true)
    @NotNull
    private Long count;
}
