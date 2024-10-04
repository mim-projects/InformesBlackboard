package com.mimsoft.informesblackboard.application.controllers.web.views.register;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.data.repositories.ModalityRepository;
import com.mimsoft.informesblackboard.domain.entities.Modality;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

@Named("modalityCtrl")
@ViewScoped
public class ModalityController extends AbstractSessionController {
    @Inject
    private ModalityRepository modalityRepository;

    @Override
    public void init() {

    }

    public List<Modality> getAllModalities() {
        return modalityRepository.findAll();
    }
}
