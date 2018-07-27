package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.response.SearchResponse;
import com.loop54.spring.test.model.ModelUtil;
import com.loop54.spring.test.model.SearchResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private ILoop54ClientProvider loop54Service;

    @Autowired
    public SearchController(ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        SearchRequest search = new SearchRequest("meat");
        search.resultsOptions.skip = 0;
        search.resultsOptions.take = 20;
        search.relatedResultsOptions.skip = 0;
        search.relatedResultsOptions.take = 20;
        search.resultsOptions.addDistinctFacet("Manufacturer");
        search.resultsOptions.addDistinctFacet("Organic");
        search.resultsOptions.addDistinctFacet("Category");
        search.resultsOptions.addRangeFacet("Price");

        SearchResponse response = loop54Service.getNamed("english").search(search);
        return new ModelAndView("search", "results", makeModel(response));
    }

    @RequestMapping(value = "/selectFacet", method = RequestMethod.GET)
    public ModelAndView selectFacet() throws Loop54Exception {
        SearchRequest search = new SearchRequest("meat");
        search.resultsOptions.skip = 0;
        search.resultsOptions.take = 20;
        search.relatedResultsOptions.skip = 0;
        search.relatedResultsOptions.take = 20;

        List<String> selectedManufacturers = new ArrayList<>();
        selectedManufacturers.add("MeatNStuff");
        selectedManufacturers.add("Happy birds");

        search.resultsOptions.addDistinctFacet("Manufacturer", selectedManufacturers);
        search.resultsOptions.addDistinctFacet("Organic");
        search.resultsOptions.addDistinctFacet("Category");
        search.resultsOptions.addRangeFacet("Price");

        SearchResponse response = loop54Service.getNamed("english").search(search);
        return new ModelAndView("search", "results", makeModel(response));
    }

    @RequestMapping(value = "/withCustomData", method = RequestMethod.GET)
    public ModelAndView withCustomData() throws Loop54Exception {
        SearchRequest search = new SearchRequest("meat");
        search.resultsOptions.skip = 0;
        search.resultsOptions.take = 20;
        search.relatedResultsOptions.skip = 0;
        search.relatedResultsOptions.take = 20;

        search.resultsOptions.addDistinctFacet("Manufacturer");
        search.resultsOptions.addDistinctFacet("Organic");
        search.resultsOptions.addDistinctFacet("Category");
        search.resultsOptions.addRangeFacet("Price");

        //Add custom data to the request
        //What custom data that is available to you may vary depending on your price package.
        //Please contact customer support for more information.
        search.addCustomData("message", "ping");

        SearchResponse response = loop54Service.getNamed("english").search(search);

        //Get the custom data from the response
        //This method can deserialize complex classes as well, for instance a EntityCollection if doing content search.
        //Again, contact customer support for more information.
        String responseMessage = response.getCustomDataOrDefault("responseMessage", String.class);

        SearchResponseModel searchModel = makeModel(response);
        searchModel.setResponseMessage(responseMessage);
        return new ModelAndView("search", "results", searchModel);
    }

    private SearchResponseModel makeModel(SearchResponse response) {
        SearchResponseModel searchModel = new SearchResponseModel();
        searchModel.setQuery(response.query);
        searchModel.setCount(response.results.count);
        searchModel.setRelatedCount(response.relatedResults.count);
        searchModel.setMakesSense(response.makesSense);

        String[] spelling = response.spellingSuggestions.items.stream().map(q -> q.query).toArray(String[]::new);
        searchModel.setSpellingSuggestions(spelling);

        String[] relatedQueries = response.relatedQueries.items.stream().map(q -> q.query).toArray(String[]::new);
        searchModel.setRelatedQueries(relatedQueries);

        searchModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        searchModel.setRelatedResults(ModelUtil.getEntityModelsFromResponse(response.relatedResults.items));
        searchModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        searchModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));
        return searchModel;
    }
}
