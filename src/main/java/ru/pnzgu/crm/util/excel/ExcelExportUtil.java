package ru.pnzgu.crm.util.excel;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.pnzgu.crm.dto.ActivityDto;
import ru.pnzgu.crm.dto.DateWrapperDto;
import ru.pnzgu.crm.dto.OrderDto;
import ru.pnzgu.crm.util.excel.enums.OrdersCol;
import ru.pnzgu.crm.util.mapping.DateOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@UtilityClass
public class ExcelExportUtil {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateOptions.PATTERN);

    public static ByteArrayOutputStream createOrdersExelDocument(List<OrderDto> orderList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        createDocumentOrders(orderList, workbook, cellStyle);

        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }

    public static ByteArrayOutputStream createManagerExelDocument(List<ActivityDto> activityList) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        createDocumentManager(activityList, workbook, cellStyle);

        workbook.write(outputStream);
        outputStream.close();
        return outputStream;
    }

    private static void createDocumentOrders(List<OrderDto> orderList, DateWrapperDto dateWrapperDto, XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        Sheet sheet = workbook.createSheet("Отчёт по заявкам");
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
            for (int colNum = 0; colNum < SostavPostavCol.LENGTH; colNum++) {
                createCell(cellStyle, row, sheet, SostavPostavCol.values()[colNum].getColNum(), SostavPostavCol.values()[colNum].getColName());
            }
            // Тело
            for (SostavPostavDTO sostavPostavDTO : nakladDTO.getSostav()) {
                rowNum++;
                row = sheet.createRow(rowNum);
                row.setHeightInPoints(18.0f);
                createCell(cellStyle, row, sheet, SostavPostavCol.COLUMN_NUMBER.getColNum(), String.valueOf(sostavPostavDTO.getId()));
                createCell(cellStyle, row, sheet, SostavPostavCol.COLUMN_PROD_NAME.getColNum(), sostavPostavDTO.getProduct().getNameProd());
                createCell(cellStyle, row, sheet, SostavPostavCol.COLUMN_QUANTITY.getColNum(), String.valueOf(sostavPostavDTO.getQuantity()));
                createCell(cellStyle, row, sheet, SostavPostavCol.COLUMN_PRICE.getColNum(), String.valueOf(sostavPostavDTO.getPrice()));
                createCell(cellStyle, row, sheet, SostavPostavCol.COLUMN_SUMMA.getColNum(), String.valueOf(sostavPostavDTO.getSumma()));
            }
            Long summ = nakladDTO
                    .getSostav()
                    .stream()
                    .map(SostavPostavDTO::getSumma)
                    .reduce(0L, Long::sum);

            rowNum++;
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(25.0f);

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, SostavPostavCol.LENGTH - 2));
            setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, SostavPostavCol.LENGTH - 2), sheet);

            createCell(cellStyle, row, sheet, 0, "Кол-во:");
            createCell(cellStyle, row, sheet, C.LENGTH - 1, String.valueOf(summ));

    }

    private static void createDocumentManager(List<ActivityDto> activityList, XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        Sheet sheet = workbook.createSheet("Отчёт по списанным продуктам");
        sheet.autoSizeColumn(1);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0, "Отчёт о списанных продуктах");

        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 1));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 1), sheet);

        createCell(cellStyle, row, sheet, 0, String.format("Дата формирования отчёта: %s", LocalDate.now()));

        // Шапка
        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);
        for (int colNum = 0; colNum < SpistProdCol.LENGTH; colNum++) {
            createCell(cellStyle, row, sheet, SpistProdCol.values()[colNum].getColNum(), SpistProdCol.values()[colNum].getColName());
        }

        for (AktDTO aktDTO : sostavAktList) {

            if (aktDTO.getSpisProducts().isEmpty()) {
                continue;
            }

            // Тело
            for (SostavAktDTO sostavAktDTO : aktDTO.getSpisProducts()) {
                rowNum++;
                row = sheet.createRow(rowNum);
                row.setHeightInPoints(18.0f);
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_NUMBER.getColNum(), String.valueOf(sostavAktDTO.getId()));
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_PROD_NAME.getColNum(), sostavAktDTO.getProduct().getNameProd());
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_QUANTITY.getColNum(), String.valueOf(sostavAktDTO.getQuantity()));
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_REASON.getColNum(), String.valueOf(sostavAktDTO.getReason()));
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_DATE.getColNum(), String.valueOf(aktDTO.getDateAkt()));
                createCell(cellStyle, row, sheet, SpistProdCol.COLUMN_SUMMA.getColNum(), String.valueOf(sostavAktDTO.getSumma()));
            }
        }
        rowNum++;
        Double summ = sostavAktList
                .stream()
                .map(akt -> akt
                        .getSpisProducts()
                        .stream()
                        .map(SostavAktDTO::getSumma)
                        .reduce(0.0D, Double::sum))
                .reduce(0.0D, Double::sum);

        row = sheet.createRow(rowNum);
        row.setHeightInPoints(25.0f);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 2));
        setMergedCellBorders(new CellRangeAddress(rowNum, rowNum, 0, SpistProdCol.LENGTH - 2), sheet);

        createCell(cellStyle, row, sheet, 0, "Итого:");
        createCell(cellStyle, row, sheet, SpistProdCol.LENGTH - 1, String.valueOf(summ));
    }

    private static void createDocumentProd(ProdazaDTO prodazaDTO, List<SostavProdDTO> sostavProdList, XSSFWorkbook order, XSSFCellStyle cellStyle) {
        Sheet sheet = order.createSheet("Отчёт о продажах в баре");
        sheet.autoSizeColumn(1);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.setHeightInPoints(30.0f);

        for (int colNum = 0; colNum < SostavProdCol.LENGTH; colNum++) {
            createCell(cellStyle, row, sheet, SostavProdCol.values()[colNum].getColNum(), SostavProdCol.values()[colNum].getColName());
        }

        rowNum++;
        row = sheet.createRow(rowNum);
        row.setHeightInPoints(53.0f);

        createCell(cellStyle, row, sheet, SostavProdCol.COLUMN_NUMBER.getColNum(), String.valueOf(prodazaDTO.getId()));
        createCell(cellStyle, row, sheet, SostavProdCol.COLUMN_DRINK_NAME.getColNum(), prodazaDTO.getSotrud().getFio());
        createCell(cellStyle, row, sheet, SostavProdCol.COLUMN_QUANTITY.getColNum(), prodazaDTO.getDateProd().toString());
        createCell(cellStyle, row, sheet, SostavProdCol.COLUMN_PRICE.getColNum(), prodazaDTO.getTimeProd().toString());
        createCell(cellStyle, row, sheet, SostavProdCol.COLUMN_SUMMA.getColNum(), prodazaDTO.getSotrud().getFio());
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
