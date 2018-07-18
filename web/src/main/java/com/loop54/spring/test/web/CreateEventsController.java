package com.loop54.spring.test.web;


import com.loop54.ILoop54ClientProvider;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.Entity;
import com.loop54.model.request.CreateEventsRequest;
import com.loop54.model.request.parameters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/createevents")
public class CreateEventsController {

    private ILoop54ClientProvider loop54Service;

    @Autowired
    public CreateEventsController(ILoop54ClientProvider loop54Service) {
        this.loop54Service = loop54Service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() throws Loop54Exception {
        Entity entity1 = new Entity("Product", "4"); // minced meat
        Entity entity2 = new Entity("Product", "12"); // Whole chicken
        Entity entity3 = new Entity("Product", "9"); // Steak

        //Clicking and adding minced meat to cart
        createEvent(new ClickEvent(entity1));
        createEvent(new AddToCartEvent(entity1));

        //Clicking on the whole chicken
        createEvent(new ClickEvent(entity2));

        //Clicking and adding a Steak to the cart
        createEvent(new ClickEvent(entity3));
        createEvent(new AddToCartEvent(entity3));

        //Buying 2 minced meats and 3 steaks
        PurchaseEvent purchase1 = new PurchaseEvent(entity1);
        purchase1.orderId = "66";
        purchase1.quantity = 2;
        purchase1.revenue = 13.37d;
        PurchaseEvent purchase2 = new PurchaseEvent(entity3);
        purchase2.orderId = "66";
        purchase2.quantity = 3;
        purchase2.revenue = 25d;
        createEvent(purchase1, purchase2);

        return new ModelAndView("createevents");
    }

    private void createEvent(Event... events) throws Loop54Exception{
        CreateEventsRequest request = new CreateEventsRequest();

        for(Event event : events) {
            request.events.add(event);
        }

        loop54Service.getNamed("english").createEvents(request);
    }
}
