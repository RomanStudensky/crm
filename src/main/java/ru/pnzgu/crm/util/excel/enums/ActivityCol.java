package ru.pnzgu.crm.util.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityCol {
    COLUMN_NUMBER (0, "№"),
    COLUMN_TITLE(1, "Заголовок"),
    COLUMN_DATE_BEGIN(2, "Дата начала выполнения"),
    COLUMN_DATE_END(3, "Дата окончания выполнеия"),
    COLUMN_DEAL(4, "Сделка");

    private int colNum;
    private String colName;
    public static final int LENGTH = 5;
}