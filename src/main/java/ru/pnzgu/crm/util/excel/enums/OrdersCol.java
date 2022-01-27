package ru.pnzgu.crm.util.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrdersCol {
    COLUMN_NUMBER (0, "Номер"),
    COLUMN_PROD_NAME(1, "Дата"),
    COLUMN_QUANTITY(2, "ФИО"),
    COLUMN_PRICE(3, "Источник");

    private int colNum;
    private String colName;
    public static final int LENGTH = 4;
}