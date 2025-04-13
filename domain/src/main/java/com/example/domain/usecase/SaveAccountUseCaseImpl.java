package com.example.domain.usecase;

import com.example.domain.gateway.SaveAccountGateway;
import com.example.domain.service.AccountValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SaveAccountUseCaseImpl implements SaveAccountUseCase {
    private final SaveAccountGateway saveAccountGateway;
    private final AccountValidatorService validatorService;

    @Override
    public Response execute(Request request) {
        validatorService.validate(request.account());
        var savedAccount = saveAccountGateway.saveAccount(request.account());
        return new SaveAccountUseCase.Response(savedAccount);
    }
}
