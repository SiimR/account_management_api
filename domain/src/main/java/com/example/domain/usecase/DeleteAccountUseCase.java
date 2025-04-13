package com.example.domain.usecase;

import java.util.UUID;

public interface DeleteAccountUseCase {
    void execute(Request request);
    record Request(UUID accountUuid){}
}
