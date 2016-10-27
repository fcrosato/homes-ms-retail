package com.tenx.ms.retail.product.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.BaseTestGenerator;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class ProductControllerTest extends BaseTestGenerator {
    @Value("classpath:store/success/create.json")
    private File createStoreSuccess;

    @Value("classpath:product/success/create1.json")
    private File createProductSuccess1;
    @Value("classpath:product/success/create2.json")
    private File createProductSuccess2;

    @Value("classpath:product/fail/create-max-sku.json")
    private File createProductSKUTooLong;
    @Value("classpath:product/fail/create-min-sku.json")
    private File createProductSKUTooShort;
    @Value("classpath:product/fail/create-no-name.json")
    private File createProductNoName;
    @Value("classpath:product/fail/create-no-price.json")
    private File createProductNoPrice;
    @Value("classpath:product/fail/create-no-sku.json")
    private File createProductNoSKU;

    @Test
    @FlywayTest
    public void testCreateAndGetProductSuccess() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long productId = createProduct(storeId, createProductSuccess1, HttpStatus.OK);
        Product product = getProduct(storeId, productId, HttpStatus.OK);
        assertNotNull("Store cannot be null", product);
        assertEquals("Store ids mismatch", product.getStoreId(), storeId);
        assertEquals("Store ids mismatch", product.getProductId(), productId);
    }

    @Test
    @FlywayTest
    public void testGetAllProductsSuccess() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long product1 = createProduct(storeId, createProductSuccess1, HttpStatus.OK);
        Long product2 = createProduct(storeId, createProductSuccess2, HttpStatus.OK);
        String url = String.format(REQUEST_URI_PRODUCT, getBasePath()) + storeId;
        List<Product> products = sendRequest(url, (String) null,
                HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Product>>() {
                });
        assertNotNull("Product list shouldn't be null", products);
        assertEquals("Product count does not match", products.size(), 2);
        assertEquals("Product Ids do not match", products.get(0).getProductId(), product1);
        assertEquals("Store Ids do not match", products.get(0).getStoreId(), storeId);
        assertEquals("Product Ids do not match", products.get(1).getProductId(), product2);
        assertEquals("Store Ids do not match", products.get(1).getStoreId(), storeId);
    }

    @Test
    @FlywayTest
    public void testSKUOutOfRange() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long product1 = createProduct(storeId, createProductSKUTooLong, HttpStatus.PRECONDITION_FAILED);
        Long product2 = createProduct(storeId, createProductSKUTooShort, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testNoName() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long product1 = createProduct(storeId, createProductNoName, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testNoPrice() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long product1 = createProduct(storeId, createProductNoPrice, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testNoSKU() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long product1 = createProduct(storeId, createProductNoSKU, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testGetProductByStoreIdNotFound() {
        Long invalidStoreId = 99999L;
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Long productId = createProduct(storeId, createProductSuccess1, HttpStatus.OK);
        Product product = getProduct(invalidStoreId, productId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testGetProductByProductIdNotFound() {
        Long invalidId = 99999L;
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Product product = getProduct(storeId, invalidId, HttpStatus.NOT_FOUND);
    }
}
