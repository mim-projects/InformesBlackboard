package com.mimsoft.informesblackboard.application.modules.upload_execute.services;

import com.mimsoft.informesblackboard.application.data.constants.GradesConstant;
import com.mimsoft.informesblackboard.application.data.repositories.CampusCodesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.GradesRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheKeywords;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheStaticData;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheStatus;
import com.mimsoft.informesblackboard.application.modules.upload_execute.interfaces.CallbackEntity;
import com.mimsoft.informesblackboard.application.utils.others.StringHelper;
import com.mimsoft.informesblackboard.domain.entities.CampusCodes;
import com.mimsoft.informesblackboard.domain.entities.Courses;
import com.mimsoft.informesblackboard.domain.entities.Grades;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RequestScoped
public class ProcessCoursesServices {
    private static final String HEADER = "EXTERNAL_COURSE_KEY|COURSE_ID|COURSE_NAME|AVAILABLE_IND|CATALOG_IND|TEMPLATE_COURSE_KEY";

    @Inject
    private GradesRepository gradesRepository;
    @Inject
    private CampusCodesRepository campusCodesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private SimulateCacheServices simulateCacheServices;

    private HashMap<Integer, Grades> gradesHashMap;
    private HashMap<String, Modality> modalityHasMap;
    private HashMap<String, CampusCodes> campusCodesHashMap;
    private Date date;

    public void load() {
        date = new Date();
        gradesHashMap = new LinkedHashMap<>();
        modalityHasMap = new LinkedHashMap<>();
        campusCodesHashMap = new LinkedHashMap<>();

        for (Grades item: gradesRepository.findAll()) gradesHashMap.put(item.getId(), item);
        for (Modality item: modalityRepository.findAll()) modalityHasMap.put(item.getName(), item);
        for (CampusCodes item: campusCodesRepository.findAll()) campusCodesHashMap.put(item.getCode(), item);
    }

    public void execute(Integer currentLine, Integer totalLine, String data, CallbackEntity<Courses> callback) throws Exception {
        if (currentLine == 0 || data.contains("***FileFooter")) {
            if (currentLine == 0 && !data.replaceAll(" ", "").equalsIgnoreCase(HEADER)) throw new Exception("Failed Format File");
            return;
        }

        String[] parts = data.trim().split("\\|");
        String[] keyword = parts[1].split("_");
        Grades grades = gradesHashMap.get(keyword.length == 10 ? GradesConstant.Posgrado.getValue() : GradesConstant.Licenciatura.getValue());

        Courses courses = new Courses();
        courses.setKeyword((grades.getId().equals(GradesConstant.Posgrado.getValue())  ? (keyword[keyword.length - 4] + "_") : "") + keyword[keyword.length - 3]);
        courses.setName(StringHelper.StripAccents(parts[2].split("\\(")[0]));
        courses.setPeriods(Integer.parseInt(keyword[0]));
        courses.setModalityId(modalityHasMap.get(keyword[keyword.length - 2]));
        courses.setCampusCodesId(campusCodesHashMap.get(keyword[1]));
        courses.setGradesId(grades);
        courses.setHashCode(parts[0]);
        courses.setDatedAt(date);
        callback.callback(courses);

        // ====================================================================
        if (currentLine == 1) {
            simulateCacheServices.status(SimulateCacheStaticData.CreateKeywordCourses(courses), SimulateCacheStatus.PROCESS);
        } else if (currentLine == totalLine - 1) {
            simulateCacheServices.status(SimulateCacheStaticData.CreateKeywordCourses(courses), SimulateCacheStatus.UPDATED);
        }
    }
}
