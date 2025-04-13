package com.example.domain.service;

import com.example.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdateService {
    public Account updateAccount(Account existing, Account update) {
        existing.setPhoneNr(update.getPhoneNr());
        existing.setName(update.getName());
        return existing;
    }
}
