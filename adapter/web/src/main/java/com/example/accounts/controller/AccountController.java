package com.example.accounts.controller;

import com.example.accounts.dto.AccountDto;
import com.example.accounts.mapper.AccountDtoMapper;
import com.example.domain.usecase.DeleteAccountUseCase;
import com.example.domain.usecase.FetchAccountUseCase;
import com.example.domain.usecase.SaveAccountUseCase;
import com.example.domain.usecase.UpdateAccountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {
    private final FetchAccountUseCase fetchAccountUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final AccountDtoMapper accountDtoMapper;

    @Operation(summary = "Fetch account by ID")
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> fetchAccount(@PathVariable("accountId") UUID accountId) {
        var response = fetchAccountUseCase.execute(new FetchAccountUseCase.Request(accountId));
        var accountDto = accountDtoMapper.toAccountDto(response.account());
        return ResponseEntity.ok(accountDto);
    }

    @Operation(summary = "Create account")
    @PostMapping
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDto) {
        var account = accountDtoMapper.toAccount(accountDto);
        var response = saveAccountUseCase.execute(new SaveAccountUseCase.Request(account));
        var savedAccountDto = accountDtoMapper.toAccountDto(response.savedAccount());
        return ResponseEntity.ok(savedAccountDto);
    }

    @Operation(summary = "Update account by account UUID and request body")
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable UUID accountId, @RequestBody AccountDto accountDto) {
        var account = accountDtoMapper.toAccount(accountDto);
        var response = updateAccountUseCase.execute(new UpdateAccountUseCase.Request(accountId, account));
        var accountDtoResponse = accountDtoMapper.toAccountDto(response.account());
        return ResponseEntity.ok(accountDtoResponse);
    }

    @Operation(summary = "Delete account by UUID")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId") UUID accountId) {
        deleteAccountUseCase.execute(new DeleteAccountUseCase.Request(accountId));
        return ResponseEntity.ok().build();
    }
}
