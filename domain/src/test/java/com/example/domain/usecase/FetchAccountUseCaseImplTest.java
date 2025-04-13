package com.example.domain.usecase;

import com.example.domain.entity.Account;
import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.FetchAccountGateway;
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
class FetchAccountUseCaseImplTest {

    @Mock
    private FetchAccountGateway fetchAccountGateway;

    @InjectMocks
    private FetchAccountUseCaseImpl useCase;

    private UUID accountId;
    private Account account;
    private FetchAccountUseCase.Request request;

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        account = new Account(accountId, "test account", null);
        request = new FetchAccountUseCase.Request(accountId);
    }

    @Test
    void givenUuidForExistingAccount_whenFetchRequestCalled_thenReturnAccount() {
        when(fetchAccountGateway.fetchAccountByUuid(accountId)).thenReturn(Optional.of(account));

        useCase.execute(request);

        verify(fetchAccountGateway).fetchAccountByUuid(accountId);
    }

    @Test
    void givenUuidForNonExistingAccount_whenFetchRequestCalled_thenThrowAccountNotFoundException() {
        when(fetchAccountGateway.fetchAccountByUuid(accountId)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> useCase.execute(request)
        );

        String expectedMessage = String.format("Account fetch request. Account with ID %s not found.", accountId);
        assertEquals(expectedMessage, exception.getMessage());
        verify(fetchAccountGateway).fetchAccountByUuid(accountId);
    }
}