package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.BaseTestGenerator;
import com.tenx.ms.retail.order.rest.dto.Order;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class OrderControllerTest extends BaseTestGenerator {
    private static Long storeId;
    private static List<Long> productIds;

    @Value("classpath:store/success/create.json")
    private File createStoreSuccess;
    @Value("classpath:product/success/create1.json")
    private File createProductSuccess1;
    @Value("classpath:product/success/create2.json")
    private File createProductSuccess2;

    @Value("classpath:order/success/create.json")
    private File createOrderSuccess;

    @Value("classpath:order/fail/create-invalid-date.json")
    private File createOrderInvalidDate;
    @Value("classpath:order/fail/create-invalid-email.json")
    private File createOrderInvalidEmail;
    @Value("classpath:order/fail/create-invalid-first-name.json")
    private File createOrderInvalidFirstName;
    @Value("classpath:order/fail/create-invalid-last-name.json")
    private File createOrderInvalidLastName;
    @Value("classpath:order/fail/create-invalid-phone.json")
    private File createOrderInvalidPhone;
    @Value("classpath:order/fail/create-invalid-status.json")
    private File createOrderInvalidStatus;
    @Value("classpath:order/fail/create-no-date.json")
    private File createOrderNoDate;
    @Value("classpath:order/fail/create-no-email.json")
    private File createOrderNoEmail;
    @Value("classpath:order/fail/create-no-first-name.json")
    private File createOrderNoFirstName;
    @Value("classpath:order/fail/create-no-last-name.json")
    private File createOrderNoLastName;
    @Value("classpath:order/fail/create-no-phone.json")
    private File createOrderNoPhone;
    @Value("classpath:order/fail/create-no-status.json")
    private File createOrderNoStatus;

    @Before
    @FlywayTest
    public void init() {
        productIds = new ArrayList<Long>();
        storeId = createStore(createStoreSuccess, HttpStatus.OK);
        productIds.add(createProduct(storeId, createProductSuccess1, HttpStatus.OK));
        productIds.add(createProduct(storeId, createProductSuccess2, HttpStatus.OK));
    }

    @Test
    @FlywayTest
    public void testCreateOrderSuccess() {
        Order order = createOrder(storeId, createOrderSuccess, HttpStatus.OK);
        assertEquals("Store ids mismatch", storeId, order.getStoreId());
        assertEquals("Product order count mismatch", productIds.size(), order.getProducts().size());
        assertEquals("Product count mismatch", order.getProducts().get(0).getCount(), 50L);
        assertEquals("Product count mismatch", order.getProducts().get(1).getCount(), 30L);
        order.getProducts().stream().forEach(x -> assertTrue("Unable to find one of the created products",
                productIds.contains(x.getProductId())));
    }

    @Test
    @FlywayTest
    public void testBadRequestFailures() {
        String url = String.format(REQUEST_URI_ORDER, getBasePath()) + storeId;
        try {
            List<File> fileList = Arrays.asList(createOrderInvalidStatus);
            for (File file : fileList) {
                String payload = FileUtils.readFileToString(file);
                ResponseEntity<String> response = getStringResponse(url, payload, HttpMethod.POST);
                assertEquals("HTTP Status code incorrect", HttpStatus.BAD_REQUEST, response.getStatusCode());
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testPreconditionFailures() {
        String url = String.format(REQUEST_URI_ORDER, getBasePath()) + storeId;
        try {
            List<File> fileList = Arrays.asList(createOrderNoDate, createOrderNoEmail, createOrderNoFirstName,
                    createOrderNoLastName, createOrderNoPhone, createOrderNoStatus, createOrderInvalidEmail,
                    createOrderInvalidFirstName, createOrderInvalidLastName, createOrderInvalidPhone,
                    createOrderInvalidEmail);
            for (File file : fileList) {
                String payload = FileUtils.readFileToString(file);
                ResponseEntity<String> response = getStringResponse(url, payload, HttpMethod.POST);
                assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}