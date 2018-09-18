package com.loop54.spring.test.model;

import com.loop54.spring.test.model.DistinctFacetModel;
import com.loop54.spring.test.model.EntityModel;
import com.loop54.spring.test.model.RangeFacetModel;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AutoCompleteResponseModel {

    private String query;
    private int count;
    private String[] results;
    private String scopedQuery;
	private String scopeAttribute;
    private String[] scoped;

    @ModelAttribute
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
	
	@ModelAttribute
    public String getScopeAttribute() {
        return scopeAttribute;
    }
	
	public void setScopeAttribute(String attributeName) {
        this.scopeAttribute = attributeName;
    }

    @ModelAttribute
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ModelAttribute
    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    @ModelAttribute
    public String getScopedQuery() {
        return scopedQuery;
    }

    public void setScopedQuery(String scopedQuery) {
        this.scopedQuery = scopedQuery;
    }

    @ModelAttribute
    public String[] getScoped() {
        return scoped;
    }

    public void setScoped(String[] scoped) {
        this.scoped = scoped;
    }
}
