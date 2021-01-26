package com.example.demo.testhelpers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

import java.math.BigDecimal;

public class Helpers {

    public static User createValidUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("gollum");
        user.setPassword("myprecious");

        user.setCart(createValidCart());
        return user;
    }

    public static Cart createValidCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.addItem(createValidItem());
        return cart;
    }

    public static Item createValidItem() {
        Item item = new Item();
        item.setId(1L);
        item.setDescription("Trinket");
        item.setPrice(BigDecimal.valueOf(13.01));
        return item;
    }

    public static UserOrder createValidOrder() {
        return UserOrder.createFromCart(createValidCart());
    }
}
