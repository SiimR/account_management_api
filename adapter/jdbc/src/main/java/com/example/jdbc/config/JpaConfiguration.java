package com.example.jdbc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.jdbc.repository")
@EntityScan(basePackages = "com.example.jdbc.dbo")
class JpaConfiguration {}
