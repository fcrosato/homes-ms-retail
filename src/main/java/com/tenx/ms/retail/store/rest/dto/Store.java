package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Store")
public class Store {
    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @NotNull
    @ApiModelProperty(value = "Store Name", required = true)
    private String name;
}
