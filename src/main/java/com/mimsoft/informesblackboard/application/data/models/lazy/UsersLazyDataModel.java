package com.mimsoft.informesblackboard.application.data.models.lazy;

import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import jakarta.faces.context.FacesContext;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;

import java.util.Collection;
import java.util.List;

public class UsersLazyDataModel extends GenericLazyDataModel<CustomUsers> {
    public UsersLazyDataModel(List<CustomUsers> datasource) {
        super(datasource);
    }

    @Override
    protected boolean filterItems(FacesContext context, Collection<FilterMeta> filterBy, CustomUsers o) {
        return false;
    }

    @Override
    protected int compareItems(CustomUsers item1, CustomUsers item2, String sortField, SortOrder sortOrder) {
        return 0;
    }
}
