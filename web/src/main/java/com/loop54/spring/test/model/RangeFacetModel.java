package com.loop54.spring.test.model;

import org.springframework.web.bind.annotation.ModelAttribute;

public class RangeFacetModel {
    private String name;
    private String min;
    private String max;

    @ModelAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ModelAttribute
    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    @ModelAttribute
    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
