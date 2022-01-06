package ru.pnzgu.crm.store.states;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ActivityState {
    BEGIN("Начата"),
    IN_PROGRESS("В процессе"),
    END("Завершена");

    @Getter
    private final String label;

    public static ActivityState of(String label) {
        return Stream.of(ActivityState.values())
                .filter(p -> Objects.equals(p.getLabel(), label))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
