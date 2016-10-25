package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "stores", description = "Stores API")
@RestController("storesControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreService service;


    @ApiOperation(value = "Creates a new store.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order successfully created."),
            @ApiResponse(code = 412, message = "Unable to create store. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @ApiParam(name = "store", value = "The Store", required = true) @RequestBody @Validated Store store) {
        LOGGER.info("Creating store {}", store);
        return new ResourceCreated<>(this.service.create(store));
    }

    @ApiOperation(value = "Gets all stores.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all stores."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> getAll() {
        LOGGER.info("Getting all stores");
        return this.service.getAll();
    }

    @ApiOperation(value = "Gets the store matching the given id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved store."),
            @ApiResponse(code = 404, message = "Order not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Store getById(@ApiParam(name = "storeId", value = "The store id.") @PathVariable long storeId) {
        LOGGER.info("Getting com.tenx.ms.retail.order by id {}", storeId);
        return this.service.getByStoreId(storeId).get();
    }
}