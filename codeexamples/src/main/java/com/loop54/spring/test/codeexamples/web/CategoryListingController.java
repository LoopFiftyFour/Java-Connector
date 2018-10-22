package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.GetEntitiesByAttributeRequest;
import com.loop54.model.request.parameters.EntitySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.request.parameters.facets.RangeFacetParameter;
import com.loop54.model.request.parameters.filters.AndFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeExistsFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.DistinctFacet.DistinctFacetItem;
import com.loop54.model.response.GetEntitiesByAttributeResponse;
import com.loop54.model.response.RangeFacet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categorylisting")
public class CategoryListingController {

	private ILoop54Client loop54Client;

	@Autowired
	public CategoryListingController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {

		// Setup
		String categoryName = "meat";
		// Code examples
		categoryListingExample(categoryName);
		categoryListingFacetsExample(categoryName);
		categoryListingDistinctFacetExample(categoryName);
		categoryListingRangeFacetExample(categoryName);
		categoryListingSortingExample(categoryName);
		categoryListingFilterExample(categoryName);

		return new ModelAndView("categorylisting");
	}

	protected void categoryListingExample(String categoryName) throws Loop54Exception {
		System.out.println("categorylisting-full: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-full BEGIN
		// Below is an example of a request - response cycle of a category listing request

		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);
		request.resultsOptions.skip = 0;
		request.resultsOptions.take = 9;

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// INJECT SAMPLE render-items BEGIN
		renderItems(response);
		// INJECT SAMPLE END
		// CODE SAMPLE END

		System.out.println("categorylisting-full (end)");	
	}

	protected void categoryListingFacetsExample(String categoryName) throws Loop54Exception {
		System.out.println("categorylisting-facets: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-facets BEGIN
		// Category listing with facets

		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);
		// Add facets to the request 
		request.resultsOptions.addDistinctFacet("Manufacturer");
		request.resultsOptions.addDistinctFacet("Category");
		request.resultsOptions.addDistinctFacet("Organic");
		request.resultsOptions.addRangeFacet("Price");

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// INJECT SAMPLE render-items BEGIN
		renderItems(response);
		// INJECT SAMPLE END
		// INJECT SAMPLE render-distinct-facets BEGIN
		renderFacets(response);
		// INJECT SAMPLE END
		// CODE SAMPLE END

		System.out.println("categorylisting-facets (end)");	
	}

	private void categoryListingDistinctFacetExample(String categoryName) throws Loop54Exception 
	{
		System.out.println("categorylisting-distinct-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-distinct-facet BEGIN
		// Category listing with a distinct facet applied
		// The use-case here is e.g. when the user clicks on a specific manufacturer in the category listing facet list
		List<String> selectedManufacturers = new ArrayList<>();
		selectedManufacturers.add("MeatNStuff");
		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);

		// Add facets to the request 
		// And select a specific facet value to filter on
		request.resultsOptions.addDistinctFacet("Manufacturer", selectedManufacturers);
		request.resultsOptions.addDistinctFacet("Category");
		request.resultsOptions.addDistinctFacet("Organic");
		request.resultsOptions.addRangeFacet("Price");

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END	

		renderItemsExtended(response);
		renderFacets(response);
		System.out.println("categorylisting-distinct-facet (end) ");
	}

	private void categoryListingRangeFacetExample(String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-range-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-range-facet BEGIN
		// Category listing with a range facet
		// The use-case here is e.g. when the user selects a specific price range in the category listing facet list
		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);

		// Add facets to the request 
		// And select a specific range for a certain facet
		request.resultsOptions.addDistinctFacet("Manufacturer");
		request.resultsOptions.addDistinctFacet("Category");
		request.resultsOptions.addDistinctFacet("Organic");
		request.resultsOptions.addRangeFacet("Price", new RangeFacetParameter.RangeFacetSelectedParameter<Double>() {{ min = 10.0; max = 60.0; }}, null);

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);
		renderFacets(response);

		System.out.println("categorylisting-range-facet (end) ");
	}

	private void categoryListingSortingExample(String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-sorting: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-sorting BEGIN
		// Category listing with sorting
		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);

		//Set the sort order of the products in the category
		request.resultsOptions.sortBy = new ArrayList<>();
		request.resultsOptions.sortBy.add(new EntitySortingParameter("Price"){{ order = SortOrders.ASC; }}); // Primary sorting: Sort on attribute Price, ascending order
		request.resultsOptions.sortBy.add(new EntitySortingParameter(EntitySortingParameter.Types.POPULARITY){{ order = SortOrders.DESC; }});// Secondary sorting: Sort on popularity, descending order

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);

		System.out.println("categorylisting-sorting (end) ");
	}

	private void categoryListingFilterExample(String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-filter: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-filter BEGIN
		// Category listing with filters
		GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", categoryName);

		// Filter the products in the category
		// In this case, we only want products that have got
		// the price attribute, and where the organic attribute is set to "True"
		request.resultsOptions.filter = new AndFilterParameter(
				new AttributeExistsFilterParameter("Price"),
				//Because the organic attribute is stored as a string in the engine we need to filter with that type.
				//If it would have been stored as a boolean we would have used bool instead.
				new AttributeFilterParameter<String>("Organic", "True")
				);

		GetEntitiesByAttributeResponse response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);

		System.out.println("categorylisting-filter (end) ");
	}

	private void renderItems(GetEntitiesByAttributeResponse response)
	{
		// CODE SAMPLE render-items BEGIN
		List<Entity> results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items in this category.");

		for(Entity resultItem : results)
		{
			String productId = resultItem.id;

			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle);
			//render a product on the category listing page
		}
		// CODE SAMPLE END
	}

	private void renderItemsExtended(GetEntitiesByAttributeResponse response)
	{
		List<Entity> results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items in this category.");

		for(Entity resultItem : results)
		{
			String productId = resultItem.id;
			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			Double price = resultItem.getAttributeValueOrNull("price", Double.class);
			String organic = resultItem.getAttributeValueOrNull("organic", String.class);
			System.out.println(productId + " " + productTitle + " (" + price + " kr, " + organic + "), ");
			// render a product on the category listing page
		}
	}

	private void renderFacets(GetEntitiesByAttributeResponse response)
	{
		// CODE SAMPLE render-distinct-facets BEGIN
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
}
