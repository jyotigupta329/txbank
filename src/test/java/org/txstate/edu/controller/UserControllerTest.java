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
import org.txstate.edu.model.UserForm;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jyoti on 4/25/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TxbankApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    MultiValueMap<String, String> userRequestHeaders = null;
    MultiValueMap<String, String> adminRequestHeaders = null;

    @Before
    public void setUp() throws Exception {
        userRequestHeaders = login("jyoti");

    }

    private MultiValueMap<String, String> login(String username) {
        String requestBody = "{\n" +
                "\t\"username\": \"" + username + "\",\n" +
                "\t\"password\": \"password\"\n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody);
        HttpEntity<String> response = restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);

        HttpHeaders headers = response.getHeaders();
        MultiValueMap<String, String>  userRequestHeaders = new LinkedMultiValueMap<>();
        userRequestHeaders.put("Authorization", headers.get("Authorization"));
        userRequestHeaders.add("content-type", "application/json");
        return userRequestHeaders;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void signUp() throws Exception {


    }

    @Test
    public void getAllUsers() throws Exception {
        adminRequestHeaders = login("admin");
        HttpEntity<String> entity = new HttpEntity<>(null, adminRequestHeaders);

        ResponseEntity<List<UserForm>> responseEntity =
                restTemplate.exchange("/users", HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserForm>>() {
                });
        List<UserForm> client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(client.size() > 0);
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