package com.mimsoft.informesblackboard.application.modules.upload_execute.services;

import com.mimsoft.informesblackboard.application.data.constants.GradesConstant;
import com.mimsoft.informesblackboard.application.data.constants.RolesConstant;
import com.mimsoft.informesblackboard.application.data.repositories.*;
import com.mimsoft.informesblackboard.domain.entities.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RequestScoped
public class ProcessUsersServices {
    private static final String HEADER = "EXTERNAL_COURSE_KEY|EXTERNAL_PERSON_KEY|ROLE|AVAILABLE_IND";

    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private CampusCodesRepository campusCodesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private RolesRepository rolesRepository;
    @Inject
    private UsersRepository usersRepository;

    private HashMap<Integer, Grades> gradesHashMap;
    private HashMap<String, Modality> modalityHasMap;
    private HashMap<String, CampusCodes> campusCodesHashMap;
    private HashMap<Integer, Roles> rolesHashMap;

    public void load() {
        gradesHashMap = new LinkedHashMap<>();
        modalityHasMap = new LinkedHashMap<>();
        campusCodesHashMap = new LinkedHashMap<>();
        rolesHashMap = new LinkedHashMap<>();

        for (Grades item: gradesRepository.findAll()) gradesHashMap.put(item.getId(), item);
        for (Modality item: modalityRepository.findAll()) modalityHasMap.put(item.getName(), item);
        for (CampusCodes item: campusCodesRepository.findAll()) campusCodesHashMap.put(item.getCode(), item);
        for (Roles item: rolesRepository.findAll()) rolesHashMap.put(item.getId(), item);
    }

    public void execute(Integer currentLine, Integer totalLine, String data) throws Exception {
        if (currentLine == 0 || data.contains("***FileFooter")) {
            if (currentLine == 0 && !data.replaceAll(" ", "").equalsIgnoreCase(HEADER)) throw new Exception("Failed Format File");
            return;
        }

        String[] parts = data.trim().split("\\|");
        if (!parts[3].equalsIgnoreCase("y")) return;
        String[] keyword = parts[0].split("_");
        Grades grades = gradesHashMap.get(keyword.length == 10 ? GradesConstant.Posgrado.getValue() : GradesConstant.Licenciatura.getValue());

        Users users = new Users();
        users.setKeyword(parts[1]);
        users.setRolesId(rolesHashMap.get(parts[2].equalsIgnoreCase("student") ? RolesConstant.Estudiantes.getValue() : RolesConstant.Docentes.getValue()));
        users.setPeriods(Integer.parseInt(keyword[0]));
        users.setKeywordCourse((grades.getId().equals(GradesConstant.Posgrado.getValue())  ? (keyword[keyword.length - 4] + "_") : "") + keyword[keyword.length - 3]);
        users.setGradesId(grades);
        users.setCampusCodesId(campusCodesHashMap.get(keyword[1]));
        users.setModalityId(modalityHasMap.get(keyword[keyword.length - 2]));
        usersRepository.createIgnore(users);
    }
}
