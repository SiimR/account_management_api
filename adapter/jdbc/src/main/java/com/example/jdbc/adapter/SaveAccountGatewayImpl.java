package com.example.jdbc.adapter;

import com.example.domain.entity.Account;
import com.example.domain.gateway.SaveAccountGateway;
import com.example.jdbc.dbo.AccountDbo;
import com.example.jdbc.mapper.AccountDboMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class SaveAccountGatewayImpl implements SaveAccountGateway {
    private final JpaRepository<AccountDbo, UUID> accountRepository;
    private final AccountDboMapper accountDboMapper;

    @Override
    public Account saveAccount(Account account) {
        var dboToSave = accountDboMapper.toAccountDbo(account);
        var savedDbo = accountRepository.save(dboToSave);
        return accountDboMapper.toAccount(savedDbo);
    }
}
