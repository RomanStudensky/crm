package ru.pnzgu.crm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActivityState {
    BEGIN("Начата"),
    IN_PROGRESS("В процессе"),
    END("Завершена");

    @Getter
    private final String label;
}
