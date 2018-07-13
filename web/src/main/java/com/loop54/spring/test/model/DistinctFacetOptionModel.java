package com.loop54.spring.test.model;

import org.springframework.web.bind.annotation.ModelAttribute;

public class DistinctFacetOptionModel {

    public DistinctFacetOptionModel(String name, int count, boolean isSelected){
        this.name = name;
        this.count = count;
        this.isSelected = isSelected;
    }

    private String name;

    @ModelAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ModelAttribute
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    @ModelAttribute
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;
}
