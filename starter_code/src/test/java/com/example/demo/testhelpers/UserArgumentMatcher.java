package com.example.demo.testhelpers;

import com.example.demo.model.persistence.User;
import org.mockito.ArgumentMatcher;

public class UserArgumentMatcher implements ArgumentMatcher<User> {
    @Override
    public boolean matches(User user) {
       return true;
    }
}
