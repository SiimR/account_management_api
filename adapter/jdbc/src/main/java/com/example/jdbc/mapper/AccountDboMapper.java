package com.example.jdbc.mapper;

import com.example.domain.entity.Account;
import com.example.jdbc.dbo.AccountDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDboMapper {
    @Mapping(target = "target.createdAt", ignore = true)
    @Mapping(target = "target.modifiedAt", ignore = true)
    AccountDbo toAccountDbo(Account account);

    Account toAccount(AccountDbo dbo);
}
