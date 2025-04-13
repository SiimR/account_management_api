package com.example.domain.usecase;

import com.example.domain.entity.Account;

public interface SaveAccountUseCase {
    Response execute(Request request);

    record Request(Account account) {}

    record Response(Account savedAccount) {}
}
