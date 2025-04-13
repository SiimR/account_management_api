package com.example.domain.service;

import com.example.domain.entity.Account;
import com.example.domain.exception.AccountValidationException;
import org.springframework.stereotype.Component;

@Component
public class AccountValidatorService {

    private static final String PHONE_NR_REGEX = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{1,8}$";

    public void validate(Account account) {
        if (account == null) {
            throw new AccountValidationException("account is null");
        }
        if (account.getName() == null || account.getName().isBlank()) {
            throw new AccountValidationException("account name is blank");
        }
        if (account.getPhoneNr() != null && !account.getPhoneNr().matches(PHONE_NR_REGEX)) {
            throw new AccountValidationException("given account phone nr does not match regex");
        }
    }
}
