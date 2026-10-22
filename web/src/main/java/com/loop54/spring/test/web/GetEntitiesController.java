package com.loop54.spring.test.web;

import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.GetEntitiesRequest;
import com.loop54.model.request.parameters.filters.AndFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeExistsFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.spring.test.model.ModelUtil;
import com.loop54.spring.test.model.GetEntitiesResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/getentities")
public class GetEntitiesController {

    private final ILoop54ClientProvider loop54Service;

    @Autowired
    public GetEntitiesController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var request = new GetEntitiesRequest()
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer")
                        .distinctFacet("Organic")
                        .distinctFacet("Category")
                        .rangeFacet("Price")
                        .filter(new AndFilterParameter(
                                new AttributeExistsFilterParameter("Organic"),
                                //We only want to show organic products
                                new AttributeFilterParameter<>("Organic", "True"))));

        final var response = loop54Service.getNamed("english").getEntities(request);
        final var getModel = new GetEntitiesResponseModel();
        getModel.setCount(response.results.count);
        getModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        getModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        getModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));

        return new ModelAndView("getentities", "results", getModel);
    }
}
