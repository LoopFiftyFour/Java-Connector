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

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final ILoop54ClientProvider loop54Service;

    @Autowired
    public SearchController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var query = "meat";
        final var search = new SearchRequest(query)
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer")
                        .distinctFacet("Organic")
                        .distinctFacet("Category")
                        .rangeFacet("Price"))
                .withRelatedResults(r -> r.page(0, 20));

        final var response = loop54Service.getNamed("english").search(search);
        return new ModelAndView("search", "results", makeModel(response));
    }

    @RequestMapping(value = "/selectFacet", method = RequestMethod.GET)
    public ModelAndView selectFacet() throws Loop54Exception {
        final var query = "meat";
        final var search = new SearchRequest(query)
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer", List.of("MeatNStuff", "Happy birds"))
                        .distinctFacet("Organic")
                        .distinctFacet("Category")
                        .rangeFacet("Price"))
                .withRelatedResults(r -> r.page(0, 20));

        final var response = loop54Service.getNamed("english").search(search);
        return new ModelAndView("search", "results", makeModel(response));
    }

    @RequestMapping(value = "/withCustomData", method = RequestMethod.GET)
    public ModelAndView withCustomData() throws Loop54Exception {
        final var query = "meat";
        final var search = new SearchRequest(query)
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer")
                        .distinctFacet("Organic")
                        .distinctFacet("Category")
                        .rangeFacet("Price"))
                .withRelatedResults(r -> r.page(0, 20));

        //Add custom data to the request
        //What custom data that is available to you may vary depending on your price package.
        //Please contact customer support for more information.
        search.addCustomData("message", "ping");

        final var response = loop54Service.getNamed("english").search(search);

        //Get the custom data from the response
        //This method can deserialize complex classes as well, for instance a EntityCollection if doing content search.
        //Again, contact customer support for more information.
        final var responseMessage = response.getCustomDataOrDefault("responseMessage", String.class);

        final var searchModel = makeModel(response);
        searchModel.setResponseMessage(responseMessage);
        return new ModelAndView("search", "results", searchModel);
    }

    private SearchResponseModel makeModel(final SearchResponse response) {
        final var searchModel = new SearchResponseModel();
        searchModel.setQuery("meat");
        searchModel.setCount(response.results.count);
        searchModel.setRelatedCount(response.relatedResults.count);
        searchModel.setMakesSense(response.makesSense);

        final var spelling = response.spellingSuggestions.items.stream().map(q -> q.query).toArray(String[]::new);
        searchModel.setSpellingSuggestions(spelling);

        final var relatedQueries = response.relatedQueries.items.stream().map(q -> q.query).toArray(String[]::new);
        searchModel.setRelatedQueries(relatedQueries);

        searchModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        searchModel.setRelatedResults(ModelUtil.getEntityModelsFromResponse(response.relatedResults.items));
        searchModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        searchModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));
        return searchModel;
    }
}
