package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "orders", description = "Orders API")
@RestController("ordersControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/order")
public class OrderController {

    @Autowired
    private OrderService service;


    @ApiOperation(value = "Creates a new order.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully created."),
            @ApiResponse(code = 412, message = "Unable to create order. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    public Order create(
            @ApiParam(name = "storeId",   value = "The store id.") @PathVariable() long  storeId,
            @ApiParam(name = "order", value = "The order.") @RequestBody @Validated Order order) {
        log.info("Creating order {}", order);
        order.setStoreId(storeId);
        return service.create(order);
    }

}