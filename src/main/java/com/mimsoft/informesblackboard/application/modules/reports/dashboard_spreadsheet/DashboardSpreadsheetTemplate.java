package com.mimsoft.informesblackboard.application.modules.reports.dashboard_spreadsheet;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.reports.utils.SectionTypes;
import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DashboardSpreadsheetTemplate implements TemplateInstance {
    private final GraphicTableCoursesUsersHelper data;
    private final BundleLanguage bundleLanguage;
    private final String periodLegend;

    public DashboardSpreadsheetTemplate(RequestController requestController, GraphicTableCoursesUsersHelper data, BundleLanguage bundleLanguage, String periodLegend) {
        this.data = data;
        this.bundleLanguage = bundleLanguage;
        this.periodLegend = periodLegend;
    }

    @Override
    public void render(OutputStream outputStream) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            int currentRow = 0;
            currentRow = drawHeaderReturnIndexRow(currentRow, sheet);
            for (SectionTypes type: SectionTypes.values()) {
                currentRow = drawTableReturnIndexRow(currentRow, sheet, type);
            }
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int drawHeaderReturnIndexRow(int currentRow, Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        for (String str: new String[] {
                bundleLanguage.getBundleMessage("uabc_description"),
                bundleLanguage.getBundleMessage("ciad_description"),
                bundleLanguage.getBundleMessage("igupb_description"),
                periodLegend
        }) {
            Cell cell = sheet.createRow(currentRow).createCell(1);
            cell.setCellValue(str);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 1, 8));
            currentRow++;
        }

        // Logo Left
        sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 0));
        sheet.autoSizeColumn(0);
        try { addImage(sheet, 0, 0, 3, 1, 1, 1.3,
                getClass().getResourceAsStream("/assets/escudo-actualizado-2022-w1000px.png"));
        } catch (IOException ignore) { }

        // Logo Right
        sheet.addMergedRegion(new CellRangeAddress(0, 3, 9, 10));
        try { addImage(sheet, 0, 9, 3, 10, 2, 1.3,
                getClass().getResourceAsStream("/assets/EM3197430-3987.png"));
        } catch (IOException ignore) { }

        return currentRow + 2;
    }

    private int drawTableReturnIndexRow(int currentRow, Sheet sheet, SectionTypes type) {
        int spaceHeader = 3;
        int globalColumn = 0;
        int sizeHeader = sizeHeader(type);
        List<String> header = getAllHeader(type);
        List<String> rows = getAllKeyword(type);
        List<String> allGradesValid = getAllGradesValid(type);

        Row rowSheet = sheet.createRow(currentRow);
        int indexTableGrade = 0;
        for (String column: header) {
            if (globalColumn == 0) {
                rowSheet.createCell(globalColumn + 1).setCellValue(getTitleTable(type, allGradesValid, indexTableGrade));
                indexTableGrade++;
            } else if (column.isEmpty()) {
                rowSheet.createCell(globalColumn + spaceHeader).setCellValue(getTitleTable(type, allGradesValid, indexTableGrade));
                indexTableGrade++;
            }
            globalColumn += (column.isEmpty() && globalColumn > 1) ? spaceHeader : 1;
        }

        // Headers
        globalColumn = 0;
        rowSheet = sheet.createRow(++currentRow);
        for (String column: header) {
            rowSheet.createCell(globalColumn).setCellValue(column);
            globalColumn += (column.isEmpty() && globalColumn > 1) ? spaceHeader : 1;
        }

        // Keywords
        for (String current: rows) {
            rowSheet = sheet.createRow(++currentRow);
            Cell cell = rowSheet.createCell(0);
            cell.setCellValue(current);
        }

        // Body Values
        currentRow -= rows.size();
        for (String row: rows) {
            globalColumn = 0;
            rowSheet = sheet.getRow(++currentRow);
            List<Grades> allGradesListValid = getAllGradesListValid(type);
            indexTableGrade = -1;
            for (String column: header) {
                if (column.isEmpty()) indexTableGrade++;

                if (!column.isEmpty()) {
                    rowSheet.createCell(globalColumn).setCellValue(getValue(allGradesListValid.get(indexTableGrade), type, row, column));
                } else if (globalColumn > 0) {
                    // SUBTOTAL HORIZONTAL
                    createFormulaSubTotalHorizontal(rowSheet.createCell(globalColumn), sizeHeader - 1);
                }
                globalColumn += (column.isEmpty() && globalColumn > 1) ? spaceHeader : 1;
            }
            // SUBTOTAL HORIZONTAL
            createFormulaSubTotalHorizontal(rowSheet.createCell(globalColumn), sizeHeader - 1);
        }

        // SUBTOTAL VERTICAL
        globalColumn = 0;
        rowSheet = sheet.createRow(++currentRow);
        rowSheet.createCell(globalColumn).setCellValue("SUBTOTAL");
        for (String column: header) {
            if (!column.isEmpty() && globalColumn > 0) {
                createFormulaSubTotalVertical(rowSheet.createCell(globalColumn), rows.size());
            } else if (column.isEmpty() && globalColumn > 1) {
                createFormulaTotalVertical(rowSheet.createCell(globalColumn), rows.size());
            }
            globalColumn += (column.isEmpty() && globalColumn > 1) ? spaceHeader : 1;
        }
        // TOTAL VERTICAL
        createFormulaTotalVertical(rowSheet.createCell(globalColumn), rows.size());

        // TOTAL HORIZONTAL (SUBTOTAL VERTICAL)
        globalColumn = 0;
        rowSheet = sheet.createRow(++currentRow);
        rowSheet.createCell(globalColumn).setCellValue("TOTAL");
        for (String column: header) {
            if (globalColumn > 0 && column.isEmpty()) {
                createFormulaTotalHorizontal(rowSheet.createCell(globalColumn - 1), sizeHeader - 1);
            }
            globalColumn += (column.isEmpty() && globalColumn > 1) ? spaceHeader : 1;
        }
        createFormulaTotalHorizontal(rowSheet.createCell(globalColumn - 1), sizeHeader - 1);

        return currentRow + (!header.isEmpty() ? 2 : 1);
    }

    private void addImage(Sheet sheet, int row, int col, int row2, int col2, double scaleX, double scaleY, InputStream inputStream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        inputStream.close();

        ClientAnchor clientAnchor = sheet.getWorkbook().getCreationHelper().createClientAnchor();
        clientAnchor.setRow1(row);
        clientAnchor.setRow2(row2);
        clientAnchor.setCol1(col);
        clientAnchor.setCol2(col2);

        XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
        drawing.createPicture(clientAnchor, pictureIdx).resize(scaleX, scaleY);
    }

    private int sizeHeader(SectionTypes type) {
        Set<String> list = new LinkedHashSet<>();;
        for (Grades grade: data.getAllGradesForType(type.getValue())) {
            if (!data.renderHelper(type.getValue(), grade)) continue;
            list.addAll(data.getCustomTableGraphicDataHelper(type.getValue(), grade).getAllColumns());
        }
        return list.size();
    }

    private String getTitleTable(SectionTypes type, List<String> allGradesValid, int indexTableGrade) {
        try {
            return (type.getValue() + ". " + allGradesValid.get(indexTableGrade)).toUpperCase();
        } catch (Exception ignore) {
            return type.getValue().toUpperCase();
        }
    }

    private List<String> getAllGradesValid(SectionTypes type) {
        List<String> list = new ArrayList<>();
        for (Grades grade: data.getAllGradesForType(type.getValue())) {
            if (!data.renderHelper(type.getValue(), grade)) continue;
            list.add(grade.getName());
        }
        return list;
    }

    private List<Grades> getAllGradesListValid(SectionTypes type) {
        List<Grades> list = new ArrayList<>();
        for (Grades grade: data.getAllGradesForType(type.getValue())) {
            if (!data.renderHelper(type.getValue(), grade)) continue;
            list.add(grade);
        }
        return list;
    }

    private List<String> getAllHeader(SectionTypes type) {
        List<String> list = new ArrayList<>();
        for (Grades grade: data.getAllGradesForType(type.getValue())) {
            if (!data.renderHelper(type.getValue(), grade)) continue;
            list.add("");
            list.addAll(data.getCustomTableGraphicDataHelper(type.getValue(), grade).getAllColumns());
        }
        return list;
    }

    private List<String> getAllKeyword(SectionTypes type) {
        Set<String> list = new LinkedHashSet<>();
        for (Grades grade: data.getAllGradesForType(type.getValue())) {
            if (!data.renderHelper(type.getValue(), grade)) continue;
            list.addAll(data.getCustomTableGraphicDataHelper(type.getValue(), grade).getAllRows());
        }
        return new ArrayList<>(list);
    }

    private Integer getValue(Grades grades, SectionTypes type, String row, String column) {
        return data.getCustomTableGraphicDataHelper(type.getValue(), grades).getValue(column, row);
    }

    private void createFormulaSubTotalVertical(Cell cell, int rowLength) {
        String address = cell.getAddress().formatAsString();
        String currentRowLetter = address.replaceAll("\\d", "");
        int currentRowNumber = Integer.parseInt(address.replaceAll("[A-Za-z]", ""));
        cell.setCellFormula("SUM(" + currentRowLetter + (currentRowNumber - 1) + ":" + currentRowLetter + (currentRowNumber - rowLength) + ")");
    }

    private void createFormulaSubTotalHorizontal(Cell cell, int rowLength) {
        Cell rowRefEnd = cell.getSheet().getRow(cell.getRowIndex()).getCell(cell.getColumnIndex() - 1);
        Cell rowRefStart = cell.getSheet().getRow(cell.getRowIndex()).getCell(rowRefEnd.getColumnIndex() - rowLength);
        cell.setCellFormula("SUM(" + rowRefStart.getAddress().formatAsString() + ":" + rowRefEnd.getAddress().formatAsString() + ")");
    }

    private void createFormulaTotalHorizontal(Cell cell, int colLength) {
        Cell rowRefEnd = cell.getSheet().getRow(cell.getRowIndex() - 1).getCell(cell.getColumnIndex());
        Cell rowRefStart = cell.getSheet().getRow(cell.getRowIndex() - 1).getCell(rowRefEnd.getColumnIndex() - colLength);
        cell.setCellFormula("SUM(" + rowRefStart.getAddress().formatAsString() + ":" + rowRefEnd.getAddress().formatAsString() + ")");
    }

    private void createFormulaTotalVertical(Cell cell, int colLength) {
        Cell rowRefEnd = cell.getSheet().getRow(cell.getRowIndex() - 1).getCell(cell.getColumnIndex());
        Cell rowRefStart = cell.getSheet().getRow(cell.getRowIndex() - colLength).getCell(rowRefEnd.getColumnIndex());
        cell.setCellFormula("SUM(" + rowRefStart.getAddress().formatAsString() + ":" + rowRefEnd.getAddress().formatAsString() + ")");
    }
}
