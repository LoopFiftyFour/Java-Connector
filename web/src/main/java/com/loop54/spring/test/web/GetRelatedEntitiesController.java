package com.loop54.spring.test.web;

import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.GetRelatedEntitiesRequest;
import com.loop54.spring.test.model.GetEntitiesResponseModel;
import com.loop54.spring.test.model.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/getrelatedentities")
public class GetRelatedEntitiesController {

    private final ILoop54ClientProvider loop54Service;

    @Autowired
    public GetRelatedEntitiesController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var request = new GetRelatedEntitiesRequest("Product", "13")
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer")
                        .distinctFacet("Organic")
                        .distinctFacet("Category")
                        .rangeFacet("Price"));

        final var response = loop54Service.getNamed("english").getRelatedEntities(request);
        final var getModel = new GetEntitiesResponseModel();
        getModel.setCount(response.results.count);
        getModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        getModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        getModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));

        return new ModelAndView("getrelatedentities", "results", getModel);
    }
}
