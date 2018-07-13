package com.loop54.spring.test.model;

import com.loop54.spring.test.model.DistinctFacetModel;
import com.loop54.spring.test.model.EntityModel;
import com.loop54.spring.test.model.RangeFacetModel;
import org.springframework.web.bind.annotation.ModelAttribute;

public class SearchResponseModel {

    private int count;

    @ModelAttribute
    public int getRelatedCount() {
        return relatedCount;
    }

    public void setRelatedCount(int relatedCount) {
        this.relatedCount = relatedCount;
    }

    private int relatedCount;

    private boolean makesSense;

    private String[] spellingSuggestions;

    private String[] relatedQueries;

    private String query;

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

    @ModelAttribute
    public EntityModel[] getRelatedResults() {
        return relatedResults;
    }

    public void setRelatedResults(EntityModel[] relatedResults) {
        this.relatedResults = relatedResults;
    }

    private EntityModel[] results;

    private EntityModel[] relatedResults;

    @ModelAttribute
    public String[] getRelatedQueries() {
        return relatedQueries;
    }

    public void setRelatedQueries(String[] relatedQueries) {
        this.relatedQueries = relatedQueries;
    }

    @ModelAttribute
    public String[] getSpellingSuggestions() {
        return spellingSuggestions;
    }

    public void setSpellingSuggestions(String[] spellingSuggestions) {
        this.spellingSuggestions = spellingSuggestions;
    }

    @ModelAttribute
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @ModelAttribute
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ModelAttribute
    public boolean isMakesSense() {
        return makesSense;
    }

    public void setMakesSense(boolean makesSense) {
        this.makesSense = makesSense;
    }
}
