package com.loop54.spring.test.model;

import org.springframework.web.bind.annotation.ModelAttribute;

public class SuggestResponseModel {

    private String query;
    private String originalQuery;
    private String queryUsedForSearch;
    private boolean makesSense;

    private int queryCount;
    private String[] queries;
    private String scopedQuery;
    private String scopeAttribute;
    private String[] scoped;

    private int productCount;
    private EntityModel[] results;
    private DistinctFacetModel[] distinctFacets;
    private RangeFacetModel[] rangeFacets;

    @ModelAttribute
    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    @ModelAttribute
    public String getOriginalQuery() {
        return originalQuery;
    }

    public void setOriginalQuery(final String originalQuery) {
        this.originalQuery = originalQuery;
    }

    @ModelAttribute
    public String getQueryUsedForSearch() {
        return queryUsedForSearch;
    }

    public void setQueryUsedForSearch(final String queryUsedForSearch) {
        this.queryUsedForSearch = queryUsedForSearch;
    }

    @ModelAttribute
    public boolean isMakesSense() {
        return makesSense;
    }

    public void setMakesSense(final boolean makesSense) {
        this.makesSense = makesSense;
    }

    @ModelAttribute
    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(final int queryCount) {
        this.queryCount = queryCount;
    }

    @ModelAttribute
    public String[] getQueries() {
        return queries;
    }

    public void setQueries(final String[] queries) {
        this.queries = queries;
    }

    @ModelAttribute
    public String getScopedQuery() {
        return scopedQuery;
    }

    public void setScopedQuery(final String scopedQuery) {
        this.scopedQuery = scopedQuery;
    }

    @ModelAttribute
    public String getScopeAttribute() {
        return scopeAttribute;
    }

    public void setScopeAttribute(final String scopeAttribute) {
        this.scopeAttribute = scopeAttribute;
    }

    @ModelAttribute
    public String[] getScoped() {
        return scoped;
    }

    public void setScoped(final String[] scoped) {
        this.scoped = scoped;
    }

    @ModelAttribute
    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(final int productCount) {
        this.productCount = productCount;
    }

    @ModelAttribute
    public EntityModel[] getResults() {
        return results;
    }

    public void setResults(final EntityModel[] results) {
        this.results = results;
    }

    @ModelAttribute
    public DistinctFacetModel[] getDistinctFacets() {
        return distinctFacets;
    }

    public void setDistinctFacets(final DistinctFacetModel[] distinctFacets) {
        this.distinctFacets = distinctFacets;
    }

    @ModelAttribute
    public RangeFacetModel[] getRangeFacets() {
        return rangeFacets;
    }

    public void setRangeFacets(final RangeFacetModel[] rangeFacets) {
        this.rangeFacets = rangeFacets;
    }
}
