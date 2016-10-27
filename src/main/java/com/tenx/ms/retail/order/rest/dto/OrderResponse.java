package com.tenx.ms.retail.order.rest.dto;


import com.tenx.ms.retail.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.avro.reflect.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("Order Response")
public class OrderResponse {
    @NotNull
    @ApiModelProperty(value = "Order Id", required = true)
    private Long orderId;

    @NotNull
    @ApiModelProperty(value = "Order Status", readOnly = true)
    private OrderStatusEnum status;

    @Nullable
    @ApiModelProperty(value = "Products", required = true)
    private List<OrderProduct> products;
}
