package com.example.demo;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SareetaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createUser() {
		HttpEntity<CreateUserRequest> request = new HttpEntity<>(createUserRequest(), new HttpHeaders());
		ResponseEntity<User> response = testRestTemplate.postForEntity("http://localhost:" + port + "/api/user/create", request, User.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertNull(response.getHeaders().get("Authorization"));

		ObjectNode loginRequest = JsonNodeFactory.instance.objectNode();
		loginRequest.put("username", "testuser");
		loginRequest.put("password", "testpassword");
		ResponseEntity<String> loginResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/login", loginRequest, String.class);
		String jwt = loginResponse.getHeaders().get("Authorization").get(0);
		Assert.assertNotNull(jwt);

		HttpHeaders authheaders = new HttpHeaders();
		authheaders.add("Authorization", jwt);
		ResponseEntity<String> orderHistoryResponse = testRestTemplate.exchange("http://localhost:" + port + "/api/order/history/testuser", HttpMethod.GET, new HttpEntity<>(authheaders), String.class);
		Assert.assertEquals(HttpStatus.OK, orderHistoryResponse.getStatusCode());
	}

	private CreateUserRequest createUserRequest() {
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername("testuser");
		createUserRequest.setPassword("testpassword");
		createUserRequest.setConfirmPassword("testpassword");
		return createUserRequest;
	}


}
