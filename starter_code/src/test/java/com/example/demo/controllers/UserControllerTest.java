package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.testhelpers.UserArgumentMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.demo.testhelpers.Helpers.createValidCart;
import static com.example.demo.testhelpers.Helpers.createValidUser;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserController uc ;

    @Test
    public void findById() {
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(createValidUser()));
        ResponseEntity<User> user = uc.findById(1L);
        Assert.assertEquals(HttpStatus.OK, user.getStatusCode());
        Assert.assertEquals(1L, user.getBody().getId());

    }

    @Test
    public void findByUserName() {
        when(userRepository.findByUsername(eq("gollum"))).thenReturn(createValidUser());
        ResponseEntity<User> user = uc.findByUserName("gollum");
        Assert.assertEquals(HttpStatus.OK, user.getStatusCode());
        Assert.assertEquals(1L, user.getBody().getId());
    }

    @Test
    public void createUser() {
        User validUser = createValidUser();
        UserArgumentMatcher userArgumentMatcher = new UserArgumentMatcher();
        when(userRepository.save(argThat(userArgumentMatcher))).thenReturn(validUser);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(validUser.getPassword());
        when(cartRepository.save(any())).thenReturn(createValidCart());

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("gollum");
        request.setPassword("myprecious");
        request.setConfirmPassword("myprecious");

        ResponseEntity<User> user = uc.createUser(request);
        Assert.assertEquals(HttpStatus.OK, user.getStatusCode());

        CreateUserRequest badrequest = new CreateUserRequest();
        badrequest.setUsername("bilbo");
        badrequest.setPassword(null);
        user = uc.createUser(badrequest);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, user.getStatusCode());
    }
}