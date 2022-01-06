package ru.pnzgu.crm.store.states;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DealState {
    OPEN("Открыта"),
    APPROVE("Утверждена"),
    CLOSE("Закрыта");

    private final String label;
}
