package com.example.domain.service;

import com.example.domain.entity.Account;
import com.example.domain.exception.AccountValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorServiceTest {

    private static final String A_VALID_PHONE_NR = "55443322";
    private static final String A_VALID_NAME = "Mari Mets";

    private AccountValidatorService validatorService;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        validatorService = new AccountValidatorService();
        accountId = UUID.randomUUID();
    }

    @Test
    void givenNullAccount_whenValidate_thenThrowAccountValidationException() {
        AccountValidationException exception = assertThrows(
                AccountValidationException.class,
                () -> validatorService.validate(null)
        );

        assertEquals("account is null", exception.getMessage());
    }

    @Test
    void givenAccountWithNullName_whenValidate_thenThrowAccountValidationException() {
        Account accountWithNullName = new Account(accountId, null, A_VALID_PHONE_NR);

        AccountValidationException exception = assertThrows(
                AccountValidationException.class,
                () -> validatorService.validate(accountWithNullName)
        );

        assertEquals("account name is blank", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "    "
    })
    void givenAccountWithInvalidName_whenValidate_thenThrowAccountValidationException(String invalidName) {
        Account accountWithEmptyName = new Account(accountId, invalidName, A_VALID_PHONE_NR);

        AccountValidationException exception = assertThrows(
                AccountValidationException.class,
                () -> validatorService.validate(accountWithEmptyName)
        );

        assertEquals("account name is blank", exception.getMessage());
    }

    @Test
    void givenAccountWithNullPhoneNr_whenValidate_thenNoExceptionThrown() {
        Account accountWithNullPhoneNr = new Account(accountId, A_VALID_NAME, null);

        assertDoesNotThrow(() -> validatorService.validate(accountWithNullPhoneNr));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            A_VALID_PHONE_NR,
            "123456789",
            "+372 55443322",
            "123.456.7890",
            "123 456 7890",
            "+1234567890",
            "(123)4567890"
    })
    void givenAccountWithValidPhoneNr_whenValidate_thenNoExceptionThrown(String phoneNr) {
        Account accountWithValidPhoneNr = new Account(accountId, "Valid Name", phoneNr);

        assertDoesNotThrow(() -> validatorService.validate(accountWithValidPhoneNr));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1",
            "12",
            "a",
            "abcdefgh",
            "abc-456-7890",
            "      ",
            "",
            "123456",
            "+372 55443322a",
            "+37255443322a",
            "++37255443322a",
    })
    void givenAccountWithInvalidPhoneNr_whenValidate_thenThrowAccountValidationException(String phoneNr) {
        Account accountWithInvalidPhoneNr = new Account(accountId, "Valid Name", phoneNr);

        AccountValidationException exception = assertThrows(
                AccountValidationException.class,
                () -> validatorService.validate(accountWithInvalidPhoneNr)
        );

        assertEquals("given account phone nr does not match regex", exception.getMessage());
    }
}