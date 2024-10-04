package com.mimsoft.informesblackboard.application.data.interfaces;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.data.models.helper.graphic_table_courses_users.GraphicTableCoursesUsersHelper;
import com.mimsoft.informesblackboard.application.modules.graphics.OrderDataServices;
import com.mimsoft.informesblackboard.domain.entities.StorageHistory;
import javax.faces.context.FacesContext;

import java.util.List;

public interface DataResourceReports {
    RequestController getRequestController();
    FacesContext getFacesContext();
    BundleLanguage getBundleLanguage();
    OrderDataServices getOrderDataServices();
    GraphicTableCoursesUsersHelper getGraphicTableCoursesUsersHelper();
    List<StorageHistory> getAllStorageHistory();
}