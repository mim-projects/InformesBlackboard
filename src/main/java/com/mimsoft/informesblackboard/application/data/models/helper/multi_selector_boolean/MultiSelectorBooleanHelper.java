package com.mimsoft.informesblackboard.application.data.models.helper.multi_selector_boolean;

public class MultiSelectorBooleanHelper<T> {
    private T model;
    private boolean value;

    public MultiSelectorBooleanHelper(T model, boolean value) {
        this.model = model;
        this.value = value;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
