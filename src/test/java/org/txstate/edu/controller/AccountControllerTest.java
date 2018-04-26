package org.txstate.edu.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.txstate.edu.config.TxbankApplication;
import org.txstate.edu.model.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TxbankApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    MultiValueMap<String, String> requestHeaders = null;

    @Before
    public void setUp() throws Exception {
        String requestBody = "{\n" +
                "\t\"username\": \"jyoti\",\n" +
                "\t\"password\": \"password\"\n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody);
        HttpEntity<String> response = restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);

        String resultString = response.getBody();
        HttpHeaders headers = response.getHeaders();
        requestHeaders = new LinkedMultiValueMap<>();
        requestHeaders.put("Authorization", headers.get("Authorization"));
        requestHeaders.add("content-type","application/json");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAccountSummary() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);

        ResponseEntity<AccountSummary> responseEntity =
                restTemplate.exchange("/accountSummary/jyoti", HttpMethod.GET, entity, AccountSummary.class);
        AccountSummary client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Jyoti Gupta", client.getAccountName());
    }

    @Test
    public void getAccounts() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);

        ResponseEntity<List<Accounts>> responseEntity =
                restTemplate.exchange("/accounts", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Accounts>>() {
                });
        List<Accounts> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() == 2);
    }

    @Test
    public void getAccountsBeneficiary() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<BeneficiaryAccount>> responseEntity =
                restTemplate.exchange("/accounts/beneficiary", HttpMethod.GET, entity, new ParameterizedTypeReference<List<BeneficiaryAccount>>() {
                });
        List<BeneficiaryAccount> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() > 0);
    }

    @Test
    public void addAccountsBeneficiary() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<BeneficiaryAccount>> responseEntity =
                restTemplate.exchange("/accounts/beneficiary", HttpMethod.GET, entity, new ParameterizedTypeReference<List<BeneficiaryAccount>>() {
                });
        List<BeneficiaryAccount> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() > 0);
    }

    @Test
    public void doTransaction() {
        String reqBody = "{\"fromAccount\":1,\"toAccount\":3,\"amount\":\"10\",\"message\":\"Transfer\",\"createdBy\":\"jyoti\"}";
        HttpEntity<String> entity = new HttpEntity<>(reqBody, requestHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/transaction", HttpMethod.POST, entity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getTransaction() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<Transaction>> responseEntity =
                restTemplate.exchange("/transaction", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transaction>>() {
                });
        List<Transaction> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() > 0);

    }

    @Test
    public void getTransactionByAccountNo() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<Transaction>> responseEntity =
                restTemplate.exchange("/transaction/1", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transaction>>() {
                });
        List<Transaction> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() > 0);
    }

    @Test
    public void getNotificationPolicy() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<NotificationPolicy> responseEntity =
                restTemplate.exchange("/notification", HttpMethod.GET, entity, NotificationPolicy.class);
        NotificationPolicy client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client != null);
    }

    @Test
    public void updateNotificationPolicy() {
        String reqBody = "{\"id\":1,\"enable\":true,\"creditAmount\":50.0,\"debitAmount\":50.0,\"profileUpdate\":true,\"passwordUpdate\":true,\"username\":\"jyoti\"}";
        HttpEntity<String> entity = new HttpEntity<>(reqBody, requestHeaders);
        ResponseEntity<Void> responseEntity =
                restTemplate.exchange("/notification", HttpMethod.PUT, entity, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}