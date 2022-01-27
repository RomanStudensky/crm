package ru.pnzgu.crm.util.excel;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.lang.NonNull;
import ru.pnzgu.crm.dto.*;
import ru.pnzgu.crm.util.excel.enums.ActivityCol;
import ru.pnzgu.crm.util.excel.enums.DealCol;
import ru.pnzgu.crm.util.excel.enums.OrdersCol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.pnzgu.crm.util.mapping.DateOptions.formatter;

@UtilityClass
public class ExcelExportUtil {

    /**
     * Create and get excel document with orders.
     *
     * @param orderList order list
     * @param dateWrapper date begin, date end wrapper
     * @return excel document with orders
     */
    public static ByteArrayOutputStream createAndGetOrdersExelDocument(@NonNull List<OrderDto> orderList, @NonNull DateWrapperDto dateWrapper) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        createDocumentOrders(orderList, dateWrapper, workbook, cellStyle);

        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }

    /**
     * Create and get excel document with activity by manager.
     *
     * @param manager manager
     * @param activityList activity list
     * @return excel document with orders by manager
     */
    public static ByteArrayOutputStream createAndGetManagerExelDocument(@NonNull ManagerDto manager, @NonNull List<ActivityDto> activityList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        createDocumentManager(manager, activityList, workbook, cellStyle);

        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }

    /**
     * Create and get excel document with deals between dates.
     *
     * @param dateWrapper dateWrapper
     * @param dealList deal list
     * @return excel document with deals by manager
     */
    public static ByteArrayOutputStream createAndGetDealExelDocument(@NonNull List<DealDto> dealList, @NonNull DateWrapperDto dateWrapper) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        createDocumentDeal(dateWrapper, dealList, workbook, cellStyle);

        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }

    private static void createDocumentOrders(List<OrderDto> orderList, DateWrapperDto dateWrapperDto, XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        Sheet sheet = workbook.createSheet(
                String.format("Отчёт по заявкам с %s по %s",
                        dateWrapperDto.getBeginDate().format(formatter),
                        dateWrapperDto.getEndDate().format(formatter))
        );

        sheet.autoSizeColumn(1);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, OrdersCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(0, 0, 0, OrdersCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0,
                String.format("Отчёт по заявкам. Дата от %s до %s",
                dateWrapperDto.getBeginDate().format(formatter),
                dateWrapperDto.getEndDate().format(formatter)));

        // Шапка
        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);
        for (int colNum = 0; colNum < OrdersCol.LENGTH; colNum++) {
            createCell(cellStyle, row, sheet, OrdersCol.values()[colNum].getColNum(), OrdersCol.values()[colNum].getColName());
        }

        // Тело
        for (OrderDto order : orderList) {
            rowNum++;
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(18.0f);
            createCell(cellStyle, row, sheet, OrdersCol.COLUMN_NUMBER.getColNum(), String.valueOf(order.getId()));
            createCell(cellStyle, row, sheet, OrdersCol.COLUMN_DATE.getColNum(), order.getDate().format(formatter));
            createCell(cellStyle, row, sheet, OrdersCol.COLUMN_FULL_NAME.getColNum(), order.getContact().getFullName());
            createCell(cellStyle, row, sheet, OrdersCol.COLUMN_ORIGIN.getColNum(), String.valueOf(order.getOrigin()));
        }

        int count = orderList.size();

        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, OrdersCol.LENGTH - 2));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, OrdersCol.LENGTH - 2), sheet);

        createCell(cellStyle, row, sheet, 0, "Кол-во:");
        createCell(cellStyle, row, sheet, OrdersCol.LENGTH - 1, String.valueOf(count));
    }

    private static void createDocumentManager(ManagerDto manager, List<ActivityDto> activityList, XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        Sheet sheet = workbook.createSheet(String.format("Отчёт по менеджеру %s", manager.getFullName()));
        sheet.autoSizeColumn(1);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0, String.format("Отчёт по менеджеру %s", manager.getFullName()));

        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0, String.format("Дата формирования отчёта: %s", LocalDate.now().format(formatter)));

        // Шапка
        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);
        for (int colNum = 0; colNum < ActivityCol.LENGTH; colNum++) {
            createCell(cellStyle, row, sheet, ActivityCol.values()[colNum].getColNum(), ActivityCol.values()[colNum].getColName());
        }

        // Тело
        for (ActivityDto activity : activityList) {
            rowNum++;
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(18.0f);
            createCell(cellStyle, row, sheet, ActivityCol.COLUMN_NUMBER.getColNum(), String.valueOf(activity.getId()));
            createCell(cellStyle, row, sheet, ActivityCol.COLUMN_TITLE.getColNum(), activity.getTitle());
            createCell(cellStyle, row, sheet, ActivityCol.COLUMN_DATE_BEGIN.getColNum(), String.valueOf(activity.getDateBegin()));
            createCell(cellStyle, row, sheet, ActivityCol.COLUMN_DATE_END.getColNum(), activity.getDateEnd() == null ? "" : activity.getDateEnd().format(formatter));
            createCell(cellStyle, row, sheet, ActivityCol.COLUMN_DEAL.getColNum(), activity.getDeal() == null ? "" : String.format("(№ %s) %s", activity.getId(), activity.getDeal().getTitle()));
        }

        rowNum++;
        int count = activityList.size();

        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 2));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, ActivityCol.LENGTH - 2), sheet);


        createCell(cellStyle, row, sheet, 0, "Кол-во:");
        createCell(cellStyle, row, sheet, ActivityCol.LENGTH - 1, String.valueOf(count));
    }

    private static void createDocumentDeal(DateWrapperDto dateWrapper, List<DealDto> daelList, XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        Sheet sheet = workbook.createSheet("Отчёт по сделкам");
        sheet.autoSizeColumn(1);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, DealCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, DealCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0, String.format("Отчёт по сделкам от %s до %s", dateWrapper.getBeginDate(), dateWrapper.getEndDate()));

        // Шапка
        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);
        for (int colNum = 0; colNum < DealCol.LENGTH; colNum++) {
            createCell(cellStyle, row, sheet, DealCol.values()[colNum].getColNum(), DealCol.values()[colNum].getColName());
        }

        // Тело
        for (DealDto deal : daelList) {
            rowNum++;
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(18.0f);
            createCell(cellStyle, row, sheet, DealCol.COLUMN_NUMBER.getColNum(), String.valueOf(deal.getId()));
            createCell(cellStyle, row, sheet, DealCol.COLUMN_TITLE.getColNum(), deal.getTitle());
            createCell(cellStyle, row, sheet, DealCol.COLUMN_TITLE.getColNum(), deal.getDate().format(formatter));
            createCell(
                    cellStyle,
                    row,
                    sheet,
                    DealCol.COLUMN_SUMMA.getColNum(),
                    deal
                            .getSostav()
                            .stream()
                            .map((a) -> a.getProduct().getPrice() * a.getCount())
                            .reduce(Double::sum).toString()
            );

        }

    }

    private static void createCell(XSSFCellStyle cellStyle, Row row, Sheet sheet, int colNumber, String cellValue) {
        Cell cell = row.createCell(colNumber);
        cell.setCellStyle(getCellStyle(cellStyle));
        cell.setCellValue(cellValue);
        sheet.autoSizeColumn(colNumber);
    }

    
    private static XSSFCellStyle getCellStyle(XSSFCellStyle cellStyle) {

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setWrapText(true);

        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return cellStyle;
    }

    private void setMergedCellBorders(CellRangeAddress cellRangeAddress, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);

        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), cellRangeAddress, sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), cellRangeAddress, sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), cellRangeAddress, sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), cellRangeAddress, sheet);
    }

}
