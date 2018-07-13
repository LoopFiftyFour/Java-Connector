package com.loop54.spring.test.model;

import com.loop54.spring.test.model.DistinctFacetModel;
import com.loop54.spring.test.model.EntityModel;
import com.loop54.spring.test.model.RangeFacetModel;
import org.springframework.web.bind.annotation.ModelAttribute;

public class GetEntitiesResponseModel {

    private int count;

    @ModelAttribute
    public DistinctFacetModel[] getDistinctFacets() {
        return distinctFacets;
    }

    public void setDistinctFacets(DistinctFacetModel[] distinctFacets) {
        this.distinctFacets = distinctFacets;
    }

    private DistinctFacetModel[] distinctFacets;

    @ModelAttribute
    public RangeFacetModel[] getRangeFacets() {
        return rangeFacets;
    }

    public void setRangeFacets(RangeFacetModel[] rangeFacets) {
        this.rangeFacets = rangeFacets;
    }

    private RangeFacetModel[] rangeFacets;

    @ModelAttribute
    public EntityModel[] getResults() {
        return results;
    }

    public void setResults(EntityModel[] results) {
        this.results = results;
    }

    private EntityModel[] results;

    @ModelAttribute
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
