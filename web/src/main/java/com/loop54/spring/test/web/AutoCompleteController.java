package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.parameters.QuerySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.spring.test.model.AutoCompleteResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/autocomplete")
public class AutoCompleteController {

    private final ILoop54ClientProvider loop54Service;

    @Autowired
    public AutoCompleteController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var query = "banan";
        final var request = new AutoCompleteRequest(query) //almost done with banana
                .withQueries(q -> q.page(0, 20)
                        .sortBy(new QuerySortingParameter(QuerySortingParameter.Types.ALPHABETIC, SortOrders.ASC)));

        final var response = loop54Service.getNamed("english").autoComplete(request);
        final var autoModel = new AutoCompleteResponseModel();
        autoModel.setCount(response.queries.count);
        autoModel.setQuery(query);
        autoModel.setResults(response.queries.items.stream().map(q -> q.query).toArray(String[]::new));
        autoModel.setScoped(response.scopedQuery.scopes.toArray(String[]::new));
        autoModel.setScopedQuery(response.scopedQuery.query);
        autoModel.setScopeAttribute(response.scopedQuery.scopeAttributeName);

        return new ModelAndView("autocomplete", "results", autoModel);
    }
}
