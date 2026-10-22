package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/search")
public class SearchController {
	private final ILoop54Client loop54Client;

	@Autowired
	public SearchController(final ILoop54Client loop54Client) {
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
		searchExample(query1);
		searchExample(query2);
		searchExample(query3);
		searchExample(query4);

		return new ModelAndView("search");
	}

	private void searchExample(final String query) throws Loop54Exception
	{
		System.out.println("search-full: ");

		// CODE SAMPLE search-full BEGIN
		// The search field
		// initialize "Search" request and set search query
		final var request = new SearchRequest(query)
				// specify the number of response items
				.withResults(r -> r.page(0, 10))
				.withRelatedResults(r -> r.page(0, 9));

		// fetch the response from the engine
		final var response = loop54Client.search(request);

		// INJECT SAMPLE search-check-results BEGIN
		checkResults(response);
		// INJECT SAMPLE END

		// render direct results
		final var results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items.");

		for (final var resultItem : results)
		{
			final var productId = resultItem.id;
			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the search results page
		}

		// render recommended results
		final var relatedResults = response.relatedResults.items;

		if (response.relatedResults.count > 0)
			System.out.println("Maybe you also want these?");

		for (final var resultItem : relatedResults)
		{
			final var productId = resultItem.id;
			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the search results page
		}
		// CODE SAMPLE END

		System.out.println("search-full (end) ");
	}

	private void checkResults(final SearchResponse response)
	{
		// CODE SAMPLE search-check-results BEGIN
		// Check the search results
		// if the result does not make sense, show error message
		// (note that there may still be results!)
		if (!response.makesSense)
			System.out.println("We did not understand your query.");

		// render spelling suggestions
		if (response.spellingSuggestions.count > 0)
		{
			final var queryResults = response.spellingSuggestions.items;
			final var queries = queryResults.stream().map(q -> q.query).toList();

			System.out.println("Did you mean: " + String.join(", ", queries) + "?");
		}
		// CODE SAMPLE END
	}


}
