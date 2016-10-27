package com.tenx.ms.retail.store.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.retail.BaseTestGenerator;
import com.tenx.ms.retail.store.rest.dto.Store;
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
public class StoreControllerTest extends BaseTestGenerator {
    @Value("classpath:store/success/create.json")
    private File createStoreSuccess;

    @Value("classpath:store/fail/create-invalid-key.json")
    private File createStoreInvalidKey;

    @Test
    @FlywayTest
    public void testCreateAndGetStore() {
        Long storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Store store = getStore(storeId, HttpStatus.OK);
        assertNotNull("Store cannot be null", store);
        assertEquals("Store ids mismatch", store.getStoreId(), storeId);
    }

    @Test
    @FlywayTest
    public void testGetAllStores() {
        Long store1 = createStore(createStoreSuccess, HttpStatus.OK);
        Long store2 = createStore(createStoreSuccess, HttpStatus.OK);
        List<Store> stores = sendRequest(String.format(REQUEST_URI_STORE, getBasePath()), (String) null, HttpMethod.GET,
                HttpStatus.OK, new TypeReference<List<Store>>() {
                });
        assertNotNull("Store list shouldn't be null", stores);
        assertEquals("Store count does not match", stores.size(), 2);
        assertEquals("Store Ids do not match", stores.get(0).getStoreId(), store1);
        assertEquals("Store Ids do not match", stores.get(1).getStoreId(), store2);
    }

    @Test
    public void testGetStoreByIdNotFound() {
        Long invalidStoreId = 99999L;
        Store store = getStore(invalidStoreId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testCreateInvalidKey() {
        Long store = createStore(createStoreInvalidKey, HttpStatus.PRECONDITION_FAILED);
    }
}
