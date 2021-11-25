package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.GetBasketRecommendationsRequest;
import com.loop54.model.response.GetBasketRecommendationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/getbasketrecommendations")
public class GetBasketRecommendationsController {
	private ILoop54Client loop54Client;

	@Autowired
	public GetBasketRecommendationsController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		// Code examples
		getBasketRecommendationsExample();

		return new ModelAndView("getbasketrecommendations");
	}

	private void getBasketRecommendationsExample() throws Loop54Exception
	{
		System.out.println("get-basket-recommendations-full: ");

		// CODE SAMPLE get-basket-recommendations-full BEGIN
		GetBasketRecommendationsRequest request = new GetBasketRecommendationsRequest(Arrays.asList(
			new Entity("Product", "13"),
			new Entity("Product", "14")
		));

		// specify the number of response items
		request.resultsOptions.skip = 0;
		request.resultsOptions.take = 10;

		// fetch the response from the engine
		GetBasketRecommendationsResponse response = loop54Client.getBasketRecommendations(request);

		// list recommendations for the basket
		List<Entity> results = response.results.items;

		if (response.results.count == 0)
			System.out.println("There were no items.");

		for(Entity resultItem : results)
		{
			String productId = resultItem.id;
			String productTitle = resultItem.getAttributeValueOrNull("title", String.class);
			System.out.println(productId + " " + productTitle); //render a product on the search results page
		}

		// CODE SAMPLE END

		System.out.println("get-basket-recommendations-full (end) ");
	}
}
