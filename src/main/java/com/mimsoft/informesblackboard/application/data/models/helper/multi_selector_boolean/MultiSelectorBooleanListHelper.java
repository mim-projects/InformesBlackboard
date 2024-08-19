package com.mimsoft.informesblackboard.application.data.models.helper.multi_selector_boolean;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectorBooleanListHelper<T> {
    private final List<MultiSelectorBooleanHelper<T>> list;

    public MultiSelectorBooleanListHelper() {
        list = new ArrayList<>();
    }

    public void add(T model) {
        add(model, false);
    }

    public void add(T model, boolean value) {
        list.add(new MultiSelectorBooleanHelper<>(model, value));
    }

    public boolean containTrue() {
        for (MultiSelectorBooleanHelper<T> current: list) {
            if (current.isValue()) return true;
        }
        return false;
    }

    public List<MultiSelectorBooleanHelper<T>> getList() {
        return list;
    }
}
