package com.example.domain.usecase;

import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.FetchAccountGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FetchAccountUseCaseImpl implements FetchAccountUseCase {
    private static final String ACCOUNT_NOT_FOUND = "Account fetch request. Account with ID %s not found.";

    private final FetchAccountGateway fetchAccountGateway;

    @Override
    public Response execute(Request request) {
        var account = fetchAccountGateway
                .fetchAccountByUuid(request.accountId())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(request.accountId())));
        return new FetchAccountUseCase.Response(account);
    }
}
