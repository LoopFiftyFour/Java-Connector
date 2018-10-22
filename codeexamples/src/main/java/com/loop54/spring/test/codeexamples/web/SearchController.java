package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.response.QueryResult;
import com.loop54.model.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController {
	private ILoop54Client loop54Client;

	@Autowired
	public SearchController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		String query1 = "a";
		String query2 = "b";

		// Code examples
		searchExample(query1);
		searchCheckResultExample(query2);

		return new ModelAndView("search");
	}

	private void searchExample(String query) throws Loop54Exception
	{
		System.out.println("search-full: ");

		// CODE SAMPLE search-full BEGIN
		// The search field
		// initialize "Search" request and set search query
		SearchRequest request = new SearchRequest(query);

		// specify the number of response items
		request.resultsOptions.skip = 0;
		request.resultsOptions.take = 10;
		request.relatedResultsOptions.skip = 0;
		request.relatedResultsOptions.take = 9;

		// fetch the response from the engine
		SearchResponse response = loop54Client.search(request);

		// INJECT SAMPLE search-check-results BEGIN
		checkResults(response);
		// INJECT SAMPLE END

		// render direct results
		List<Entity> results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items.");

		for(Entity resultItem : results)
		{
			String productId = resultItem.id;
			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the search results page
		}

		// render recommended results
		List<Entity> relatedResults = response.relatedResults.items;

		if (response.relatedResults.count > 0)
			System.out.println("Maybe you also want these?");

		for(Entity resultItem : relatedResults)
		{
			String productId = resultItem.id;
			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the search results page
		}
		// CODE SAMPLE END

		System.out.println("search-full (end) ");
	}

	private void searchCheckResultExample(String query) throws Loop54Exception
	{
		System.out.println("search-check-result: ");
		// initialize "Search" request and set the search query
		SearchRequest request = new SearchRequest(query);

		// specify the number of response items
		request.resultsOptions.skip = 0;
		request.resultsOptions.take = 10;
		request.relatedResultsOptions.skip = 0;
		request.relatedResultsOptions.take = 9;

		// fetch the response from the engine
		SearchResponse response = loop54Client.search(request);

		checkResults(response);

		System.out.println("search-check-result (end) ");
	}

	private void checkResults(SearchResponse response)
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
			List<QueryResult> queryResults = response.spellingSuggestions.items;
			List<String> queries = queryResults.stream().map(q->q.query).collect(Collectors.toList());

			System.out.println("Did you mean: " + String.join(", ", queries) + "?");
		}
		// CODE SAMPLE END
	}


}
