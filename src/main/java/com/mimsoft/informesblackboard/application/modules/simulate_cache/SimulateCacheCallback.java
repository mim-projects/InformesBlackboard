package com.mimsoft.informesblackboard.application.modules.simulate_cache;

import java.util.List;

public interface SimulateCacheCallback<T> {
    List<T> execute();
}
