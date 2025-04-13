package com.example.domain.usecase;

import com.example.domain.entity.Account;
import com.example.domain.exception.AccountNotFoundException;
import com.example.domain.gateway.DeleteAccountGateway;
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
class DeleteAccountUseCaseImplTest {

    @Mock
    private FetchAccountGateway fetchAccountGateway;

    @Mock
    private DeleteAccountGateway deleteAccountGateway;

    @InjectMocks
    private DeleteAccountUseCaseImpl useCase;

    private UUID validAccountUuid;
    private Account mockAccount;
    private DeleteAccountUseCase.Request validRequest;

    @BeforeEach
    void setUp() {
        validAccountUuid = UUID.randomUUID();
        mockAccount = new Account(validAccountUuid, "name", null);
        validRequest = new DeleteAccountUseCase.Request(validAccountUuid);
    }

    @Test
    void givenUuidForExistingAccount_whenDeleteRequestCalled_thenDeleteAccount() {
        when(fetchAccountGateway.fetchAccountByUuid(validAccountUuid)).thenReturn(Optional.of(mockAccount));

        useCase.execute(validRequest);

        verify(fetchAccountGateway).fetchAccountByUuid(validAccountUuid);
        verify(deleteAccountGateway).delete(mockAccount);
    }

    @Test
    void givenUuidForNonExistingAccount_whenDeleteRequestCalled_thenThrowAccountNotFoundException() {
        when(fetchAccountGateway.fetchAccountByUuid(validAccountUuid)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> useCase.execute(validRequest)
        );

        String expectedMessage = String.format("Account delete request. Account with ID %s not found.", validAccountUuid);
        assertEquals(expectedMessage, exception.getMessage());

        verify(fetchAccountGateway).fetchAccountByUuid(validAccountUuid);
        verify(deleteAccountGateway, never()).delete(any(Account.class));
    }
}