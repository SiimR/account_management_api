package com.example.domain.gateway;

import com.example.domain.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface FetchAccountGateway {
    Optional<Account> fetchAccountByUuid(UUID uuid);
}
