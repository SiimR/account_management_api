package com.example.domain.service;

import com.example.domain.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountUpdateServiceTest {

    private AccountUpdateService updateService;
    private Account existingAccount;

    @BeforeEach
    void setUp() {
        updateService = new AccountUpdateService();
        existingAccount = new Account(UUID.randomUUID(), "existing name", "123456789");
    }

    @Test
    void givenUpdateWithNewNameAndPhone_whenUpdateCalled_thenUpdateBothFields() {
        Account update = new Account(null, "update name", "987654321");

        Account result = updateService.updateAccount(existingAccount, update);

        assertEquals(update.getName(), result.getName());
        assertEquals(update.getPhoneNr(), result.getPhoneNr());
    }
}