package com.loop54.spring.test.model;

import org.springframework.web.bind.annotation.ModelAttribute;

public class DistinctFacetModel {
    @ModelAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ModelAttribute
    public DistinctFacetOptionModel[] getOptions() {
        return options;
    }

    public void setOptions(DistinctFacetOptionModel[] options) {
        this.options = options;
    }

    private String name;
    private DistinctFacetOptionModel[] options;
}
