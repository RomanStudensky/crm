package ru.pnzgu.crm.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    MANAGER("Менеджер"),
    DIRECTOR("Директор");

    private final String value;
}
