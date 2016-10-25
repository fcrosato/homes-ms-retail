package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.BaseIntegrationTest;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;

public class BaseTestGenerator extends BaseIntegrationTest {
    protected static final String API_VERSION = RestConstants.VERSION_ONE;
    protected static final String REQUEST_URI_STORE = "%s" + API_VERSION + "/stores/";
    protected static final String REQUEST_URI_PRODUCT = "%s" + API_VERSION + "/product/";
    protected static final String REQUEST_URI_STOCK = "%s" + API_VERSION + "/stock/";
    protected static final String REQUEST_URI_ORDER = "%s" + API_VERSION + "/order/";

    protected Long createStore(File data, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_STORE, getBasePath());
        ResourceCreated<Long> response = sendRequest(
                baseUrl, data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {
                });

        return response != null ? response.getId() : null;
    }

    protected Store getStore(long storeId, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_STORE, getBasePath());
        return sendRequest(baseUrl + storeId, (String) null, HttpMethod.GET,
                expectedStatus, new TypeReference<Store>() {});
    }

    protected Long createProduct(long storeId, File data, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_PRODUCT, getBasePath());
        ResourceCreated<Long> response = sendRequest(
                baseUrl + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {
                });

        return response != null ? response.getId() : null;
    }

    protected Product getProduct(long storeId, long productId, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_PRODUCT, getBasePath());
        return sendRequest(baseUrl + productId + "/" + storeId, (String) null, HttpMethod.GET,
                expectedStatus, new TypeReference<Product>() {});
    }

    protected void createStock(long storeId, long productId, File data, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_STOCK, getBasePath());
        sendRequest( baseUrl + productId + "/" + storeId, data, HttpMethod.POST, expectedStatus, null);
    }

    protected Order createOrder(long storeId, File data, HttpStatus expectedStatus) {
        String baseUrl = String.format(REQUEST_URI_ORDER, getBasePath());
        Order response = sendRequest(
                baseUrl + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<Order>() {
                });

        return response != null ? response : null;
    }
}
