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
import org.txstate.edu.model.Users;
import org.txstate.edu.model.UsersProfile;

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

    @After
    public void tearDown() throws Exception {
        this.activateAccount();
    }

    private MultiValueMap<String, String> login(String username) {
        String requestBody = "{\n" +
                "\t\"username\": \"" + username + "\",\n" +
                "\t\"password\": \"password\"\n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody);
        HttpEntity<String> response = restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);

        HttpHeaders headers = response.getHeaders();
        MultiValueMap<String, String> userRequestHeaders = new LinkedMultiValueMap<>();
        userRequestHeaders.put("Authorization", headers.get("Authorization"));
        userRequestHeaders.add("content-type", "application/json");
        return userRequestHeaders;
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
    public void updateUserProfile() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, userRequestHeaders);

        ResponseEntity<UsersProfile> responseEntity =
                restTemplate.exchange("/users/profile/jyoti", HttpMethod.GET, entity, UsersProfile.class);
        UsersProfile client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Jyoti", client.getFirstName());

        client.setAddress1("North Carolina");
        HttpEntity<UsersProfile> updateEntity = new HttpEntity<>(client, userRequestHeaders);

        ResponseEntity<Void> updateResponseEntity =
                restTemplate.exchange("/users/profile/jyoti", HttpMethod.PUT, updateEntity, Void.class);
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
    }

    @Test
    public void getUsersProfile() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, userRequestHeaders);

        ResponseEntity<UsersProfile> responseEntity =
                restTemplate.exchange("/users/profile/jyoti", HttpMethod.GET, entity, UsersProfile.class);
        UsersProfile client = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Jyoti", client.getFirstName());
    }

    @Test
    public void updateUserPassword() throws Exception {

        Users client = new Users();
        client.setPassword("password");
        HttpEntity<Users> updateEntity = new HttpEntity<>(client, userRequestHeaders);

        ResponseEntity<Void> updateResponseEntity =
                restTemplate.exchange("/users/changePassword/jyoti", HttpMethod.PUT, updateEntity, Void.class);
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
    }

    @Test
    public void activateAccount() throws Exception {
        adminRequestHeaders = login("admin");
        HttpEntity<Users> updateEntity = new HttpEntity<>(null, adminRequestHeaders);

        ResponseEntity<Void> updateResponseEntity =
                restTemplate.exchange("/users/activateAccount/jyoti", HttpMethod.POST, updateEntity, Void.class);
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
    }

    @Test
    public void suspendAccount() throws Exception {
        adminRequestHeaders = login("admin");
        HttpEntity<Users> updateEntity = new HttpEntity<>(null, adminRequestHeaders);

        ResponseEntity<Void> updateResponseEntity =
                restTemplate.exchange("/users/suspendAccount/jyoti", HttpMethod.POST, updateEntity, Void.class);
        assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
    }

}