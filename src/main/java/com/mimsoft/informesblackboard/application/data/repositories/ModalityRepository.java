package com.mimsoft.informesblackboard.application.data.repositories;

import com.mimsoft.informesblackboard.domain.core.Repository;
import com.mimsoft.informesblackboard.domain.core.RepositoryClass;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class ModalityRepository {
    @Inject
    @RepositoryClass(Modality.class)
    private Repository<Modality> repository;

    public List<Modality> findAll() {
        return repository.findAll();
    }
}
