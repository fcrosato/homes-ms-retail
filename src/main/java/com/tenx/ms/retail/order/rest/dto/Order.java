package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import com.tenx.ms.retail.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("Order")
public class Order {
    @ApiModelProperty(value = "Order Id", required = true, readOnly = true)
    private Long orderId;

    @ApiModelProperty(value = "Store Id", required = true, readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Order Date", required = true, readOnly = true)
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
    @Length(max = 50)
    @NotNull
    private String firstName;

    @ApiModelProperty(value = "Customer Last Name", required = true)
    @Pattern(regexp = "^[A-Za-z]*$")
    @Length(max = 50)
    @NotNull
    private String lastName;

    @ApiModelProperty(value = "Customer Email", required = true)
    @Length(max = 50)
    @Email
    @NotNull
    private String email;

    @ApiModelProperty(value = "Customer Phone", required = true)
    @PhoneNumber
    @NotNull
    private String phone;

}
