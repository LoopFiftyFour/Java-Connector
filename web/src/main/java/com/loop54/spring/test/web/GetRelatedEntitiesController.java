package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.GetRelatedEntitiesRequest;
import com.loop54.model.response.*;
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

    private ILoop54ClientProvider loop54Service;

    @Autowired
    public GetRelatedEntitiesController(ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        GetRelatedEntitiesRequest request = new GetRelatedEntitiesRequest("Product", "13");
        request.resultsOptions.take = 20;
        request.resultsOptions.addDistinctFacet("Manufacturer", null, null, null);
        request.resultsOptions.addDistinctFacet("Organic", null, null, null);
        request.resultsOptions.addDistinctFacet("Category", null, null, null);
        request.resultsOptions.addRangeFacet("Price", null, null);

        GetRelatedEntitiesResponse response = loop54Service.getNamed("english").getRelatedEntities(request);
        GetEntitiesResponseModel getModel = new GetEntitiesResponseModel();
        getModel.setCount(response.results.count);
        getModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        getModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        getModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));

        return new ModelAndView("getrelatedentities", "results", getModel);
    }
}
