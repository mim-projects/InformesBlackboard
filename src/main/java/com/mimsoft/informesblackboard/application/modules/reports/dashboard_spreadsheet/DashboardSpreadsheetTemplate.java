package com.mimsoft.informesblackboard.application.modules.reports.dashboard_spreadsheet;

import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;

public class DashboardSpreadsheetTemplate implements TemplateInstance {
    @Override
    public void render(OutputStream outputStream) {
        try {
            Workbook workbook = new XSSFWorkbook();
            String sheetName = "CountryBarChart";//"CountryColumnChart";

            XSSFSheet sheet = (XSSFSheet) workbook.createSheet(sheetName);

            // Create row and put some cells in it. Rows and cells are 0 based.
            Row row = sheet.createRow((short) 0);

            Cell cell = row.createCell((short) 0);
            cell.setCellValue("Russia");

            cell = row.createCell((short) 1);
            cell.setCellValue("Canada");

            cell = row.createCell((short) 2);
            cell.setCellValue("USA");

            cell = row.createCell((short) 3);
            cell.setCellValue("China");

            cell = row.createCell((short) 4);
            cell.setCellValue("Brazil");

            cell = row.createCell((short) 5);
            cell.setCellValue("Australia");

            cell = row.createCell((short) 6);
            cell.setCellValue("India");

            row = sheet.createRow((short) 1);

            cell = row.createCell((short) 0);
            cell.setCellValue(17098242);

            cell = row.createCell((short) 1);
            cell.setCellValue(9984670);

            cell = row.createCell((short) 2);
            cell.setCellValue(9826675);

            cell = row.createCell((short) 3);
            cell.setCellValue(9596961);

            cell = row.createCell((short) 4);
            cell.setCellValue(8514877);

            cell = row.createCell((short) 5);
            cell.setCellValue(7741220);

            cell = row.createCell((short) 6);
            cell.setCellValue(3287263);

            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 7, 20);

            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText("Area-wise Top Seven Countries");
            chart.setTitleOverlay(false);

            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            bottomAxis.setTitle("Country");
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setTitle("Area");
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet,
                    new CellRangeAddress(0, 0, 0, 6));

            XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
                    new CellRangeAddress(1, 1, 0, 6));

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            XDDFChartData.Series series1 = data.addSeries(countries, values);
            series1.setTitle("Country", null);
            data.setVaryColors(true);
            chart.plot(data);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
