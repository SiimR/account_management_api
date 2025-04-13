package com.example.domain.usecase;

import com.example.domain.entity.Account;
import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.FetchAccountGateway;
import com.example.domain.gateway.SaveAccountGateway;
import com.example.domain.service.AccountUpdateService;
import com.example.domain.service.AccountValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseImplTest {

    @Mock
    private SaveAccountGateway saveAccountGateway;

    @Mock
    private FetchAccountGateway fetchAccountGateway;

    @Mock
    private AccountUpdateService accountUpdateService;

    @Mock
    private AccountValidatorService accountValidatorService;

    @InjectMocks
    private UpdateAccountUseCaseImpl useCase;

    private Account existing;
    private Account update;
    private UpdateAccountUseCase.Request request;

    @BeforeEach
    void setUp() {
        UUID accountId = UUID.randomUUID();
        existing = new Account(accountId, "existing account", null);
        update = new Account(accountId, "update account", null);
        request = new UpdateAccountUseCase.Request(accountId, update);
    }

    @Test
    void givenUuidForExistingAccount_whenUpdateRequestCalled_thenReturnUpdatedAccount() {
        when(fetchAccountGateway.fetchAccountByUuid(request.accountUuid())).thenReturn(Optional.of(existing));
        when(accountUpdateService.updateAccount(existing, update)).thenReturn(update);
        when(saveAccountGateway.saveAccount(update)).thenReturn(update);

        useCase.execute(request);

        verify(fetchAccountGateway).fetchAccountByUuid(request.accountUuid());
        verify(accountUpdateService).updateAccount(existing, update);
        verify(accountValidatorService).validate(update);
        verify(saveAccountGateway).saveAccount(update);
    }

    @Test
    void givenUuidForNonExistingAccount_whenUpdateRequestCalled_thenThrowAccountNotFoundException() {
        when(fetchAccountGateway.fetchAccountByUuid(request.accountUuid())).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> useCase.execute(request)
        );

        String expectedMessage = String.format("Account update request. Account with ID %s not found.", request.accountUuid());
        assertEquals(expectedMessage, exception.getMessage());
        verify(fetchAccountGateway).fetchAccountByUuid(request.accountUuid());
        verify(accountUpdateService, never()).updateAccount(any(), any());
        verify(accountValidatorService, never()).validate(any());
        verify(saveAccountGateway, never()).saveAccount(any());
    }
}