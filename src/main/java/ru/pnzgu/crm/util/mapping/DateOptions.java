package ru.pnzgu.crm.util.mapping;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

/**
 * Опции для полей типа Date
 */
@UtilityClass
public class DateOptions {

    public static final String PATTERN = "yyyy-MM-dd";

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateOptions.PATTERN);

}
