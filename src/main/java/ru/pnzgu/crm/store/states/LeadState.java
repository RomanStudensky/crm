package ru.pnzgu.crm.store.states;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LeadState {
    CREATED("Создан"),
    KNOW("Осведомлён"),
    INTEREST("Заинтересован"),
    WANT("Желает"),
    DEAL("Совершил сделку"),
    REPEAT_DEAL("Повторная сделка");

    private final String label;
}
