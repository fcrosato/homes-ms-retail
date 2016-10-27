package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("Stock model")
public class Stock {
    @ApiModelProperty(value = "The Id of the product in the stock", required = true)
    private Long productId;

    @ApiModelProperty(value = "The Id of the Order associated with this stock", required = true)
    private Long storeId;

    @ApiModelProperty(value = "The total count in stock", required = true)
    @Min(value = 0)
    @NotNull
    private Long count;
}
