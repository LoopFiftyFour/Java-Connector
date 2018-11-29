package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.*;
import com.loop54.user.*;
import com.loop54.http.*;
import com.loop54.model.response.*;
import com.loop54.model.Entity;
import com.loop54.model.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sync")
public class SyncController {
	private ILoop54Client loop54Client;

	@Autowired
	public SyncController(ILoop54Client loop54Client) {
		this.loop54Client = loop54Client;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() throws Loop54Exception {
		
		syncExample();

		return new ModelAndView("sync");
	}

	private void syncExample() throws Loop54Exception
	{
		System.out.println("sync:");
		
		// CODE SAMPLE sync BEGIN
		// Set up a Loop54Client with an Api key and a null client info (we don't need metadata about the user here)
		Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com", "TestApiKey");
        ILoop54Client client = new Loop54Client(new RequestManager(settings), () -> new NullClientInfo());
		
		Response response = client.sync();
		// CODE SAMPLE END

		System.out.println("sync (end)");
	}
}