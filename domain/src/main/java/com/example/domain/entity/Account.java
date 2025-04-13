package com.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Account {
    private UUID id;

    private String name;

    private String phoneNr;
}
