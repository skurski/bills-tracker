package com.skurski.gwt.demo.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pskurski on 4/6/2016.
 */
public class CategoriesDto {
    private List<CategoryDto> categories = new ArrayList<CategoryDto>();

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
