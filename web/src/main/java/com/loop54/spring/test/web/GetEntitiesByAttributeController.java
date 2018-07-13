package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.GetEntitiesByAttributeRequest;
import com.loop54.model.request.parameters.filters.AndFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeExistsFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.response.GetEntitiesByAttributeResponse;
import com.loop54.spring.test.model.GetEntitiesResponseModel;
import com.loop54.spring.test.model.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/getentitiesbyattribute")
public class GetEntitiesByAttributeController {

    private ILoop54ClientProvider loop54Service;

    @Autowired
    public GetEntitiesByAttributeController(ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        //Get all products in the meat category
        GetEntitiesByAttributeRequest request
                = new GetEntitiesByAttributeRequest("Category", "meat");

        request.resultsOptions.skip = 0;
        request.resultsOptions.take = 20;
        request.resultsOptions.addDistinctFacet("Manufacturer", null, null, null);
        request.resultsOptions.addRangeFacet("Price", null, null);

        request.resultsOptions.filter = new AndFilterParameter(
                new AttributeExistsFilterParameter("Organic"),
                //We only want to show non-organic products
                new AttributeFilterParameter("Organic", "False"));

        GetEntitiesByAttributeResponse response = loop54Service.getNamed("english").getEntitiesByAttribute(request);
        GetEntitiesResponseModel getModel = new GetEntitiesResponseModel();
        getModel.setCount(response.results.count);
        getModel.setResults(ModelUtil.getEntityModelsFromResponse(response.results.items));
        getModel.setDistinctFacets(ModelUtil.getDistinctFacetModelFromResponse(response.results.facets));
        getModel.setRangeFacets(ModelUtil.getRangeFacetModelFromResponse(response.results.facets));

        return new ModelAndView("getentitiesbyattribute", "results", getModel);
    }
}
