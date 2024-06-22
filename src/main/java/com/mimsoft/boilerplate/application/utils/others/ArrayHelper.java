package com.mimsoft.boilerplate.application.utils.others;

import java.util.ArrayList;
import java.util.List;

public class ArrayHelper<T> {
    public boolean contains(T item, T[] list) {
        for (T current: list) if (current.equals(item)) return true;
        return false;
    }

    public boolean contains(T[] items, T[] list) {
        for (T current: items) {
            if (contains(current, list)) return true;
        }
        return false;
    }

    public boolean equals(T item, T[] list) {
        if (list.length == 0 || item == null) return false;
        for (T current: list) if (!current.equals(item)) return false;
        return true;
    }

    public List<Integer> arrayInteger(int start, int end) {
        if (start > end) end += start;
        if (start == end) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int k = start; k <= end; k++) list.add(k);
        return list;
    }

    public interface IFilter<T> {
        boolean onFilter(T data);
    }
    public List<T> filter(List<T> data, IFilter<T> filter) {
        List<T> list = new ArrayList<>();
        for (T item: data) if (filter.onFilter(item)) list.add(item);
        return list;
    }
}
