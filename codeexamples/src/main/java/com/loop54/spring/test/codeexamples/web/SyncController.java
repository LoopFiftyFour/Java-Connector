package com.loop54.spring.test.codeexamples.web;

import com.loop54.ILoop54Client;
import com.loop54.Loop54Client;
import com.loop54.Loop54Settings;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.RequestManager;
import com.loop54.user.NullClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sync")
public class SyncController {
	private final ILoop54Client loop54Client;

	@Autowired
	public SyncController(final ILoop54Client loop54Client) {
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
		final var settings = new Loop54Settings("https://helloworld.54proxy.com", "TestApiKey");
		try (var client = new Loop54Client(new RequestManager(settings), NullClientInfo::new)) {
			final var response = client.sync();
		} catch (final java.io.IOException e) {
			throw new RuntimeException("Failed to close client", e);
		}
		// CODE SAMPLE END

		System.out.println("sync (end)");
	}
}
