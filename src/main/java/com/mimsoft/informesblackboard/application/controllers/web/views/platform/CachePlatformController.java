package com.mimsoft.informesblackboard.application.controllers.web.views.platform;

import com.mimsoft.informesblackboard.application.controllers.web.common.AbstractSessionController;
import com.mimsoft.informesblackboard.application.modules.simulate_cache.SimulateCacheServices;
import com.mimsoft.informesblackboard.domain.entities.StaticSimpleDataJSON;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("cachePlatformCtrl")
@ViewScoped
public class CachePlatformController extends AbstractSessionController {
    @Inject
    private SimulateCacheServices simulateCacheServices;

    @Override
    public void init() {

    }

    public List<StaticSimpleDataJSON> getAllCache() {
        return simulateCacheServices.getAllCacheList();
    }

    public void removeAll() {
        for (StaticSimpleDataJSON item: getAllCache()) remove(item.getKeyword());
    }

    public void remove(String keyword) {
        simulateCacheServices.remove(keyword);
    }
}
