package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.parameters.facets.RangeFacetParameter;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.RangeFacet;
import com.loop54.model.response.SearchResponse;
import com.loop54.model.response.DistinctFacet.DistinctFacetItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/faceting")
public class FacetingController {
	private ILoop54Client loop54Client;

	@Autowired
	public FacetingController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Setup
		String query = "a";
		String query2 = "dfshdfjsdkhfsdjkf";

		// Code examples
		facetingSingleFacetExample(query);
		facetingMultipleFacetsExample(query);
		facetingEngineResponseExample(query);
		facetingDistinctFacetExample(query);
		facetingRangeFacetExample(query);
		facetingSingleFacetExample(query2);

		return new ModelAndView("faceting");
	}

	private void facetingSingleFacetExample(String query) throws Loop54Exception 
	{
		System.out.println("faceting-single-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-single-facet BEGIN
		// Search with a single facet
		SearchRequest request = new SearchRequest(query);
		// Add facets to the search request 
		request.resultsOptions.addDistinctFacet("Category");
		SearchResponse response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-single-facet (end) ");
	}

	private void facetingMultipleFacetsExample(String query) throws Loop54Exception 
	{
		System.out.println("faceting-multiple-facets: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-multiple-facets BEGIN
		// Search with multiple facets
		SearchRequest request = new SearchRequest(query);
		// Add facets to the search request 
		request.resultsOptions.addDistinctFacet("Manufacturer");
		request.resultsOptions.addDistinctFacet("Category");
		SearchResponse response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-multiple-facet (end) ");
	}

	private void facetingEngineResponseExample(String query) throws Loop54Exception 
	{
		System.out.println("faceting-engine-response: ");
		System.out.println("items: ");

		SearchRequest request = new SearchRequest(query);
		// Add facets to the search request 
		request.resultsOptions.addDistinctFacet("Manufacturer");
		request.resultsOptions.addDistinctFacet("Category");
		SearchResponse response = loop54Client.search(request);

		renderItems(response);

		renderFacets(response);

		System.out.println("faceting-engine-response (end) ");
	}

	private void facetingDistinctFacetExample(String query) throws Loop54Exception 
	{
		System.out.println("faceting-distinct-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-distinct-facet BEGIN
		// Search with a distinct facet applied
		// The use-case here is e.g. when the user clicks on a specific manufacturer in the search result facet list
		List<String> selectedManufacturers = new ArrayList<>();
		selectedManufacturers.add("MeatNStuff");
		SearchRequest request = new SearchRequest(query);

		// Add facets to the search request
		// And select a specific facet value to filter on
		request.resultsOptions.addDistinctFacet("Manufacturer", selectedManufacturers);
		request.resultsOptions.addDistinctFacet("Organic");
		request.resultsOptions.addDistinctFacet("Category");
		request.resultsOptions.addRangeFacet("Price");

		SearchResponse response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-distinct-facet (end) ");
	}

	private void facetingRangeFacetExample(String query) throws Loop54Exception 
	{
		System.out.println("faceting-range-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE faceting-range-facet BEGIN
		// Search with a range facet
		// The use-case here is e.g. when the user selects a specific price range in the search result facet list
		SearchRequest request = new SearchRequest(query);

		// Add facets to the search request
		// And select a specific range for a certain facet
		request.resultsOptions.addDistinctFacet("Manufacturer");
		request.resultsOptions.addDistinctFacet("Organic");
		request.resultsOptions.addDistinctFacet("Category");
		request.resultsOptions.addRangeFacet("Price", new RangeFacetParameter.RangeFacetSelectedParameter<Double>() {{ min = 10.0; max = 60.0; }}, null);

		SearchResponse response = loop54Client.search(request);
		// CODE SAMPLE END

		renderItems(response);
		renderFacets(response);

		System.out.println("faceting-range-facet (end) ");
	}

	private void renderFacets(SearchResponse response)
	{
		// CODE SAMPLE render-distinct-facets BEGIN
		// Render distinct facets
		List<String> distinctFacetsToDisplay = new ArrayList<String>();
		distinctFacetsToDisplay.add("Manufacturer");
		distinctFacetsToDisplay.add("Category");
		distinctFacetsToDisplay.add("Organic");

		for(String attributeName : distinctFacetsToDisplay)
		{
			DistinctFacet facet = response.results.getDistinctFacetByName(attributeName);
			if (facet != null)
			{
				List<DistinctFacetItem> facetItems = facet.getItems();
				if (facet.hasValues())
					System.out.println(attributeName + ": ");
				for (DistinctFacetItem facetItem : facetItems)
				{
					System.out.println(facetItem.getItem(String.class) + ": " + facetItem.count); // Write the facet name and the number of products in the facet 
				}
			}
		}
		// CODE SAMPLE END

		// if there is a price range facet
		RangeFacet priceFacet = response.results.getRangeFacetByName("Price");
		if (priceFacet != null)
		{
			System.out.println("Price: ");
			double minPrice = priceFacet.getMin(Double.class);
			double maxPrice = priceFacet.getMax(Double.class);
			double minPriceSelected = priceFacet.getSelectedMin(Double.class);
			double maxPriceSelected = priceFacet.getSelectedMax(Double.class);
			System.out.println("min: " + minPrice + " kr, max: " + maxPrice +
					" kr, min selected: " + minPriceSelected + " kr," +
					" max selected: " + maxPriceSelected + " kr.");
		}
	}

	private void renderItems(SearchResponse response)
	{
		List<Entity> results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items.");

		for(Entity resultItem : results)
		{
			String productId = resultItem.id;
			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			Double price = resultItem.getAttributeValueOrNull("price", Double.class);
			System.out.println(productId + " " + productTitle + " (" + price + " kr), ");
		}
	}



}
