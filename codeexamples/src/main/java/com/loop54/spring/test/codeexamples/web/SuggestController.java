package com.loop54.spring.test.codeexamples.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.SuggestRequest;
import com.loop54.model.response.SuggestResponse;

@Controller
@RequestMapping("/suggest")
public class SuggestController {

	private ILoop54Client loop54Client;

	@Autowired
	public SuggestController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		final String query1 = "a";
		final String query2 = "b";
		final String query3 = "dfshdfjsdkhfsdjkf";
		final String query4 = "apple";

		// Code examples
		// Query-suggestion part of the 'Suggest' module
		suggestQueriesExample(query1);
		suggestScopedExample(query1);

		// Search part of the 'Suggest' module
		suggestExample(query1);
		suggestExample(query2);
		suggestExample(query3);
		suggestExample(query4);

		return new ModelAndView("suggest");
	}

	private void suggestQueriesExample(final String query) throws Loop54Exception
	{
		System.out.println("suggest-full: ");

		// CODE SAMPLE suggest-full BEGIN
		// Below is an example of a request - response cycle of a 'Suggest' request
		final SuggestRequest request = new SuggestRequest(query, true);
		request.queriesOptions.skip = 0;
		request.queriesOptions.take = 9;
		final SuggestResponse response = loop54Client.suggest(request);

		final List<String> queries = response.queries.items.stream().map(q->q.query).collect(Collectors.toList());

		// print out all suggested queries
		System.out.println("queries: " + String.join(", ", queries));
		// CODE SAMPLE END

		System.out.println("suggest-full (end) ");
	}

	private void suggestScopedExample(String query) throws Loop54Exception
	{
		System.out.println("suggest-scoped: ");

		// CODE SAMPLE suggest-scoped BEGIN
		// Below is an example of a request - response cycle of a 'Suggest' request
		// where scopes are used to provide the user with more context
		final SuggestRequest request = new SuggestRequest(query, true);
		request.queriesOptions.skip = 0;
		request.queriesOptions.take = 9;
		final SuggestResponse response = loop54Client.suggest(request);

		// prints out the scoped suggestions
		if(response.scopedQuery != null)
		{
			System.out.println("scoped query: " + response.scopedQuery.query);
			System.out.println("scopes based on: " + response.scopedQuery.scopeAttributeName);
			System.out.println("scopes: " + String.join(", ", response.scopedQuery.scopes));
		}
		// CODE SAMPLE END

		System.out.println("suggest-scoped (end)");
	}

	private void suggestExample(final String query) throws Loop54Exception
	{
		System.out.println("suggest-full: ");

		// CODE SAMPLE suggest-full BEGIN
		// The search field
		// initialize 'Suggest' request and set search query
		final SuggestRequest request = new SuggestRequest(query);

		// specify the number of response items
		request.resultsOptions.skip = 0;
		request.resultsOptions.take = 10;

		// fetch the response from the engine
		final SuggestResponse response = loop54Client.suggest(request);

		System.out.println("Original Query: " + response.originalQuery);
		System.out.println("Query used for search: " + response.queryUsedForSearch);

		if (!response.makesSense) {
			System.out.println("We did not understand your query.");
		}

		// render direct results
		final List<Entity> results = response.results.items;

		if (response.results.count == 0) {
			System.out.println("There were no items.");
		}

		for(final Entity resultItem : results)
		{
			final String productId = resultItem.id;
			final String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the 'Suggest' results page
		}
		// CODE SAMPLE END

		System.out.println("suggest-full (end) ");
	}
}
