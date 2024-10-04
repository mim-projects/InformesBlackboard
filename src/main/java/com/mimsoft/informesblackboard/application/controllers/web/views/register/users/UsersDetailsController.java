package com.mimsoft.informesblackboard.application.controllers.web.views.register.users;

import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.models.lazy.CustomUserLazyDataModel;
import com.mimsoft.informesblackboard.application.data.queries.custom_periods.CustomPeriodsRepository;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsersHelper;
import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsersRepository;
import com.mimsoft.informesblackboard.application.data.repositories.*;
import com.mimsoft.informesblackboard.domain.entities.*;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named("usersDetailsCtrl")
@ViewScoped
public class UsersDetailsController extends AbstractSessionController {
    @Inject
    private UsersRepository usersRepository;
    @Inject
    private RequestController requestController;
    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private CampusRepository campusRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private RolesRepository rolesRepository;
    @Inject
    private CustomPeriodsRepository customPeriodsRepository;
    @Inject
    private CustomUsersRepository customUsersRepository;
    @Inject
    private CoursesRepository coursesRepository;

    private Users currentUser;
    private LazyDataModel<CustomUsers> customUsersLazyDataModel;

    @Override
    public void init() {
        String userId = requestController.getParam("id");
        try { currentUser = usersRepository.findById(userId); }
        catch (Exception e) { currentUser = null; }
        if (currentUser == null) return;

        Set<CustomUsers> result = new HashSet<>();
        List<CustomUsers> list = currentUser.getKeyword().isEmpty()
                ? customUsersRepository.findAllByUserIfKeywordIsNull(currentUser)
                : customUsersRepository.findAllByUser(currentUser);
        for (CustomUsers item: list) {
            for (String current: item.getListCoursesKeyword()) {
                Courses courses = coursesRepository.findByKeyword(current);
                if (courses != null) current += ". " + courses.getName();
                result.add(CustomUsersHelper.copy(item, current));
            }
        }
        customUsersLazyDataModel = new CustomUserLazyDataModel(new ArrayList<>(result));
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }

    public List<Modality> getAllModalities() {
        return modalityRepository.findAll();
    }

    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    public List<String> getAllPeriodsString() {
        return customPeriodsRepository.findAllString();
    }

    public LazyDataModel<CustomUsers> getCustomUsersLazyDataModel() {
        return customUsersLazyDataModel;
    }

    public void setCustomUsersLazyDataModel(LazyDataModel<CustomUsers> customUsersLazyDataModel) {
        this.customUsersLazyDataModel = customUsersLazyDataModel;
    }
}
