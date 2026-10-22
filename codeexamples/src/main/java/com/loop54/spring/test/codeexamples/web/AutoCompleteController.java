package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/autocomplete")
public class AutoCompleteController {

	private final ILoop54Client loop54Client;

	@Autowired
	public AutoCompleteController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		final var query = "a";

		// Code examples
		autoCompleteExample(query);
		scopedAutoCompleteExample(query);

		return new ModelAndView("autocomplete");
	}

	private void autoCompleteExample(final String query) throws Loop54Exception 
	{
		System.out.println("autocomplete-full: ");

		// CODE SAMPLE autocomplete-full BEGIN
		// Below is an example of a request - response cycle of an autocomplete request
		final var request = new AutoCompleteRequest(query)
				.withQueries(q -> q.page(0, 9));
		final var response = loop54Client.autoComplete(request);

		final var queryResults = response.queries.items;
		final var queries = queryResults.stream().map(q -> q.query).toList();

		// print out all suggested autocomplete queries
		System.out.println("queries: " + String.join(", ", queries));
		// CODE SAMPLE END

		System.out.println("autocomplete-full (end) ");
	}

	private void scopedAutoCompleteExample(final String query) throws Loop54Exception 
	{
		System.out.println("autocomplete-scoped: ");

		// CODE SAMPLE autocomplete-scoped BEGIN
		// Below is an example of a request - response cycle of an autocomplete request
		// where scopes are used to provide the user with more context
		final var request = new AutoCompleteRequest(query)
				.withQueries(q -> q.page(0, 9));
		final var response = loop54Client.autoComplete(request);

		// prints out the scoped suggestions
		if(response.scopedQuery != null)
		{
			System.out.println("scoped query: " + response.scopedQuery.query);
			System.out.println("scopes based on: " + response.scopedQuery.scopeAttributeName);
			System.out.println("scopes: " + String.join(", ", response.scopedQuery.scopes));
		}
		// CODE SAMPLE END

		System.out.println("autocomplete-scoped (end) ");
	}
}
