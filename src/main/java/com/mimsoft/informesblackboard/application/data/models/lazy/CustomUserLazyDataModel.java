package com.mimsoft.informesblackboard.application.data.models.lazy;

import com.mimsoft.informesblackboard.application.data.queries.custom_users.CustomUsers;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;

import javax.faces.context.FacesContext;
import java.util.Collection;
import java.util.List;

import static com.mimsoft.informesblackboard.application.utils.others.StringHelper.CleanStrLowerCase;
import static com.mimsoft.informesblackboard.application.utils.others.StringHelper.CompareStrings;

public class CustomUserLazyDataModel extends GenericLazyDataModel<CustomUsers> {
    public CustomUserLazyDataModel(List<CustomUsers> datasource) {
        super(datasource);
    }

    @Override
    protected boolean filterItems(FacesContext context, Collection<FilterMeta> filterBy, CustomUsers o) {
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
                case "userKeyword": {
                    if (CleanStrLowerCase(o.getUserKeyword()).contains(current)) match++;
                    break;
                }
                case "periods": {
                    if (CleanStrLowerCase(o.getPeriod()).equalsIgnoreCase(current)) match++;
                    break;
                }
                case "role": {
                    if (CleanStrLowerCase(o.getRole()).equalsIgnoreCase(current)) match++;
                    break;
                }
                case "modality": {
                    if (CleanStrLowerCase(o.getModality()).equalsIgnoreCase(current)) match++;
                    break;
                }
                case "campus": {
                    if (CleanStrLowerCase(o.getCampus()).equalsIgnoreCase(current)) match++;
                    break;
                }
                case "grade": {
                    if (CleanStrLowerCase(o.getGrade()).equalsIgnoreCase(current)) match++;
                    break;
                }
                case "coursesKeyword": {
                    if (CleanStrLowerCase(o.getCoursesKeyword()).contains(current)) match++;
                    break;
                }
            }
        }
        return match == sizeFilters;
    }

    @Override
    protected int compareItems(CustomUsers item1, CustomUsers item2, String sortField, SortOrder sortOrder) {
        int sort = 0;
        switch (sortField) {
            case "id": {
                if (item1.getId() > item2.getId()) sort = -1;
                else if (item1.getId() < item2.getId()) sort = 1;
                break;
            }
            case "userKeyword": {
                sort = CompareStrings(item1.getUserKeyword(), item2.getUserKeyword());
                break;
            }
            case "periods": {
                sort = CompareStrings(item1.getPeriod(), item2.getPeriod());
                break;
            }
            case "role": {
                sort = CompareStrings(item1.getRole(), item2.getRole());
                break;
            }
            case "modality": {
                sort = CompareStrings(item1.getModality(), item2.getModality());
                break;
            }
            case "campus": {
                sort = CompareStrings(item1.getCampus(), item2.getCampus());
                break;
            }
            case "grade": {
                sort = CompareStrings(item1.getGrade(), item2.getGrade());
                break;
            }
            case "coursesKeyword": {
                sort = CompareStrings(item1.getCoursesKeyword(), item2.getCoursesKeyword());
                break;
            }
        }
        return SortOrder.ASCENDING.equals(sortOrder) ? sort : -1 * sort;
    }
}
