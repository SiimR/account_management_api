package com.example.domain.usecase;

import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.DeleteAccountGateway;
import com.example.domain.gateway.FetchAccountGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DeleteAccountUseCaseImpl implements DeleteAccountUseCase {
    private static final String ACCOUNT_NOT_FOUND = "Account delete request. Account with ID %s not found.";

    private final FetchAccountGateway fetchAccountGateway;
    private final DeleteAccountGateway deleteAccountGateway;

    @Override
    public void execute(Request request) {
        var accountToDelete = fetchAccountGateway.fetchAccountByUuid(request.accountUuid())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(request.accountUuid())));
        deleteAccountGateway.delete(accountToDelete);
    }
}
