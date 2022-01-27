package ru.pnzgu.crm.util.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DealCol {
    COLUMN_NUMBER (0, "№"),
    COLUMN_TITLE(1, "Заголовок"),
    COLUMN_DATE(2, "Дата"),
    COLUMN_SUMMA(3, "Сумма");

    private int colNum;
    private String colName;
    public static final int LENGTH = 3;
}
