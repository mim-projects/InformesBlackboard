package com.mimsoft.informesblackboard.application.modules.reports;

import com.mimsoft.informesblackboard.application.data.interfaces.DataResourceReports;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.DashboardPdfTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.dashboard_spreadsheet.DashboardSpreadsheetTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;

import java.io.IOException;

public class DashboardReport {
    public static void DownloadPDF(DataResourceReports dataResourceReports, String filename, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(dataResourceReports);
        instance.setFilename(filename);
        instance.setPeriodLegend(periodLegend);
        instance.downloadPDF();
    }

    public static void DownloadSpreadsheet(DataResourceReports dataResourceReports, String filename, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(dataResourceReports);
        instance.setFilename(filename);
        instance.setPeriodLegend(periodLegend);
        instance.downloadSpreadSheet();
    }

    private final DataResourceReports dataResourceReports;
    private String filename;
    private String periodLegend;

    public DashboardReport(DataResourceReports dataResourceReports) {
        this.dataResourceReports = dataResourceReports;
        filename = "FILE_" + System.currentTimeMillis();
    }

    public void downloadPDF() throws Exception {
        download(new DashboardPdfTemplate(dataResourceReports, periodLegend), filename + ".pdf");
    }

    public void downloadSpreadSheet() throws Exception {
        download(new DashboardSpreadsheetTemplate(dataResourceReports, periodLegend), filename + ".xlsx");
    }

    private void download(TemplateInstance instance, String exportFilename) throws IOException {
        ResponseHelper.DownloadOutputStream(dataResourceReports.getFacesContext(), exportFilename, instance::render);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPeriodLegend(String periodLegend) {
        this.periodLegend = periodLegend;
    }
}
