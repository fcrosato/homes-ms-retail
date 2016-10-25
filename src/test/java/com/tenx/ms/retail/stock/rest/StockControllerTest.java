package com.tenx.ms.retail.stock.rest;

import com.netflix.ribbon.proxy.annotation.Http;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.BaseTestGenerator;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.service.StockService;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.tenx.ms.commons.config.Profiles;
import com.fasterxml.jackson.core.type.TypeReference;
import org.flywaydb.test.annotation.FlywayTest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.io.File;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StockControllerTest extends BaseTestGenerator {
    private static Long storeId;
    private static Long productId;

    @Autowired
    private StockService stockService;

    @Value("classpath:store/success/create.json")
    private File createStoreSuccess;
    @Value("classpath:product/success/create2.json")
    private File createProductSuccess;

    @Value("classpath:stock/success/create1.json")
    private File createStockSuccess1;
    @Value("classpath:stock/success/create2.json")
    private File createStockSuccess2;

    @Value("classpath:stock/fail/create-no-count.json")
    private File createStockNoCount;

    @Before
    @FlywayTest
    public void init() {
        storeId = createStore(createStoreSuccess, HttpStatus.OK);
        productId = createProduct(storeId, createProductSuccess, HttpStatus.OK);
    }

    @Test
    @FlywayTest
    public void testCreateStock() {
        createStock(storeId, productId, createStockSuccess1, HttpStatus.OK);
        Stock stock = this.stockService.findByProductIdAndStoreId(productId, storeId).get();
        assertNotNull("Stock cannot be null", stock);
        assertEquals("Store ids mismatch", stock.getStoreId(), storeId);
        assertEquals("Products ids mismatch", stock.getProductId(), productId);
    }

    @Test
    @FlywayTest
    public void testUpdateStock() {
        createStock(storeId, productId, createStockSuccess1, HttpStatus.OK);
        createStock(storeId, productId, createStockSuccess2, HttpStatus.OK);
        Stock stock = this.stockService.findByProductIdAndStoreId(productId, storeId).get();
        assertNotNull("Stock cannot be null", stock);
        assertEquals("Store ids mismatch", stock.getStoreId(), storeId);
        assertEquals("Products ids mismatch", stock.getProductId(), productId);
        assertEquals("Product count was not updated", stock.getCount(), 200L);
    }

    @Test
    @FlywayTest
    public void testCreateNoCount() {
        createStock(storeId, productId, createStockNoCount, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidStore() {
        Long invalidStoreId = 99999L;
        createStock(invalidStoreId, productId, createStockNoCount, HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidProduct() {
        Long invalidProductId = 99999L;
        createStock(storeId, invalidProductId, createStockNoCount, HttpStatus.PRECONDITION_FAILED);
    }
}
