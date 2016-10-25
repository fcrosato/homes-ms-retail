package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("Order")
public class Order {
    @ApiModelProperty(value = "Order Id", required = true)
    private Long orderId;
    @ApiModelProperty(value = "Store Id", required = true)
    private Long storeId;
    @ApiModelProperty(value = "Order Date", required = true)
    @DateTimeFormat
    @NotNull
    private Date orderDate;
    @ApiModelProperty(value = "Order Status", required = true)
    @NotNull
    private OrderStatusEnum status;
    @ApiModelProperty(value = "Order Products", required = true)
    private List<OrderProduct> products;
    @ApiModelProperty(value = "Customer First Name", required = true)
    @Pattern(regexp = "^[A-Za-z]*$")
    @NotNull
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]*$")
    @NotNull
    @ApiModelProperty(value = "Customer Last Name", required = true)
    private String lastName;
    @ApiModelProperty(value = "Customer Email", required = true)
    @Email
    @NotNull
    private String email;
    @ApiModelProperty(value = "Customer Phone", required = true)
    @PhoneNumber
    @NotNull
    private String phone;

}
