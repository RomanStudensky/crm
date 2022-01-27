package ru.pnzgu.crm.util.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrdersCol {
    COLUMN_NUMBER (0, "Номер"),
    COLUMN_DATE(1, "Дата"),
    COLUMN_FULL_NAME(2, "ФИО"),
    COLUMN_ORIGIN(3, "Источник");

    private int colNum;
    private String colName;
    public static final int LENGTH = 4;
}