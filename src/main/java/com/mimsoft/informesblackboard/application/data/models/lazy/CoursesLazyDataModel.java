package com.mimsoft.informesblackboard.application.data.models.lazy;

import com.mimsoft.informesblackboard.domain.entities.Courses;
import jakarta.faces.context.FacesContext;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;

import java.util.Collection;
import java.util.List;

import static com.mimsoft.informesblackboard.application.utils.others.StringHelper.CleanStrLowerCase;
import static com.mimsoft.informesblackboard.application.utils.others.StringHelper.CompareStrings;

public class CoursesLazyDataModel extends GenericLazyDataModel<Courses> {
    public CoursesLazyDataModel(List<Courses> datasource) {
        super(datasource);
    }

    @Override
    protected boolean filterItems(FacesContext context, Collection<FilterMeta> filterBy, Courses o) {
        if (filterBy.isEmpty()) return true;
        int match = 0;
        int sizeFilters = 0;

        for (FilterMeta filter : filterBy) {
            sizeFilters++;
            String current = CleanStrLowerCase(filter.getFilterValue());
            switch (filter.getField()) {
                case "id": {
                    if (CleanStrLowerCase(o.getId()).contains(current)) match++;
                    break;
                }
                case "name": {
                    if (CleanStrLowerCase(o.getName()).contains(current)) match++;
                    break;
                }
                case "keyword": {
                    if (CleanStrLowerCase(o.getKeyword()).contains(current)) match++;
                    break;
                }
                case "gradesId.name": {
                    if (CleanStrLowerCase(o.getGradesId().getName()).contains(current)) match++;
                    break;
                }
                case "modalityId.description": {
                    if (CleanStrLowerCase(o.getModalityId().getDescription()).contains(current)) match++;
                    break;
                }
                case "campusCodesId.campusId.name": {
                    if (CleanStrLowerCase(o.getCampusCodesId().getCampusId().getName()).contains(current)) match++;
                    break;
                }
            }
        }
        return match == sizeFilters;
    }

    @Override
    protected int compareItems(Courses item1, Courses item2, String sortField, SortOrder sortOrder) {
        int sort = 0;
        switch (sortField) {
            case "id": {
                if (item1.getId() > item2.getId()) sort = -1;
                else if (item1.getId() < item2.getId()) sort = 1;
                break;
            }
            case "name": {
                sort = CompareStrings(item1.getName(), item2.getName());
                break;
            }
            case "keyword": {
                sort = CompareStrings(item1.getKeyword(), item2.getKeyword());
                break;
            }
            case "gradesId.name": {
                sort = CompareStrings(item1.getGradesId().getName(), item2.getGradesId().getName());
                break;
            }
            case "modalityId.description": {
                sort = CompareStrings(item1.getModalityId().getDescription(), item2.getModalityId().getDescription());
                break;
            }
            case "campusCodesId.campusId.name": {
                sort = CompareStrings(item1.getCampusCodesId().getCampusId().getName(), item2.getCampusCodesId().getCampusId().getName());
                break;
            }
        }
        return SortOrder.ASCENDING.equals(sortOrder) ? sort : -1 * sort;
    }
}
