package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    @Before
    public void setUp() throws Exception {
        when(itemRepository.findByName(any())).thenReturn(null);
    }

    @Test
    public void getItemsByInvalidName() {
        ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName("somename");
        Assert.assertEquals(HttpStatus.NOT_FOUND, itemsByName.getStatusCode());
    }
}