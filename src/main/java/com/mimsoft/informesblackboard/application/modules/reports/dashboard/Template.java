package com.mimsoft.informesblackboard.application.modules.reports.dashboard;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.mimsoft.informesblackboard.application.data.constants.Colors;

import java.io.IOException;

class Template {
    public static Template Instance(Document document) {
        return new Template(document);
    }

    private static final int PADDING = 4;
    private final Document document;

    public Template(Document document) {
        this.document = document;
    }

    public void render() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        for (int k = 0; k < 6; k++) {
            createTableAndGraphic(table);
            table.addCell(emptyHeight(40));
        }

        document.add(table);
    }

    // Data for section
    private void createTableAndGraphic(PdfPTable parent) throws BadElementException, IOException {
        PdfPTable table = new PdfPTable(1);

        table.addCell(cellBorder(createText("Licenciatura", 12), 0));
        table.addCell(emptyHeight(20));
        table.addCell(cellBorder(createTable(), 0));
        table.addCell(emptyHeight(20));
        table.addCell(createGraphic());

        parent.addCell(cellBorder(table, 0));
    }

    private PdfPTable createTable() {
        PdfPTable table = new PdfPTable(5);

        // Header
        table.addCell(valueCell("", null, null, false));
        table.addCell(headerCell("Tijuana"));
        table.addCell(headerCell("Mexicali"));
        table.addCell(headerCell("Ensenada"));
        table.addCell(valueCell("", null, null, false));

        // Rows
        table.addCell(keywordCell("Docentes", convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK));
        table.addCell(valueCell("423", null, null, false));
        table.addCell(valueCell("32", null, null, false));
        table.addCell(valueCell("14", null, null, false));
        table.addCell(valueCell("469", convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK, false));

        table.addCell(keywordCell("Estudiantes", convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK));
        table.addCell(valueCell("423", null, null, false));
        table.addCell(valueCell("32", null, null, false));
        table.addCell(valueCell("14", null, null, false));
        table.addCell(valueCell("469", convertColorRGBArr(Colors.UABC_YELLOW_2), BaseColor.BLACK, false));

        table.addCell(keywordCell("SUBTOTAL", convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK));
        table.addCell(valueCell("423", convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK, false));
        table.addCell(valueCell("32", convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK, false));
        table.addCell(valueCell("14", convertColorRGBArr(Colors.UABC_YELLOW_1), BaseColor.BLACK, false));
        table.addCell(valueCell("469", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));

        table.addCell(keywordCell("TOTAL", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK));
        table.addCell(valueCell("", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
        table.addCell(valueCell("", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
        table.addCell(valueCell("14", convertColorRGBArr(Colors.UABC_YELLOW), BaseColor.BLACK, false));
        table.addCell(valueCell("", null, null, false));

        return table;
    }

    private PdfPCell createGraphic() throws BadElementException, IOException {
        Image image = new BarCharts(750, 200).getImage();
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
}
