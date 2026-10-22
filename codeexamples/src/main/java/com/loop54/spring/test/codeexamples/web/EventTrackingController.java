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

	private final ILoop54Client loop54Client;

	@Autowired
	public EventTrackingController(final ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		//Setup
		final var productId = "1";

		// Code examples
		final var clickedEntity = createEventsExample(productId);
		customerUserIDExample(clickedEntity);
		return new ModelAndView("eventtracking");
	}

	private Entity createEventsExample(final String productId) throws Loop54Exception 
	{
		System.out.println("create-events: ");

		// CODE SAMPLE create-events BEGIN
		// Code examples

		// click event (can be called on the product page)
		final var clickedEntity = new Entity("Product", productId);
		loop54Client.createEvents(new CreateEventsRequest(new ClickEvent(clickedEntity)));

		// addtocart event (call this when a customer adds a product to cart)
		final var addToCartEntity = new Entity("Product", productId);
		loop54Client.createEvents(new CreateEventsRequest(new AddToCartEvent(addToCartEntity)));

		// purchase events (can be called when an order is processed, or on the "thank you" page)
		final var purchasedEntity = new Entity("Product", productId);
		final var purchaseEvent = new PurchaseEvent(purchasedEntity)
				.withOrderId("13t09j1g") // Optional
				.withQuantity(5) // Optional
				.withRevenue(249.0); // Optional
		loop54Client.createEvents(new CreateEventsRequest(purchaseEvent));
		// CODE SAMPLE END

		System.out.println("create-events (end) ");

		return clickedEntity;
	}

	private void customerUserIDExample(final Entity clickedEntity) throws Loop54Exception 
	{
		System.out.println("create-events-custom-user-id: ");

		// CODE SAMPLE create-events-custom-user-id BEGIN
		// How it works

		//click event with a custom user ID
		final var request = new CreateEventsRequest(new ClickEvent(clickedEntity));
		final var data = new UserMetaData("testUserID");
		loop54Client.createEvents(Loop54Client.getRequestContainer(request, data));
		// CODE SAMPLE END

		System.out.println("create-events-custom-user-id (end) ");
	}
}
