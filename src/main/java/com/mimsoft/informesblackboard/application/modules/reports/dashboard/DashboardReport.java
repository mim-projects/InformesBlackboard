package com.mimsoft.informesblackboard.application.modules.reports.dashboard;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.utils.http.ResponseHelper;
import jakarta.faces.context.FacesContext;

public class DashboardReport {
    public static void Download(GraphicTableCoursesUsersHelper data, FacesContext context, String filename) throws Exception {
        DashboardReport instance = new DashboardReport(data);
        instance.setFacesContext(context);
        instance.setFilename(filename);
        instance.download();
    }

    private final GraphicTableCoursesUsersHelper data;
    private FacesContext facesContext;
    private String filename;

    public DashboardReport(GraphicTableCoursesUsersHelper data) {
        this.data = data;
        facesContext = FacesContext.getCurrentInstance();
        filename = "FILE_" + System.currentTimeMillis();
    }

    public void download() throws Exception {
        String exportFilename = filename + ".pdf";

        ResponseHelper.DownloadOutputStream(facesContext, exportFilename, outputStream -> {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Template.Instance(document).render();
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
