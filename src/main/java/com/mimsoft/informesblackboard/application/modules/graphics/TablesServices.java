package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.application.data.repositories.*;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class TablesServices {
    @Inject
    private CampusRepository campusRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private RolesRepository rolesRepository;
    @Inject
    private CoursesRepository coursesRepository;
    @Inject
    private UsersRepository usersRepository;

    public String getCustomPeriodUsers(String period, Grades grades) {
        List<Campus> campusList = campusRepository.findAll();

        String campusHead = "<tr><th></th>";
        for (Campus campus: campusList) campusHead += "<th>" + campus.getName() + "</th>";
        campusHead += "<th></th></tr>";

        String rolesBody= "";
        for (Roles roles: rolesRepository.findAll()) {
            rolesBody += "<tr>";
            rolesBody += "<td>" + roles.getName() + "</td>";
            int rolesTotal = 0;
            for (Campus campus: campusList) {
                int rolesPartial = usersRepository.findTotalByRolesCampusPeriodGrade(roles, campus, period, grades);
                rolesTotal += rolesPartial;
                rolesBody += "<td style='text-align: center'>" + rolesPartial + "</td>";
            }
            rolesBody += "<td style='text-align: center;'>" + rolesTotal + "</td></tr>";
        }

        // SubTotal
        rolesBody += "<tr><td></td>";
        int campusTotal = 0;
        for (Campus campus: campusList) {
            int campusPartial = usersRepository.findTotalByCampusPeriodGrade(campus, period, grades);
            campusTotal += campusPartial;
            rolesBody += "<td style='text-align: center;'>" + campusPartial + "</td>";
        }
        rolesBody += "<td style='text-align: center; background: var(--text-color); color: var(--surface-ground)'>" + campusTotal + "</td></tr>";

        return "<table style='width: 100%; border-spacing: 0'>" +
                "   <thead>" + campusHead + "</thead>" +
                "   <tbody>" + rolesBody + "</tbody>" +
                "</table>";
    }

    public String getCustomPeriodCourses(String period, Grades grades) {
        List<Campus> campusList = campusRepository.findAll();

        String campusHead = "<tr><th></th>";
        for (Campus campus: campusList) campusHead += "<th>" + campus.getName() + "</th>";
        campusHead += "<th></th></tr>";

        String modalityBody= "";
        for (Modality modality: modalityRepository.findAll()) {
            modalityBody += "<tr>";
            modalityBody += "<td>" + modality.getDescription() + " (" + modality.getName() + ")" + "</td>";
            int modalityTotal = 0;
            for (Campus campus: campusList) {
                int modalityPartial = coursesRepository.findTotalByModalityCampusPeriodGrade(modality, campus, period, grades);
                modalityTotal += modalityPartial;
                modalityBody += "<td style='text-align: center'>" + modalityPartial + "</td>";
            }
            modalityBody += "<td style='text-align: center;'>" + modalityTotal + "</td></tr>";
        }

        // SubTotal
        modalityBody += "<tr><td></td>";
        int campusTotal = 0;
        for (Campus campus: campusList) {
            int campusPartial = coursesRepository.findTotalByCampusPeriodGrade(campus, period, grades);
            campusTotal += campusPartial;
            modalityBody += "<td style='text-align: center;'>" + campusPartial + "</td>";
        }
        modalityBody += "<td style='text-align: center; background: var(--text-color); color: var(--surface-ground)'>" + campusTotal + "</td></tr>";

        return "<table style='width: 100%; border-spacing: 0'>" +
                "   <thead>" + campusHead + "</thead>" +
                "   <tbody>" + modalityBody + "</tbody>" +
                "</table>";
    }
}
