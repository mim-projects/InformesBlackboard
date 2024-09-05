package com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.utils;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class CustomElement {
    public static PdfPCell CellNoBorder(PdfPCell cell) {
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell CellNoBorder() {
        return CellNoBorder(new PdfPCell());
    }

    public static PdfPCell CellNoBorder(Phrase phrase) {
        return CellNoBorder(new PdfPCell(phrase));
    }

    public static PdfPCell CellCenterText(PdfPCell cell) {
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell CellLeftText(PdfPCell cell) {
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell CellRightText(PdfPCell cell) {
        cell.setVerticalAlignment(Element.ALIGN_RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
