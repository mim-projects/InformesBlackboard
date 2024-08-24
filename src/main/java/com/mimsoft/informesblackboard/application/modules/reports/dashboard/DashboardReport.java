package com.mimsoft.informesblackboard.application.modules.reports.dashboard;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.mimsoft.informesblackboard.application.data.interfaces.BundleLanguage;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;
import jakarta.faces.context.FacesContext;

public class DashboardReport {
    public static void Download(GraphicTableCoursesUsersHelper data, FacesContext context, String filename, BundleLanguage bundleLanguage) throws Exception {
        DashboardReport instance = new DashboardReport(data, bundleLanguage);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.download();
    }

    private final BundleLanguage bundleLanguage;
    private final GraphicTableCoursesUsersHelper data;
    private FacesContext facesContext;
    private String filename;

    public DashboardReport(GraphicTableCoursesUsersHelper data, BundleLanguage bundleLanguage) {
        this.data = data;
        this.bundleLanguage = bundleLanguage;
        facesContext = FacesContext.getCurrentInstance();
        filename = "FILE_" + System.currentTimeMillis();
    }

    public void download() throws Exception {
        String exportFilename = filename + ".pdf";

        ResponseHelper.DownloadOutputStream(facesContext, exportFilename, outputStream -> {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Template.Instance(document, data, bundleLanguage).render();
            document.close();
        });
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
