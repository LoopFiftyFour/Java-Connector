package com.loop54.spring.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.CreateEventsRequest;
import com.loop54.model.request.parameters.AddToCartEvent;
import com.loop54.model.request.parameters.ClickEvent;
import com.loop54.model.request.parameters.Event;
import com.loop54.model.request.parameters.PurchaseEvent;

@Controller
@RequestMapping("/createevents")
public class CreateEventsController {

    private final ILoop54ClientProvider loop54Service;

    @Autowired
    public CreateEventsController(final ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        final var entity1 = new Entity("Product", "4"); // minced meat
        final var entity2 = new Entity("Product", "12"); // Whole chicken
        final var entity3 = new Entity("Product", "9"); // Steak

        //Clicking and adding minced meat to cart
        createEvents(new ClickEvent(entity1));
        createEvents(new AddToCartEvent(entity1));

        //Clicking on the whole chicken
        createEvents(new ClickEvent(entity2));

        //Clicking and adding a Steak to the cart
        createEvents(new ClickEvent(entity3));
        createEvents(new AddToCartEvent(entity3));

        //Buying 2 minced meats and 3 steaks
        createEvents(
                new PurchaseEvent(entity1).withOrderId("66").withQuantity(2).withRevenue(13.37d),
                new PurchaseEvent(entity3).withOrderId("66").withQuantity(3).withRevenue(25d)
		);

        return new ModelAndView("createevents");
    }

    private void createEvents(final Event... events) throws Loop54Exception {
        loop54Service.getNamed("english").createEvents(new CreateEventsRequest().withEvents(events));
    }
}
