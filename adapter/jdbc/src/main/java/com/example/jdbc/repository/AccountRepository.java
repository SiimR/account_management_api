package com.example.jdbc.repository;

import com.example.jdbc.dbo.AccountDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountDbo, UUID> {}