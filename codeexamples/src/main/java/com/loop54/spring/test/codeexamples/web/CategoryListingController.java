package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.request.GetEntitiesByAttributeRequest;
import com.loop54.model.request.parameters.EntitySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.request.parameters.facets.RangeFacetParameter;
import com.loop54.model.request.parameters.filters.AndFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeExistsFilterParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.response.GetEntitiesByAttributeResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categorylisting")
public class CategoryListingController {

	private final ILoop54Client loop54Client;

	@Autowired
	public CategoryListingController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {

		// Setup
		final var categoryName = "meat";
		// Code examples
		categoryListingExample(categoryName);
		categoryListingFacetsExample(categoryName);
		categoryListingDistinctFacetExample(categoryName);
		categoryListingRangeFacetExample(categoryName);
		categoryListingSortingExample(categoryName);
		categoryListingFilterExample(categoryName);

		return new ModelAndView("categorylisting");
	}

	private void categoryListingExample(final String categoryName) throws Loop54Exception {
		System.out.println("categorylisting-full: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-full BEGIN
		// Below is an example of a request - response cycle of a category listing request

		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				.withResults(r -> r.page(0, 9));

		final var response = loop54Client.getEntitiesByAttribute(request);
		// INJECT SAMPLE render-items BEGIN
		renderItems(response);
		// INJECT SAMPLE END
		// CODE SAMPLE END

		System.out.println("categorylisting-full (end)");	
	}

	private void categoryListingFacetsExample(final String categoryName) throws Loop54Exception {
		System.out.println("categorylisting-facets: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-facets BEGIN
		// Category listing with facets

		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				// Add facets to the request
				.withResults(r -> r
						.distinctFacet("Manufacturer")
						.distinctFacet("Category")
						.distinctFacet("Organic")
						.rangeFacet("Price"));

		final var response = loop54Client.getEntitiesByAttribute(request);
		// INJECT SAMPLE render-items BEGIN
		renderItems(response);
		// INJECT SAMPLE END
		// INJECT SAMPLE render-distinct-facets BEGIN
		renderFacets(response);
		// INJECT SAMPLE END
		// CODE SAMPLE END

		System.out.println("categorylisting-facets (end)");	
	}

	private void categoryListingDistinctFacetExample(final String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-distinct-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-distinct-facet BEGIN
		// Category listing with a distinct facet applied
		// The use-case here is e.g. when the user clicks on a specific manufacturer in the category listing facet list
		final var selectedManufacturers = List.of("MeatNStuff");
		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				// Add facets to the request
				// And select a specific facet value to filter on
				.withResults(r -> r
						.distinctFacet("Manufacturer", selectedManufacturers)
						.distinctFacet("Category")
						.distinctFacet("Organic")
						.rangeFacet("Price"));

		final var response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END	

		renderItemsExtended(response);
		renderFacets(response);
		System.out.println("categorylisting-distinct-facet (end) ");
	}

	private void categoryListingRangeFacetExample(final String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-range-facet: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-range-facet BEGIN
		// Category listing with a range facet
		// The use-case here is e.g. when the user selects a specific price range in the category listing facet list
		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				// Add facets to the request
				// And select a specific range for a certain facet
				.withResults(r -> r
						.distinctFacet("Manufacturer")
						.distinctFacet("Category")
						.distinctFacet("Organic")
						.rangeFacet("Price", new RangeFacetParameter.RangeFacetSelectedParameter<>(10.0, 60.0), null));

		final var response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);
		renderFacets(response);

		System.out.println("categorylisting-range-facet (end) ");
	}

	private void categoryListingSortingExample(final String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-sorting: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-sorting BEGIN
		// Category listing with sorting
		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				//Set the sort order of the products in the category
				.withResults(r -> r.sortBy(
						new EntitySortingParameter("Price", SortOrders.ASC), // Primary sorting: Sort on attribute Price, ascending order
						new EntitySortingParameter(EntitySortingParameter.Types.POPULARITY, SortOrders.DESC))); // Secondary sorting: Sort on popularity, descending order

		final var response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);

		System.out.println("categorylisting-sorting (end) ");
	}

	private void categoryListingFilterExample(final String categoryName) throws Loop54Exception
	{
		System.out.println("categorylisting-filter: ");
		System.out.println("items: ");

		// CODE SAMPLE categorylisting-filter BEGIN
		// Category listing with filters
		final var request = new GetEntitiesByAttributeRequest("Category", categoryName)
				// Filter the products in the category
				// In this case, we only want products that have got
				// the price attribute, and where the organic attribute is set to "True"
				.withResults(r -> r.filter(new AndFilterParameter(
						new AttributeExistsFilterParameter("Price"),
						//Because the organic attribute is stored as a string in the engine we need to filter with that type.
						//If it would have been stored as a boolean we would have used bool instead.
						new AttributeFilterParameter<String>("Organic", "True"))));

		final var response = loop54Client.getEntitiesByAttribute(request);
		// CODE SAMPLE END

		renderItemsExtended(response);

		System.out.println("categorylisting-filter (end) ");
	}

	private void renderItems(final GetEntitiesByAttributeResponse response)
	{
		// CODE SAMPLE render-items BEGIN
		final var results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items in this category.");

		for (final var resultItem : results)
		{
			final var productId = resultItem.id;

			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle);
			//render a product on the category listing page
		}
		// CODE SAMPLE END
	}

	private void renderItemsExtended(final GetEntitiesByAttributeResponse response)
	{
		final var results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items in this category.");

		for (final var resultItem : results)
		{
			final var productId = resultItem.id;
			final var productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			final var price = resultItem.getAttributeValueOrNull("price", Double.class);
			final var organic = resultItem.getAttributeValueOrNull("organic", String.class);
			System.out.println(productId + " " + productTitle + " (" + price + " kr, " + organic + "), ");
			// render a product on the category listing page
		}
	}

	private void renderFacets(final GetEntitiesByAttributeResponse response)
	{
		// CODE SAMPLE render-distinct-facets BEGIN
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
}
