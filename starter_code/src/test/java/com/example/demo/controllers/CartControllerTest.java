package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.example.demo.testhelpers.Helpers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private CartController cartController;

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUsername(eq("gollum"))).thenReturn(createValidUser());
        when(itemRepository.findById(eq(1L))).thenReturn(Optional.of(createValidItem()));
        when(cartRepository.save(any())).thenReturn(createValidCart());
    }

    @Test
    public void addTocart() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1L);
        //createValidUser() already creates a cart with 1 item. Adding one more
        cartRequest.setQuantity(1);
        cartRequest.setUsername("gollum");
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(cartRequest);
        Assert.assertEquals(HttpStatus.OK, cartResponseEntity.getStatusCode());
        Assert.assertEquals(2, cartResponseEntity.getBody().getItems().size());
    }

    @Test
    public void removeFromcart() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1L);
        //createValidUser() already creates a cart with 1 item. Removing 1 item.
        cartRequest.setQuantity(1);
        cartRequest.setUsername("gollum");
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(cartRequest);
        Assert.assertEquals(HttpStatus.OK, cartResponseEntity.getStatusCode());
        Assert.assertEquals(0, cartResponseEntity.getBody().getItems().size());
    }
}