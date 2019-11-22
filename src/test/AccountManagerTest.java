/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package test;

import main.model.AccountManager;
import main.model.Exceptions.AccountNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {
    AccountManager accountManager;

    @BeforeEach
    void runBefore() {
        accountManager = AccountManager.getInstance();
    }

//    @Test
//    void testPublishPostWithoutException() {
//        accountManager.load();
//        accountManager.login("hua","hua");
//        try {
//            accountManager.publishMoment("sssss");
//            // test the functionality of the method
//            List<String> posts = accountManager.getMoments();
//            assertTrue(posts.contains("sssss"));
//        } catch (AccountNullException e) {
//            fail("");
//        }
//    }

    @Test
    void testPublishPostWithException() {
        try {
            accountManager.publishMoment("sssss");
            String str = "abc";
            fail("");
        } catch (AccountNullException e) {
            // expected case
        }
    }
}
