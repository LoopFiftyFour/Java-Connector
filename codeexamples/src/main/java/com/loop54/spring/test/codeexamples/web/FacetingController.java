package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.parameters.facets.RangeFacetParameter;
import com.loop54.model.response.SearchResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/faceting")
public class FacetingController {
	private final ILoop54Client loop54Client;

	@Autowired
	public FacetingController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		final var query = "a";
		final var query2 = "dfshdfjsdkhfsdjkf";

		// Code examples
		facetingSingleFacetExample(query);
		facetingMultipleFacetsExample(query);
		facetingEngineResponseExample(query);
		facetingDistinctFacetExample(query);
		facetingRangeFacetExample(query);
		facetingSingleFacetExample(query2);

		return new ModelAndView("faceting");
	}

	private void facetingSingleFacetExample(final String query) throws Loop54Exception 
	{
		System.out.println("faceting-single-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-single-facet BEGIN
		// Search with a single facet
		final var request = new SearchRequest(query)
				// Add facets to the search request
				.withResults(r -> r.distinctFacet("Category"));
		final var response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-single-facet (end) ");
	}

	private void facetingMultipleFacetsExample(final String query) throws Loop54Exception 
	{
		System.out.println("faceting-multiple-facets: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-multiple-facets BEGIN
		// Search with multiple facets
		final var request = new SearchRequest(query)
				// Add facets to the search request
				.withResults(r -> r
						.distinctFacet("Manufacturer")
						.distinctFacet("Category"));
		final var response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-multiple-facet (end) ");
	}

	private void facetingEngineResponseExample(final String query) throws Loop54Exception 
	{
		System.out.println("faceting-engine-response: ");
		System.out.println("items: ");

		final var request = new SearchRequest(query)
				// Add facets to the search request
				.withResults(r -> r
						.distinctFacet("Manufacturer")
						.distinctFacet("Category"));
		final var response = loop54Client.search(request);

		renderItems(response);

		renderFacets(response);

		System.out.println("faceting-engine-response (end) ");
	}

	private void facetingDistinctFacetExample(final String query) throws Loop54Exception 
	{
		System.out.println("faceting-distinct-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-distinct-facet BEGIN
		// Search with a distinct facet applied
		// The use-case here is e.g. when the user clicks on a specific manufacturer in the search result facet list
		final var selectedManufacturers = List.of("MeatNStuff");
		final var request = new SearchRequest(query)
				// Add facets to the search request
				// And select a specific facet value to filter on
				.withResults(r -> r
						.distinctFacet("Manufacturer", selectedManufacturers)
						.distinctFacet("Organic")
						.distinctFacet("Category")
						.rangeFacet("Price"));

		final var response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-distinct-facet (end) ");
	}

	private void facetingRangeFacetExample(final String query) throws Loop54Exception 
	{
		System.out.println("faceting-range-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-range-facet BEGIN
		// Search with a range facet
		// The use-case here is e.g. when the user selects a specific price range in the search result facet list
		final var request = new SearchRequest(query)
				// Add facets to the search request
				// And select a specific range for a certain facet
				.withResults(r -> r
						.distinctFacet("Manufacturer")
						.distinctFacet("Organic")
						.distinctFacet("Category")
						.rangeFacet("Price", new RangeFacetParameter.RangeFacetSelectedParameter<>(10.0, 60.0), null));

		final var response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-range-facet (end) ");
	}

	private void renderFacets(final SearchResponse response)
	{
		// CODE SAMPLE render-distinct-facets BEGIN
		// Render distinct facets
		final var distinctFacetsToDisplay = List.of("Manufacturer", "Category", "Organic");

		for (final var attributeName : distinctFacetsToDisplay)
		{
			final var facet = response.results.getDistinctFacetByName(attributeName);
			if (facet != null)
			{
				final var facetItems = facet.getItems();
				if (facet.hasValues())
					System.out.println(attributeName + ": ");
				for (final var facetItem : facetItems)
				{
					System.out.println(facetItem.getItem(String.class) + ": " + facetItem.count); // Write the facet name and the number of products in the facet 
				}
			}
		}
		// CODE SAMPLE END

		// if there is a price range facet
		final var priceFacet = response.results.getRangeFacetByName("Price");
		if (priceFacet != null)
		{
			System.out.println("Price: ");
			final double minPrice = priceFacet.getMin(Double.class);
			final double maxPrice = priceFacet.getMax(Double.class);
			final double minPriceSelected = priceFacet.getSelectedMin(Double.class);
			final double maxPriceSelected = priceFacet.getSelectedMax(Double.class);
			System.out.println("min: " + minPrice + " kr, max: " + maxPrice +
					" kr, min selected: " + minPriceSelected + " kr," +
					" max selected: " + maxPriceSelected + " kr.");
		}
	}

	private void renderItems(final SearchResponse response)
	{
		final var results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items.");

		for (final var resultItem : results)
		{
			final var productId = resultItem.id;
			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			final var price = resultItem.getAttributeValueOrNull("price", Double.class);
			System.out.println(productId + " " + productTitle + " (" + price + " kr), ");
		}
	}



}
