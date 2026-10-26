package com.loop54.spring.test.codeexamples.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.SuggestRequest;

@Controller
@RequestMapping("/suggest")
public class SuggestController {

	private final ILoop54Client loop54Client;

	@Autowired
	public SuggestController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		final var query1 = "a";
		final var query2 = "b";
		final var query3 = "dfshdfjsdkhfsdjkf";
		final var query4 = "apple";

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
		final var request = new SuggestRequest(query, true)
				.withQueries(q -> q.page(0, 9));
		final var response = loop54Client.suggest(request);

		final var queries = response.queries.items.stream().map(q -> q.query).toList();

		// print out all suggested queries
		System.out.println("queries: " + String.join(", ", queries));
		// CODE SAMPLE END

		System.out.println("suggest-full (end) ");
	}

	private void suggestScopedExample(final String query) throws Loop54Exception
	{
		System.out.println("suggest-scoped: ");

		// CODE SAMPLE suggest-scoped BEGIN
		// Below is an example of a request - response cycle of a 'Suggest' request
		// where scopes are used to provide the user with more context
		final var request = new SuggestRequest(query, true)
				.withQueries(q -> q.page(0, 9));
		final var response = loop54Client.suggest(request);

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
		System.out.println("suggest-search: ");

		// CODE SAMPLE suggest-search BEGIN
		// The search field
		// initialize 'Suggest' request and set search query
		final var request = new SuggestRequest(query)
				// specify the number of response items
				.withResults(r -> r.page(0, 10));

		// fetch the response from the engine
		final var response = loop54Client.suggest(request);

		System.out.println("Original Query: " + response.originalQuery);
		System.out.println("Query used for search: " + response.queryUsedForSearch);

		if (!response.makesSense) {
			System.out.println("We did not understand your query.");
		}

		// render direct results
		final var results = response.results.items;

		if (response.results.count == 0) {
			System.out.println("There were no items.");
		}

		for (final var resultItem : results)
		{
			final var productId = resultItem.id;
			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the 'Suggest' results page
		}
		// CODE SAMPLE END

		System.out.println("suggest-search (end) ");
	}
}
