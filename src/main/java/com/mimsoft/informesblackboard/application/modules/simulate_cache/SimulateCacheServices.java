package com.mimsoft.informesblackboard.application.modules.simulate_cache;

import com.google.gson.Gson;
import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.data.repositories.StaticSimpleDataJSONRepository;
import com.mimsoft.informesblackboard.application.utils.others.DateHelper;
import com.mimsoft.informesblackboard.domain.entities.StaticSimpleDataJSON;
import jakarta.ejb.*;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionManagement(TransactionManagementType.BEAN)
public class SimulateCacheServices {
    @Inject
    private StaticSimpleDataJSONRepository staticSimpleDataJSONRepository;

    public List<StaticSimpleDataJSON> getAllCacheList() {
        return staticSimpleDataJSONRepository.getAll();
    }

    @Lock(LockType.READ)
    public String get(String keyword) {
        StaticSimpleDataJSON item = staticSimpleDataJSONRepository.get(keyword);
        if (item == null) return null;
        if (item.getStatus().equals(SimulateCacheStatus.FAILED.getValue())) return null;
        if ((item.getStatus().equals(SimulateCacheStatus.UPLOADING.getValue()) ||
                item.getStatus().equals(SimulateCacheStatus.PROCESS.getValue())) &&
                DateHelper.DiffSeconds(new Date(), item.getUpdatedAt()) > Configuration.DURATION_MAX_UPDATE_SIMULATE_CACHE) {
            return null;
        }
        return item.getValue();
    }

    @Lock(LockType.WRITE)
    public void put(String keyword, Object object) {
        update(keyword, object, SimulateCacheStatus.UPDATED);
    }

    @Lock(LockType.WRITE)
    public void status(String keyword, SimulateCacheStatus status) {
        staticSimpleDataJSONRepository.status(keyword, status.getValue());
    }

    @Lock(LockType.WRITE)
    public void update(String keyword, Object object, SimulateCacheStatus status) {
        String json = new Gson().toJson(object);
        staticSimpleDataJSONRepository.put(keyword, json, status.getValue());
    }

    @Lock(LockType.WRITE)
    public void remove(String keyword) {
        staticSimpleDataJSONRepository.remove(keyword);
    }
}
