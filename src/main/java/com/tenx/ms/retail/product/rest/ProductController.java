package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
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

import java.util.List;

@Slf4j
@Api(value = "product", description = "product API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/product")
public class ProductController {
    @Autowired
    private ProductService service;


    @ApiOperation(value = "Creates a new product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully created."),
            @ApiResponse(code = 412, message = "Unable to create product. Validation errors"),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @ApiParam(name = "storeId", value = "The store id", required = true) @PathVariable long storeId,
            @ApiParam(name = "product", value = "The product to be created.") @RequestBody @Validated Product product) {
        log.info("Creating product {}", product);
        product.setStoreId(storeId);
        return new ResourceCreated<>(this.service.create(product));
    }

    @ApiOperation(value = "Gets all products from a store.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all products of the store."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public List<Product> getAll(@ApiParam(name = "storeId", value = "The store id.") @PathVariable long storeId) {
        log.info("Getting all products");
        return this.service.getAllByStoreId(storeId);
    }

    @ApiOperation(value = "Gets the product matching the given id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the product."),
            @ApiResponse(code = 404, message = "Product not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @RequestMapping(value = {"/{productId:\\d+}/{storeId:\\d+}"}, method = RequestMethod.GET)
    public Product getById(@ApiParam(name = "productId", value = "The product id.") @PathVariable long productId,
                           @ApiParam(name = "storeId", value = "The product id.") @PathVariable long storeId) {
        log.info("Getting product by id {} and by product {}", productId, storeId);
        return this.service.getByProductIdAndStoreId(productId, storeId).get();
    }

}
