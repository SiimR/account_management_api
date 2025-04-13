package com.example.controller;

import com.example.App;
import com.example.accounts.dto.AccountDto;
import com.example.jdbc.dbo.AccountDbo;
import com.example.jdbc.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = App.class)
@AutoConfigureMockMvc
class AccountControllerIntTest {

    private static final String NAME = "Mari";
    private static final String NEW_NAME = "Mia";
    private static final String PHONE_NUMBER = "55443322";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Nested
    class createAccount {
        @Test
        void givenCorrectAccount_whenPostRequest_thenCreateAccount() throws Exception {
            var requestBuilder = post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(prepareJson(NAME, PHONE_NUMBER));

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
            var databaseResult = repository.findAll();

            var accountJson = mvcResult.getResponse().getContentAsString();
            var accountDto = objectMapper.readValue(accountJson, AccountDto.class);

            assertThat(databaseResult).extracting(AccountDbo::getId).containsOnly(accountDto.getId());
            assertThat(databaseResult).extracting(AccountDbo::getName).containsOnly(NAME);
            assertThat(databaseResult).extracting(AccountDbo::getPhoneNr).containsOnly(PHONE_NUMBER);
            assertThat(databaseResult).extracting(AccountDbo::getCreatedAt).isNotEmpty();
            assertThat(databaseResult).extracting(AccountDbo::getModifiedAt).isNotEmpty();
        }

        @Test
        void givenAccountWithNameAndNullPhoneNr_whenPostRequest_thenCreateAccount() throws Exception {
            var requestBuilder = post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(prepareJson(NAME, null));

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
            var databaseResult = repository.findAll();

            var accountJson = mvcResult.getResponse().getContentAsString();
            var accountDto = objectMapper.readValue(accountJson, AccountDto.class);

            assertThat(databaseResult).extracting(AccountDbo::getId).containsOnly(accountDto.getId());
            assertThat(databaseResult).extracting(AccountDbo::getName).containsOnly(NAME);
            assertThat(databaseResult).extracting(AccountDbo::getPhoneNr).containsOnlyNulls();
            assertThat(databaseResult).extracting(AccountDbo::getCreatedAt).isNotEmpty();
            assertThat(databaseResult).extracting(AccountDbo::getModifiedAt).isNotEmpty();
        }

        @Test
        void givenAccountWithNullName_whenPostRequest_thenReceiveUnprocessableEntityStatus() throws Exception {
            var requestBuilder = post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(prepareJson(null, PHONE_NUMBER));

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity()).andReturn();

            assertEquals("account name is blank", mvcResult.getResponse().getContentAsString());
        }

    }

    @Nested
    class fetchAccount {
        @Test
        void givenAccountExists_whenGetRequest_thenReceiveAccount() throws Exception {
            var accountInDb = insertAccount(NAME, PHONE_NUMBER);

            var requestBuilder = get("/accounts/{id}", accountInDb.getId())
                    .contentType(MediaType.APPLICATION_JSON);

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

            var accountJson = mvcResult.getResponse().getContentAsString();
            var accountDto = objectMapper.readValue(accountJson, AccountDto.class);

            assertThat(accountDto).extracting(AccountDto::getId).isEqualTo(accountInDb.getId());
            assertThat(accountDto).extracting(AccountDto::getName).isEqualTo(NAME);
            assertThat(accountDto).extracting(AccountDto::getPhoneNr).isEqualTo(PHONE_NUMBER);
        }

        @Test
        void givenNoAccountInDb_whenGetRequest_thenReceiveNotFoundStatus() throws Exception {
            var uuid = UUID.randomUUID();
            var requestBuilder = get("/accounts/{id}", uuid)
                    .contentType(MediaType.APPLICATION_JSON);

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();

            var expectedMessage = "Account fetch request. Account with ID %s not found.".formatted(uuid);
            assertEquals(expectedMessage, mvcResult.getResponse().getContentAsString());
        }
    }

    @Nested
    class deleteAccount {
        @Test
        void givenAccountExists_whenDeleteRequest_thenAccountIsDeleted() throws Exception {
            var accountInDb = insertAccount(NAME, PHONE_NUMBER);

            var requestBuilder = delete("/accounts/{id}", accountInDb.getId())
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(requestBuilder).andExpect(status().isOk());

            var databaseResult = repository.findAll();

            assertThat(databaseResult).isEmpty();
        }

        @Test
        void givenNonExistingUuidInDb_whenDeleteRequest_thenReceiveError() throws Exception {
            var uuid = UUID.randomUUID();
            var requestBuilder = delete("/accounts/{id}", uuid)
                    .contentType(MediaType.APPLICATION_JSON);

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();

            var expectedMessage = "Account delete request. Account with ID %s not found.".formatted(uuid);
            assertEquals(expectedMessage, mvcResult.getResponse().getContentAsString());
        }
    }

    @Nested
    class updateAccount {
        @Test
        void givenAccountExists_whenUpdateRequest_thenAccountIsUpdated() throws Exception {
            var initialAccountDbo = insertAccount(NAME, PHONE_NUMBER);

            var requestBuilder = put("/accounts/{id}", initialAccountDbo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(prepareJson(NEW_NAME, PHONE_NUMBER));

            mockMvc.perform(requestBuilder).andExpect(status().isOk());

            var updatedAccountInDb = repository.findById(initialAccountDbo.getId()).orElse(null);

            assertThat(updatedAccountInDb).extracting(AccountDbo::getId).isEqualTo(initialAccountDbo.getId());
            assertThat(updatedAccountInDb).extracting(AccountDbo::getName).isEqualTo(NEW_NAME);
            assertThat(updatedAccountInDb).extracting(AccountDbo::getPhoneNr).isEqualTo(PHONE_NUMBER);
            assertThat(updatedAccountInDb).extracting(AccountDbo::getCreatedAt).isEqualTo(initialAccountDbo.getCreatedAt());
            assertThat(updatedAccountInDb).extracting(AccountDbo::getModifiedAt).isNotEqualTo(initialAccountDbo.getModifiedAt());
        }

        @Test
        void givenNoAccountInDb_whenUpdateRequest_thenReceiveNotFoundStatus() throws Exception {
            var uuid = UUID.randomUUID();

            var requestBuilder = put("/accounts/{id}", uuid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(prepareJson(NEW_NAME, PHONE_NUMBER));

            var mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();

            var expectedMessage = "Account update request. Account with ID %s not found.".formatted(uuid);
            assertEquals(expectedMessage, mvcResult.getResponse().getContentAsString());
        }
    }

    private String prepareJson(String name, String phoneNr) throws JsonProcessingException {
        var requestAccountDto = new AccountDto();
        requestAccountDto.setName(name);
        requestAccountDto.setPhoneNr(phoneNr);
        return objectMapper.writeValueAsString(requestAccountDto);
    }

    private AccountDbo insertAccount(String name, String phoneNr) {
        var dbo = new AccountDbo();
        dbo.setName(name);
        dbo.setPhoneNr(phoneNr);
        return repository.save(dbo);
    }
}