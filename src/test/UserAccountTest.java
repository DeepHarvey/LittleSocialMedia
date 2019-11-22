/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package test;

import main.model.AbstractAccount;
import main.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAccountTest {

    AbstractAccount abstractAccount;

    @BeforeEach
    void runBefore() {
        abstractAccount = new UserAccount("test", "hhh", "123");
    }

    @Test
    void testConstructor() {
        assertEquals("test", abstractAccount.getUsername());
        assertEquals("hhh", abstractAccount.getNickname());
        assertEquals("123", abstractAccount.getPassword());
    }

    @Test
    void testSetPassword() {
        abstractAccount.setPassword("12345");
        assertEquals("12345", abstractAccount.getPassword());
    }

    @Test
    void testSetNickname() {
        abstractAccount.setNickname("fish");
        assertEquals("fish", abstractAccount.getNickname());
    }
}
