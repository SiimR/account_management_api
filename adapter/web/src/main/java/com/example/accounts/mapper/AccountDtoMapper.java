package com.example.accounts.mapper;

import com.example.accounts.dto.AccountDto;
import com.example.domain.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDtoMapper {
    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto dto);
}
