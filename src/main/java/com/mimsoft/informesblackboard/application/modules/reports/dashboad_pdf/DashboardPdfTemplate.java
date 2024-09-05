package com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.constants.Colors;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.data.queries.custom_table_courses_users.CustomTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.components.BarCharts;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.components.HeaderAndFooter;
import com.mimsoft.informesblackboard.application.modules.reports.utils.SectionTypes;
import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import com.mimsoft.informesblackboard.application.utils.others.ArrayHelper;
import com.mimsoft.informesblackboard.domain.entities.Grades;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.itextpdf.text.PageSize.LETTER;

public class DashboardPdfTemplate implements TemplateInstance {
    public static float INCH_TO_POINTS(float inch) { return inch * 72; }
    public static final float[] MARGIN_LRTB = new float[] {
            INCH_TO_POINTS(0.79f), // LEFT
            INCH_TO_POINTS(0.79f), // RIGHT
            INCH_TO_POINTS(0.49f), // TOP
            INCH_TO_POINTS(0.49f), // BOTTOM
    };
    private static final int PADDING = 4;
    private final RequestController requestController;
    private final GraphicTableCoursesUsersHelper data;
    private final BundleLanguage bundleLanguage;
    private final Document document;
    private final String periodLegend;

    public DashboardPdfTemplate(RequestController requestController, GraphicTableCoursesUsersHelper data, BundleLanguage bundleLanguage, String periodLegend) {
        this.data = data;
        this.bundleLanguage = bundleLanguage;
        this.requestController = requestController;
        this.document = new Document(LETTER, MARGIN_LRTB[0], MARGIN_LRTB[1], HeaderAndFooter.TOTAL_HEIGHT, MARGIN_LRTB[3]);
        this.periodLegend = periodLegend;
    }

    @Override
    public void render(OutputStream outputStream) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setPageEvent(new HeaderAndFooter(requestController, bundleLanguage, periodLegend));
            document.open();

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            HashMap<SectionTypes, String> types = new LinkedHashMap<>();
            types.put(SectionTypes.USERS, bundleLanguage.getBundleMessage("users"));
            types.put(SectionTypes.COURSES, bundleLanguage.getBundleMessage("courses"));

            for (SectionTypes type: types.keySet()) {
                for (Grades grade: data.getAllGradesForType(type.getValue())) {
                    String title = types.get(type) + ". " + grade.getName();
                    if (createTableAndGraphic(table, title, type, grade)) {
                        table.addCell(emptyHeight(25));
                    }
                }
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Data for section
    private boolean createTableAndGraphic(PdfPTable parent, String title, SectionTypes type, Grades grade) throws BadElementException, IOException {
        if (!data.renderHelper(type.getValue(), grade)) return false;

        // Data
        CustomTableCoursesUsersHelper result = data.getCustomTableGraphicDataHelper(type.getValue(), grade);
        String[] dataHeader = result.getAllColumns().toArray(new String[0]);
        String[] dataKeyword = result.getAllRows().toArray(new String[0]);
        Integer[][] values = new Integer[dataKeyword.length][dataHeader.length];
        Integer[] subTotalHorizontal = new Integer[dataHeader.length];
        Integer[] subTotalVertical = new Integer[dataKeyword.length];
        Integer total = result.getTotal();

        for (int i = 0; i < dataHeader.length; i++) subTotalHorizontal[i] = result.getTotalColumn(dataHeader[i]);
        for (int j = 0; j < dataKeyword.length; j++) subTotalVertical[j] = result.getTotalRow(dataKeyword[j]);
        for (int j = 0; j < dataKeyword.length; j++) {
            for (int i = 0; i < dataHeader.length; i++) {
                values[j][i] = result.getValue(dataHeader[i], dataKeyword[j]);
            }
        }

        PdfPTable table = new PdfPTable(1);
        PdfPTable customTable = createTable(dataHeader, dataKeyword, values, subTotalVertical, subTotalHorizontal, total);
        PdfPCell customGraphic = createBarChartGraphic(dataHeader, dataKeyword, values);

        table.addCell(cellBorder(createText(title.toUpperCase(), 11, true), 0));
        table.addCell(emptyHeight(11));
        table.addCell(cellBorder(customTable, 0));
        table.addCell(emptyHeight(11));
        table.addCell(customGraphic);
        parent.addCell(cellBorder(table, 0));
        return true;
    }

    private PdfPTable createTable(String[] dataHeader, String[] dataKeyword, Integer[][] values, Integer[] subtotalVertical, Integer[] subtotalHorizontal, Integer total) {
        int columns = dataHeader.length + 2;
        PdfPTable table = new PdfPTable(columns);

        // Header
        table.addCell(valueCell("", null, null, false));
        for (String current: dataHeader) table.addCell(headerCell(current));
        table.addCell(valueCell("", null, null, false));

        // Rows
        int TOTAL = 0;
        int SUBTOTAL = 1;
        String[] dataKeywordAdded = new ArrayHelper<String>().added(
                dataKeyword,
                bundleLanguage.getBundleMessage("subtotal").toUpperCase(),
                bundleLanguage.getBundleMessage("total").toUpperCase()
        );

        int partialRow = 0;
        int incrementRow = 0;
        for (int row = dataKeywordAdded.length - 1; row >= 0; row--) {
            if (row == TOTAL) {
                table.addCell(keywordCell(dataKeywordAdded[incrementRow], convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK));
                for (int k = 0; k < dataHeader.length - 1; k++) table.addCell(valueCell("", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
                table.addCell(valueCell(String.valueOf(total), convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
                table.addCell(valueCell("", null, null, false));
            } else if (row == SUBTOTAL) {
                table.addCell(keywordCell(dataKeywordAdded[incrementRow], convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK));
                for (int k = 0; k < dataHeader.length; k++) table.addCell(valueCell(String.valueOf(subtotalHorizontal[k]), convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK, false));
                table.addCell(valueCell(String.valueOf(total), convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
            } else {
                table.addCell(keywordCell(dataKeywordAdded[incrementRow], convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK));
                for (int k = 0; k < dataHeader.length; k++) table.addCell(valueCell(String.valueOf(values[partialRow][k]), null, null, false));
                table.addCell(valueCell(String.valueOf(subtotalVertical[partialRow]), convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK, false));
                partialRow++;
            }
            incrementRow++;
        }

        return table;
    }

    private PdfPCell createBarChartGraphic(String[] dataHeader, String[] dataKeyword, Integer[][] values) throws BadElementException, IOException {
        Image image = new BarCharts(750, 180).setDataset(dataHeader, dataKeyword, values).build().getImage();
        PdfPCell graphic = new PdfPCell(image, true);
        graphic.setBorder(0);
        return graphic;
    }

    // Components
    private PdfPCell headerCell(String value) {
        PdfPCell cell = new PdfPCell(createText(value.toUpperCase(), BaseColor.WHITE, 8));
        cell.setBackgroundColor(convertColorRGBArr(Colors.UABC_GREEN));
        cell.getPhrase().getFont().setStyle(Font.BOLD);
        cell.setPadding(PADDING);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell keywordCell(String value, BaseColor background, BaseColor text) {
        PdfPCell cell = new PdfPCell(createText(value, text, 8));
        cell.setBackgroundColor(background);
        cell.getPhrase().getFont().setStyle(Font.BOLD);
        cell.setPadding(3);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }

    private PdfPCell valueCell(String value, BaseColor background, BaseColor text, boolean bold) {
        PdfPCell cell = new PdfPCell(createText(value, text, 10));
        if (background != null) cell.setBackgroundColor(background);
        if (bold) cell.getPhrase().getFont().setStyle(Font.BOLD);
        cell.setPadding(PADDING);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if (value.isEmpty() && background == null && text == null) cell.setBorder(0);
        return cell;
    }

    private PdfPCell emptyHeight(int size) {
        PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(size);
        cell.setBorder(0);
        return cell;
    }

    private PdfPCell cellBorder(PdfPTable table, int border) {
        PdfPCell cell = new PdfPCell(table);
        cell.setBorder(border);
        return cell;
    }

    private PdfPCell cellBorder(Phrase phrase, int border) {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(border);
        return cell;
    }

    // Helper
    private BaseColor convertColorRGBArr(String[] rgb) {
        return new BaseColor(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    private BaseColor convertColorRGBArr(Colors color) {
        return convertColorRGBArr(color.getRGB());
    }

    private Phrase createText(String text, BaseColor color, int size) {
        Phrase phrase = createText(text, size);
       if (color != null) phrase.getFont().setColor(color);
       return phrase;
    }

    private Phrase createText(String text, int size) {
        Phrase phrase = new Phrase(text);
        phrase.getFont().setSize(size);
        return phrase;
    }

    private Phrase createText(String text, int size, boolean bold) {
        Phrase phrase = createText(text, size);
        if (bold) phrase.getFont().setStyle(Font.BOLD);
        return phrase;
    }
}
