package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Modality;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@RequestScoped
public class ModalityRepository {
    @Inject
    @RepositoryClass(Modality.class)
    private Repository<Modality> repository;

    public List<Modality> findAll() {
        List<Modality> list = repository.findAll();
        Collections.reverse(list);
        return list;
    }
}
