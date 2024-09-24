package com.mimsoft.informesblackboard.application.modules.reports;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.graphics.OrderDataServices;
import com.mimsoft.informesblackboard.application.modules.reports.dashboad_pdf.DashboardPdfTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.dashboard_spreadsheet.DashboardSpreadsheetTemplate;
import com.mimsoft.informesblackboard.application.modules.reports.utils.TemplateInstance;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.OrderBy;

import java.io.IOException;

public class DashboardReport {
    public static void DownloadPDF(RequestController requestController, FacesContext context, BundleLanguage bundleLanguage, OrderDataServices orderDataServices, GraphicTableCoursesUsersHelper data, String filename, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(data, bundleLanguage, orderDataServices);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.setRequestController(requestController);
        instance.setPeriodLegend(periodLegend);
        instance.downloadPDF();
    }

    public static void DownloadSpreadsheet(GraphicTableCoursesUsersHelper data, FacesContext context, String filename, BundleLanguage bundleLanguage, OrderDataServices orderDataServices, String periodLegend) throws Exception {
        DashboardReport instance = new DashboardReport(data, bundleLanguage, orderDataServices);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.setPeriodLegend(periodLegend);
        instance.downloadSpreadSheet();
    }

    private final BundleLanguage bundleLanguage;
    private final GraphicTableCoursesUsersHelper data;
    private final OrderDataServices orderDataServices;
    private RequestController requestController;
    private FacesContext facesContext;
    private String filename;
    private String periodLegend;

    public DashboardReport(GraphicTableCoursesUsersHelper data, BundleLanguage bundleLanguage, OrderDataServices orderDataServices) {
        this.data = data;
        this.bundleLanguage = bundleLanguage;
        this.orderDataServices = orderDataServices;
        facesContext = FacesContext.getCurrentInstance();
        filename = "FILE_" + System.currentTimeMillis();
    }

    public void downloadPDF() throws Exception {
        download(new DashboardPdfTemplate(requestController, data, bundleLanguage, orderDataServices, periodLegend), filename + ".pdf");
    }

    public void downloadSpreadSheet() throws Exception {
        download(new DashboardSpreadsheetTemplate(requestController, data, bundleLanguage, orderDataServices, periodLegend), filename + ".xlsx");
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
