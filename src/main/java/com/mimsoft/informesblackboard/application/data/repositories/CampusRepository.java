package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Campus;
import java.util.Collections;
import java.util.Comparator;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.List;

@RequestScoped
public class CampusRepository {
    @Inject
    @RepositoryClass(Campus.class)
    private Repository<Campus> repository;

    public List<Campus> findAll() {
        return repository.findAll();
    }

    public void update(Campus item) {
        repository.update(item);
    }

    public Campus findById(Integer id) {
        return repository.findId(id);
    }
    
    
    // Método que retorna una lista ordenada
    public List<Campus> findAllSorted(String sortField, boolean ascending) {
        List<Campus> campusList = repository.findAll();

        // Realiza la ordenación según el campo especificado
        if (sortField != null) {
            Collections.sort(campusList, new Comparator<Campus>() {
                @Override
                public int compare(Campus o1, Campus o2) {
                    // Implementa la lógica de comparación según el campo
                    if ("name".equals(sortField)) {
                        return ascending ? o1.getName().compareTo(o2.getName()) : o2.getName().compareTo(o1.getName());
                    } else if ("description".equals(sortField)) {
                        return ascending ? o1.getDescription().compareTo(o2.getDescription()) : o2.getDescription().compareTo(o1.getDescription());
                    }
                    return 0;
                }
            });
        }

        return campusList;
    }
}
