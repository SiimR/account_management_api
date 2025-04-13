package com.example.domain.usecase;

import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.FetchAccountGateway;
import com.example.domain.gateway.SaveAccountGateway;
import com.example.domain.service.AccountUpdateService;
import com.example.domain.service.AccountValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UpdateAccountUseCaseImpl implements UpdateAccountUseCase {
    private static final String ACCOUNT_NOT_FOUND = "Account update request. Account with ID %s not found.";

    private final SaveAccountGateway saveAccountGateway;
    private final FetchAccountGateway fetchAccountGateway;
    private final AccountUpdateService accountUpdateService;
    private final AccountValidatorService accountValidatorService;

    @Override
    public Response execute(Request request) {
        var existingAccount = fetchAccountGateway.fetchAccountByUuid(request.accountUuid())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND.formatted(request.accountUuid())));

        var accountToSave = accountUpdateService.updateAccount(existingAccount, request.account());
        accountValidatorService.validate(accountToSave);

        var updatedAccount = saveAccountGateway.saveAccount(accountToSave);

        return new UpdateAccountUseCase.Response(updatedAccount);
    }
}
