package com.mimsoft.boilerplate.application.data.models.lazy;

import com.mimsoft.boilerplate.domain.core.EntityCore;
import jakarta.faces.context.FacesContext;
import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class GenericLazyDataModel<T extends EntityCore> extends LazyDataModel<T> implements Serializable {
    protected abstract boolean filterItems(FacesContext context, Collection<FilterMeta> filterBy, T o);
    protected abstract int compareItems(T item1, T item2, String sortField, SortOrder sortOrder);
    private final List<T> datasource;

    public GenericLazyDataModel(List<T> datasource) {
        this.datasource = datasource;
    }

    @Override
    public String getRowKey(T object) {
        return String.valueOf(object.getId());
    }

    @Override
    public T getRowData(String rowKey) {
        for (T customer : datasource) {
            if (customer.getId() == Integer.parseInt(rowKey)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        return 0;
    }

    @Override
    public List<T> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        List<T> data = datasource.stream()
                .filter(o -> filterItems(FacesContext.getCurrentInstance(), map1.values(), o))
                .collect(Collectors.toList());
        int rowCount = data.size();
        setRowCount(rowCount);

        if (rowCount > 0 && i >= rowCount) {
            int numberOfPages = (int) Math.ceil(rowCount * 1d / i1);
            i = Math.max((numberOfPages - 1) * i1, 0);
        }

        List<T> list = data.stream().skip(i).limit(i1).collect(Collectors.toList());

        if (!map.isEmpty()) {
            List<Comparator<T>> comparators = map.values().stream()
                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<T> cp = ComparatorUtils.chainedComparator(comparators);
            list.sort(cp);
        }

        return list;
    }

    private class LazySorter implements Comparator<T> {
        private final String sortField;
        private final SortOrder sortOrder;

        public LazySorter(String sortField, SortOrder sortOrder) {
            this.sortField = sortField;
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(T customer1, T customer2) {
            return compareItems(customer1, customer2, sortField, sortOrder);
        }

    }
}
