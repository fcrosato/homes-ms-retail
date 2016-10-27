package com.tenx.ms.retail.product.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel("product model")
public class Product {
    @ApiModelProperty(value = "The Id of the product", readOnly = true, required = true)
    private Long productId;

    @ApiModelProperty(value = "The Id of the Order associated with this product", readOnly = true, required = true)
    @NotNull
    private Long storeId;

    @ApiModelProperty(value = "The name of the product", required = true)
    @Length(max = 50)
    @NotNull
    private String name;

    @ApiModelProperty(value = "The description of the product")
    @Length(max = 500)
    private String description;

    @ApiModelProperty(value = "The SKU of the product", required = true)
    @NotNull
    @Size(min = 5, max = 10)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String sku;

    @ApiModelProperty(value = "The price of the product", required = true)
    @NotNull
    @Min(0)
    private Double price;
}
