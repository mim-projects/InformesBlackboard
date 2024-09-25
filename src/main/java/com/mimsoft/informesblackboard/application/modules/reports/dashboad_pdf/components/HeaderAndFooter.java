package com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.components;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.DashboardPdfTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.utils.CustomElement;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.utils.CustomFonts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class HeaderAndFooter extends PdfPageEventHelper {
    private final static float HEIGHT_CELL = 40;
    public final static float TOTAL_HEIGHT = HEIGHT_CELL * 3.25f;
    private final BundleLanguage bundleLanguage;
    private final String periodLegend;

    float multi = (float) (CustomFonts.FONT_P.getSize() * 0.69);
    private PdfTemplate t;
    private Image total;

    public HeaderAndFooter(BundleLanguage bundleLanguage, String periodLegend) {
        this.bundleLanguage = bundleLanguage;
        this.periodLegend = periodLegend;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        try { table.setWidths(new float[] { 8, 72, 16 }); } catch (DocumentException e) { e.printStackTrace(); }
        table.getDefaultCell().setFixedHeight(HEIGHT_CELL);
        table.setLockedWidth(true);

        Image imageLeft = null;
        try { imageLeft = Image.getInstance(Objects.requireNonNull(getClass().getResource("/assets/escudo-actualizado-2022-w1000px.png"))); }
        catch (BadElementException | IOException ignored) { }

        Image imageRight = null;
        try { imageRight = Image.getInstance(Objects.requireNonNull(getClass().getResource("/assets/EM3197430-3987.png"))); }
        catch (BadElementException | IOException ignored) { }

        PdfPCell left = CustomElement.CellNoBorder(imageLeft == null ? new PdfPCell() : new PdfPCell(imageLeft, true));
        PdfPCell right = CustomElement.CellNoBorder(imageRight == null ? new PdfPCell() : new PdfPCell(imageRight, true));
        PdfPCell center = CustomElement.CellNoBorder();
        center.setPaddingLeft(10);
        center.setPaddingRight(10);

        PdfPTable pdfPTableCenter = new PdfPTable(1);
        pdfPTableCenter.setWidthPercentage(100);
        pdfPTableCenter.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(bundleLanguage.getBundleMessage("uabc_description"), CustomFonts.FONT_H1))));
        pdfPTableCenter.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(bundleLanguage.getBundleMessage("ciad_description"), CustomFonts.FONT_H2))));
        pdfPTableCenter.addCell(CustomElement.CellNoBorder(new Phrase(" ", CustomFonts.FONT_P_XS)));
        pdfPTableCenter.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(bundleLanguage.getBundleMessage("igupb_description"), CustomFonts.FONT_P))));
        pdfPTableCenter.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(periodLegend, CustomFonts.FONT_P))));
        center.addElement(pdfPTableCenter);

        table.addCell(left);
        table.addCell(center);
        table.addCell(right);
        table.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight() - DashboardPdfTemplate.MARGIN_LRTB[3], writer.getDirectContent());
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte pdfContentByte = writer.getDirectContent();

        String left = bundleLanguage.getBundleMessage("legend_left_pdf_report");
        String right = bundleLanguage.getBundleMessage("legend_right_pdf_report");

        Date date = new Date();
        String[] months = bundleLanguage.getBundleMessage("months").split(",");
        int indexMonth = Integer.parseInt(new SimpleDateFormat("MM").format(date)) - 1;
        right = right.replaceAll("#1", new SimpleDateFormat("dd").format(date));
        right = right.replaceAll("#2", months[indexMonth]);
        right = right.replaceAll("#3", new SimpleDateFormat("yyyy").format(date));

        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(left, CustomFonts.FONT_P), document.leftMargin(), document.bottom(), 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(right, CustomFonts.FONT_P), document.right(), document.bottom(), 0);

        try {
            String page = bundleLanguage.getBundleMessage("page");
            String currentPage = String.valueOf(writer.getCurrentPageNumber() < 9 ? "0" + writer.getCurrentPageNumber() : writer.getCurrentPageNumber());
            String of = bundleLanguage.getBundleMessage("prefix_of");
            String totalPage = "00";

            PdfPTable pdfPTablePaginator = new PdfPTable(4);
            pdfPTablePaginator.setTotalWidth(new float[] {
                    page.length() * multi,
                    (currentPage.length() + 1) * multi,
                    (of.length() + 1) * multi,
                    (totalPage.length() + 1) * multi
            });
            pdfPTablePaginator.setLockedWidth(true);
            pdfPTablePaginator.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(page, CustomFonts.FONT_P))));
            pdfPTablePaginator.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(currentPage, CustomFonts.FONT_P))));
            pdfPTablePaginator.addCell(CustomElement.CellCenterText(CustomElement.CellNoBorder(new Phrase(of.toLowerCase(), CustomFonts.FONT_P))));
            pdfPTablePaginator.addCell(CustomElement.CellNoBorder(new PdfPCell(total)));

            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            pdfPTablePaginator.writeSelectedRows(0, -1,
                    document.getPageSize().getWidth() / 2 - pdfPTablePaginator.getTotalWidth() / 2,
                    document.bottomMargin() + CustomFonts.FONT_P.getSize() + 2,
                    canvas
            );
            canvas.endMarkedContentSequence();
        } catch (DocumentException ignore) { }
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(3 * multi, 3 * multi);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException ignore) { }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        int countTotalPages = writer.getPageNumber();
        String count = countTotalPages + "";
        if (count.length() < 2) count = "0" + count;
        ColumnText.showTextAligned(t, Element.ALIGN_CENTER, new Phrase(count, CustomFonts.FONT_P), multi * 1.5f, multi + 2, 0);
    }
}
