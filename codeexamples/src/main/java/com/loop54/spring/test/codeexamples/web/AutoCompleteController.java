package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.response.AutoCompleteResponse;
import com.loop54.model.response.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/autocomplete")
public class AutoCompleteController {

	private ILoop54Client loop54Client;

	@Autowired
	public AutoCompleteController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		final String query = "a";

		// Code examples
		autoCompleteExample(query);
		scopedAutoCompleteExample(query);

		return new ModelAndView("autocomplete");
	}

	private void autoCompleteExample(String query) throws Loop54Exception 
	{
		System.out.println("autocomplete-full: ");

		// CODE SAMPLE autocomplete-full BEGIN
		// Below is an example of a request - response cycle of an autocomplete request
		AutoCompleteRequest request = new AutoCompleteRequest(query);
		request.queriesOptions.skip = 0;
		request.queriesOptions.take = 9;
		AutoCompleteResponse response = loop54Client.autoComplete(request);

		List<QueryResult> queryResults = response.queries.items;
		List<String> queries = queryResults.stream().map(q->q.query).collect(Collectors.toList());

		// print out all suggested autocomplete queries
		System.out.println("queries: " + String.join(", ", queries));
		// CODE SAMPLE END

		System.out.println("autocomplete-full (end) ");
	}

	private void scopedAutoCompleteExample(String query) throws Loop54Exception 
	{
		System.out.println("autocomplete-scoped: ");

		// CODE SAMPLE autocomplete-scoped BEGIN
		// Below is an example of a request - response cycle of an autocomplete request
		// where scopes are used to provide the user with more context
		AutoCompleteRequest request = new AutoCompleteRequest(query);
		request.queriesOptions.skip = 0;
		request.queriesOptions.take = 9;
		AutoCompleteResponse response = loop54Client.autoComplete(request);

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
