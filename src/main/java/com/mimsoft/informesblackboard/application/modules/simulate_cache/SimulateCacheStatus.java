package com.mimsoft.informesblackboard.application.modules.simulate_cache;

public enum SimulateCacheStatus {
    UPDATED(0),
    UPLOADING(1),
    PROCESS(2),
    FAILED(3)
    ;

    private final int value;

    SimulateCacheStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
