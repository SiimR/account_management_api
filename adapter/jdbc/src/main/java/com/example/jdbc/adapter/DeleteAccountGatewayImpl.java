package com.example.jdbc.adapter;

import com.example.domain.entity.Account;
import com.example.domain.gateway.DeleteAccountGateway;
import com.example.jdbc.dbo.AccountDbo;
import com.example.jdbc.mapper.AccountDboMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class DeleteAccountGatewayImpl implements DeleteAccountGateway {
    private final JpaRepository<AccountDbo, UUID> accountRepository;
    private final AccountDboMapper accountDboMapper;

    @Override
    public void delete(Account account) {
        var dboToDelete = accountDboMapper.toAccountDbo(account);
        accountRepository.delete(dboToDelete);
    }
}
