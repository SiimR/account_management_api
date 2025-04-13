package com.example.domain.usecase;

import com.example.domain.entity.Account;

import java.util.UUID;

public interface UpdateAccountUseCase {
    Response execute(Request request);

    record Request(UUID accountUuid, Account account) {}

    record Response(Account account) {}
}
