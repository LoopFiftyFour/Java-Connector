package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.Loop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.CreateEventsRequest;
import com.loop54.model.request.parameters.*;
import com.loop54.user.UserMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/eventtracking")
public class EventTrackingController {

	private ILoop54Client loop54Client;

	@Autowired
	public EventTrackingController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		//Setup
		String productId = "1";

		// Code examples
		Entity clickedEntity = createEventsExample(productId);
		customerUserIDExample(clickedEntity);
		return new ModelAndView("eventtracking");
	}

	private Entity createEventsExample(String productId) throws Loop54Exception 
	{
		System.out.println("create-events: ");

		// CODE SAMPLE create-events BEGIN
		// Code examples

		// click event (can be called on the product page)
		Entity clickedEntity = new Entity("Product", productId);
		loop54Client.createEvents(new CreateEventsRequest(new ClickEvent(clickedEntity)));

		// addtocart event (call this when a customer adds a product to cart)
		Entity addToCartEntity = new Entity("Product", productId);
		loop54Client.createEvents(new CreateEventsRequest(new AddToCartEvent(addToCartEntity)));

		// purchase events (can be called when an order is processed, or on the "thank you" page)
		Entity purchasedEntity = new Entity("Product", productId);
		loop54Client.createEvents(new CreateEventsRequest(new PurchaseEvent(purchasedEntity)
		{{
			orderId = "13t09j1g"; // Optional
			quantity = 5; // Optional
			revenue = 249.0; // Optional
		}}
				));
		// CODE SAMPLE END

		System.out.println("create-events (end) ");

		return clickedEntity;
	}

	private void customerUserIDExample(Entity clickedEntity) throws Loop54Exception 
	{
		System.out.println("create-events-custom-user-id: ");

		// CODE SAMPLE create-events-custom-user-id BEGIN
		// How it works

		//click event with a custom user ID
		CreateEventsRequest request = new CreateEventsRequest(new ClickEvent(clickedEntity));
		UserMetaData data = new UserMetaData("testUserID");
		loop54Client.createEvents(Loop54Client.getRequestContainer(request, data));
		// CODE SAMPLE END

		System.out.println("create-events-custom-user-id (end) ");
	}
}
