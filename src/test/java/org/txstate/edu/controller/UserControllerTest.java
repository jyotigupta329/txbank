package org.txstate.edu.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.txstate.edu.config.TxbankApplication;

/**
 * Created by jyoti on 4/25/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TxbankApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

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
    public void signUp() throws Exception {


    }

    @Test
    public void getAllUsers() throws Exception {
    }

    @Test
    public void addUser() throws Exception {
    }

    @Test
    public void updateUserProfile() throws Exception {
    }

    @Test
    public void getUsersProfile() throws Exception {
    }

    @Test
    public void updateUserPassword() throws Exception {
    }

    @Test
    public void activateAccount() throws Exception {
    }

    @Test
    public void suspendAccount() throws Exception {
    }

}