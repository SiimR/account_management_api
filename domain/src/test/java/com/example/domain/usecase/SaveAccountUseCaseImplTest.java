package com.example.domain.usecase;

import com.example.domain.entity.Account;
import com.example.domain.gateway.SaveAccountGateway;
import com.example.domain.service.AccountValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveAccountUseCaseImplTest {

    @Mock
    private SaveAccountGateway saveAccountGateway;

    @Mock
    private AccountValidatorService validatorService;

    @InjectMocks
    private SaveAccountUseCaseImpl useCase;

    private Account accountToSave;
    private Account savedAccount;
    private SaveAccountUseCase.Request request;

    @BeforeEach
    void setUp() {
        accountToSave = new Account(null, "name", null);
        savedAccount = new Account(UUID.randomUUID(), "name", null);
        request = new SaveAccountUseCase.Request(accountToSave);
    }

    @Test
    void givenValidAccount_whenSaveRequestCalled_thenReturnSavedAccountId() {
        when(saveAccountGateway.saveAccount(accountToSave)).thenReturn(savedAccount);

        useCase.execute(request);

        verify(saveAccountGateway).saveAccount(accountToSave);
    }
}