package com.weboutin.sbs;

import com.weboutin.sbs.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void create() throws Exception {
        String account = "test";
        String password = "test";
        userService.register(account, password);
    }
}