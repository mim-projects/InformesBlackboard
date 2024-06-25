package com.mimsoft.informesblackboard.application.modules.upload_execute.services;

import com.mimsoft.informesblackboard.application.data.constants.GradesConstant;
import com.mimsoft.informesblackboard.application.data.repositories.*;
import com.mimsoft.informesblackboard.application.utils.others.StringHelper;
import com.mimsoft.informesblackboard.domain.entities.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RequestScoped
public class ProcessCoursesServices {
    private static final String HEADER = "EXTERNAL_COURSE_KEY|COURSE_ID|COURSE_NAME|AVAILABLE_IND|CATALOG_IND|TEMPLATE_COURSE_KEY";

    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private PeriodsRepository periodsRepository;
    @Inject
    private CampusCodesRepository campusCodesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private CoursesRepository coursesRepository;
    @Inject
    private HistoryRepository historyRepository;
    @Inject
    private CoursesHistoryRepository coursesHistoryRepository;

    private HashMap<Integer, Grades> gradesHashMap;
    private HashMap<String, Modality> modalityHasMap;
    private HashMap<String, CampusCodes> campusCodesHashMap;

    public void load() {
        gradesHashMap = new LinkedHashMap<>();
        modalityHasMap = new LinkedHashMap<>();
        campusCodesHashMap = new LinkedHashMap<>();

        for (Grades item: gradesRepository.findAll()) gradesHashMap.put(item.getId(), item);
        for (Modality item: modalityRepository.findAll()) modalityHasMap.put(item.getName(), item);
        for (CampusCodes item: campusCodesRepository.findAll()) campusCodesHashMap.put(item.getCode(), item);
    }

    public void execute(Integer currentLine, Integer totalLine, String data) throws Exception {
        if (currentLine == 0 || data.contains("***FileFooter")) {
            if (currentLine == 0 && !data.replaceAll(" ", "").equalsIgnoreCase(HEADER)) throw new Exception("Failed Format File");
            return;
        }

        String[] parts = data.trim().split("\\|");
        if (!parts[4].equalsIgnoreCase("y")) return;
        String[] keyword = parts[1].split("_");

        Grades grade = gradesHashMap.get(keyword.length == 10 ? GradesConstant.Posgrado.getValue() : GradesConstant.Licenciatura.getValue());
        Modality modality = modalityHasMap.get(keyword[keyword.length - 2]);
        CampusCodes campus = campusCodesHashMap.get(keyword[1]);

        Periods periods = periodsRepository.findOrCreate(keyword[0]);
        Courses courses = coursesRepository.findOrCreateOrUpdate(
                StringHelper.StripAccents(parts[2].split("\\(")[0]),
                (grade.getId().equals(GradesConstant.Posgrado.getValue())  ? (keyword[keyword.length - 4] + "_") : "") + keyword[keyword.length - 3]
        );

        if (modality == null || campus == null || periods == null || courses == null) throw new Exception("xxxxxxxxxxx");

        History history = new History();
        history.setGradesId(grade);
        history.setPeriodsId(periods);
        history.setCampusCodesId(campus);
        history.setModalityId(modality);

        CoursesHistory coursesHistory = new CoursesHistory();
        coursesHistory.setCoursesId(courses);
        coursesHistory.setHistoryId(historyRepository.findOrCreate(history));
        System.out.println(currentLine + " / " + totalLine);

        coursesHistory = coursesHistoryRepository.findOrCreate(coursesHistory);
        if (coursesHistory == null) throw new Exception("lllllllllllllll");
        System.out.println(coursesHistory);
    }
}
