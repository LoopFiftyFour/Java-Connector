package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.parameters.QuerySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.response.AutoCompleteResponse;
import com.loop54.spring.test.model.AutoCompleteResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/autocomplete")
public class AutoCompleteController {

    private ILoop54ClientProvider loop54Service;

    @Autowired
    public AutoCompleteController(ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final String query = "banan";
        AutoCompleteRequest request = new AutoCompleteRequest(query); //almost done with banana
        request.queriesOptions.skip = 0;
        request.queriesOptions.take = 20;

        request.queriesOptions.sortBy = new ArrayList<>();
        request.queriesOptions.sortBy
                .add(new QuerySortingParameter(QuerySortingParameter.Types.ALPHABETIC, SortOrders.ASC));

        AutoCompleteResponse response = loop54Service.getNamed("english").autoComplete(request);
        AutoCompleteResponseModel autoModel = new AutoCompleteResponseModel();
        autoModel.setCount(response.queries.count);
        autoModel.setQuery(query);
        autoModel.setResults(response.queries.items.stream().map(q -> q.query).toArray(String[]::new));
        List<String> scopes = response.scopedQuery.scopes;
        autoModel.setScoped(scopes.toArray(new String[scopes.size()]));
        autoModel.setScopedQuery(response.scopedQuery.query);

        return new ModelAndView("autocomplete", "results", autoModel);
    }
}
