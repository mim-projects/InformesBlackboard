package com.mimsoft.informesblackboard.application.modules.graphics;

import com.mimsoft.informesblackboard.application.data.repositories.CampusRepository;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.application.data.repositories.RolesRepository;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import com.mimsoft.informesblackboard.domain.entities.Roles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.*;

@RequestScoped
public class OrderDataServices {
    @Inject
    private RolesRepository rolesRepository;
    @Inject
    private ModalityRepository modalityRepository;
    @Inject
    private CampusRepository campusRepository;

    public List<Integer> orderValueForRow(List<String> unOrderRows, Integer[] rowValues) {
        HashMap<String, Integer> list = new LinkedHashMap<>();

        for (Roles role : rolesRepository.findAll()) {
            for (int i = 0; i < unOrderRows.size(); i++) {
                if (unOrderRows.get(i).contains(role.getName())) {
                    list.put(role.getName(), rowValues[i]);
                    break;
                }
            }
        }

        if (list.isEmpty()) {
            for (Modality modality : modalityRepository.findAll()) {
                for (int i = 0; i < unOrderRows.size(); i++) {
                    if (unOrderRows.get(i).contains(modality.getName())) {
                        list.put(modality.getName(), rowValues[i]);
                        break;
                    }
                }
            }
        }

        return new ArrayList<>(list.values());
    }

    public List<String> orderRow(List<String> data) {
        Set<String> list = new LinkedHashSet<>();

        for (Roles role : rolesRepository.findAll()) {
            for (String current: data) {
                if (current.contains(role.getName())) {
                    list.add(current);
                    break;
                }
            }
        }

        if (list.isEmpty()) {
            for (Modality modality : modalityRepository.findAll()) {
                for (String current: data) {
                    if (current.contains(modality.getName())) {
                        list.add(current);
                        break;
                    }
                }
            }
        }

        return new ArrayList<>(list);
    }

    public List<String> orderColumn(List<String> data) {
        Set<String> list = new LinkedHashSet<>();

        for (Campus campus : campusRepository.findAll()) {
            for (String current: data) {
                if (current.contains(campus.getName())) {
                    list.add(current);
                    break;
                }
            }
        }

        return new ArrayList<>(list);
    }
}
