package com.example.domain.usecase;

import com.example.domain.entity.Account;

import java.util.UUID;

public interface FetchAccountUseCase {
    Response execute(Request request);

    record Request(UUID accountId) {}

    record Response(Account account) {}
}
