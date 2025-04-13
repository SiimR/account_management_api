package com.example.jdbc.adapter;

import com.example.domain.entity.Account;
import com.example.domain.gateway.FetchAccountGateway;
import com.example.jdbc.dbo.AccountDbo;
import com.example.jdbc.mapper.AccountDboMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class FetchAccountGatewayImpl implements FetchAccountGateway {
    private final JpaRepository<AccountDbo, UUID> accountRepository;
    private final AccountDboMapper accountDboMapper;

    @Override
    public Optional<Account> fetchAccountByUuid(UUID uuid) {
        var accountDbo = accountRepository.findById(uuid);
        return accountDbo.map(accountDboMapper::toAccount);
    }
}
