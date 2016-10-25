package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.service.StockService;

@Api(value = "stock", description = "stock API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockService service;


    @ApiOperation(value = "Creates/updates a new stock.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock successfully created."),
            @ApiResponse(code = 412, message = "Unable to create stock. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{productId:\\d+}/{storeId:\\d+}"}, method = RequestMethod.POST)
    public void createOrUpdate (
            @ApiParam(name = "productId", value = "The stock to be created.") @PathVariable Long productId,
            @ApiParam(name = "storeId", value = "The stock to be created.") @PathVariable Long storeId,
            @ApiParam(name = "stock", value = "The stock to be created.") @RequestBody @Validated Stock stock) {
        LOGGER.info("Creating stock {}", stock);
        stock.setStoreId(storeId);
        stock.setProductId(productId);
        service.create(stock);
        int a = 1;
    }
}