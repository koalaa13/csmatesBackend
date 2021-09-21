package com.csmates.wp.validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {
    private static EmailValidator validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailValidator();
    }

    @Test
    void simpleValidEmailTest() {
        String validEmail = "darkness@mail.ru";
        assertTrue(validator.test(validEmail));
    }

    @Test
    void emailWithoutAtSignTest() {
        String invalidEmail = "darkness";
        assertFalse(validator.test(invalidEmail));
    }

    @Test
    void differentEmails() {
        List<String> validEmails = new ArrayList<>();
        validEmails.add("user@domain.com");
        validEmails.add("user@domain.co.in");
        validEmails.add("user.name@domain.com");
        validEmails.add("user_name@domain.com");
        validEmails.add("username@yahoo.corporate.in");

        for (String email : validEmails) {
            assertTrue(validator.test(email));
        }

        //Invalid emails
        List<String> invalidEmails = new ArrayList<>();
        invalidEmails.add(".username@yahoo.com");
        invalidEmails.add("username@yahoo.com.");
        invalidEmails.add("username@yahoo..com");
        invalidEmails.add("username@yahoo.c");
        invalidEmails.add("username@yahoo.corporate");

        for (String email : invalidEmails) {
            assertFalse(validator.test(email));
        }
    }
}
