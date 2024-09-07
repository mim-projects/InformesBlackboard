package com.mimsoft.informesblackboard.application.modules.reports;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.DashboardPdfTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.dashboard_spreadsheet.DashboardSpreadsheetTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;
import jakarta.faces.context.FacesContext;

import java.io.IOException;

public class DashboardReport {
    public static void DownloadPDF(RequestController requestController, FacesContext context, BundleLanguage bundleLanguage, GraphicTableCoursesUsersHelper data, String filename, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(data, bundleLanguage);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.setRequestController(requestController);
        instance.setPeriodLegend(periodLegend);
        instance.downloadPDF();
    }

    public static void DownloadSpreadsheet(GraphicTableCoursesUsersHelper data, FacesContext context, String filename, BundleLanguage bundleLanguage, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(data, bundleLanguage);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.setPeriodLegend(periodLegend);
        instance.downloadSpreadSheet();
    }

    private final BundleLanguage bundleLanguage;
    private final GraphicTableCoursesUsersHelper data;
    private RequestController requestController;
    private FacesContext facesContext;
    private String filename;
    private String periodLegend;

    public DashboardReport(GraphicTableCoursesUsersHelper data, BundleLanguage bundleLanguage) {
        this.data = data;
        this.bundleLanguage = bundleLanguage;
        facesContext = FacesContext.getCurrentInstance();
        filename = "FILE_" + System.currentTimeMillis();
    }

    public void downloadPDF() throws Exception {
        download(new DashboardPdfTemplate(requestController, data, bundleLanguage, periodLegend), filename + ".pdf");
    }

    public void downloadSpreadSheet() throws Exception {
        download(new DashboardSpreadsheetTemplate(requestController, data, bundleLanguage, periodLegend), filename + ".xlsx");
    }

    private void download(TemplateInstance instance, String exportFilename) throws IOException {
        ResponseHelper.DownloadOutputStream(facesContext, exportFilename, instance::render);
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setRequestController(RequestController requestController) {
        this.requestController = requestController;
    }

    public void setPeriodLegend(String periodLegend) {
        this.periodLegend = periodLegend;
    }
}
