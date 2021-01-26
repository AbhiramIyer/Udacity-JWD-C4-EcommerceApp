package com.example.demo.controllers;

import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.testhelpers.Helpers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderController oc;

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUsername(eq("gollum"))).thenReturn(createValidUser());
        when(orderRepository.save(any())).thenReturn(createValidOrder());
        when(orderRepository.findByUser(any())).thenReturn(List.of(createValidOrder()));
    }

    @Test
    public void submit() {
        ResponseEntity<UserOrder> userOrder = oc.submit("gollum");
        Assert.assertEquals(HttpStatus.OK, userOrder.getStatusCode());
    }

    @Test
    public void submitInvalidUser() {
        ResponseEntity<UserOrder> userOrder = oc.submit("frodo");
        Assert.assertEquals(HttpStatus.NOT_FOUND, userOrder.getStatusCode());
    }

    @Test
    public void getOrdersForUser() {
        ResponseEntity<List<UserOrder>> ordersForUser = oc.getOrdersForUser("gollum");
        Assert.assertEquals(HttpStatus.OK, ordersForUser.getStatusCode());
    }

    @Test
    public void getOrdersForInvalidUser() {
        ResponseEntity<List<UserOrder>> ordersForUser = oc.getOrdersForUser("frodo");
        Assert.assertEquals(HttpStatus.NOT_FOUND, ordersForUser.getStatusCode());
    }
}