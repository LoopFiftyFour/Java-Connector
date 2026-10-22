package com.loop54.spring.test.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.SuggestRequest;
import com.loop54.model.request.parameters.QuerySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.spring.test.model.ModelUtil;
import com.loop54.spring.test.model.SuggestResponseModel;

@Controller
@RequestMapping("/suggest")
public class SuggestController {

    private ILoop54ClientProvider loop54Service;

    @Autowired
    public SuggestController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var query = "banan";
        final var request = new SuggestRequest(query, true); //almost done with banana
        request.queriesOptions.skip = 0;
        request.queriesOptions.take = 20;

        request.queriesOptions.sortBy = new ArrayList<>();
        request.queriesOptions.sortBy.add(new QuerySortingParameter(QuerySortingParameter.Types.ALPHABETIC, SortOrders.ASC));

        request.resultsOptions.skip = 0;
        request.resultsOptions.take = 20;
        request.resultsOptions.addDistinctFacet("Manufacturer");
        request.resultsOptions.addDistinctFacet("Organic");
        request.resultsOptions.addDistinctFacet("Category");
        request.resultsOptions.addRangeFacet("Price");

        final var response = loop54Service.getNamed("english").suggest(request);
        final var model = new SuggestResponseModel();
        model.setQuery(query);
        model.setOriginalQuery(response.originalQuery);
        model.setQueryUsedForSearch(response.queryUsedForSearch);
        model.setMakesSense(response.makesSense);

        // Handle query suggestions
        model.setQueryCount(response.queries.count);
        model.setQueries(response.queries.items.stream().map(q -> q.query).toArray(String[]::new));
        model.setScoped(response.scopedQuery.scopes.toArray(String[]::new));
        model.setScopedQuery(response.scopedQuery.query);
        model.setScopeAttribute(response.scopedQuery.scopeAttributeName);

        // Handle products and facets
        model.setProductCount(response.results.count);
        model.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        model.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        model.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));

        return new ModelAndView("suggest", "results", model);
    }
}
