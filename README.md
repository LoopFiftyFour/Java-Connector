
# Java-Connector
Java Wrapper for Loop54 JSON V3 API

## How to install
Either download any of these jars and import them into you project:

* [loop54-core-1.0.0.jar](http://files.loop54.com/files/libs/loop54-core-1.0.0.jar) - **Core** functionallity **without** it's dependencies
* [loop54-core-all-1.0.0.jar](http://files.loop54.com/files/libs/loop54-core-all-1.0.0.jar) - **Core** functionallity **with** all it's dependencies 
* [loop54-spring-1.0.0.jar](http://files.loop54.com/files/libs/loop54-spring-1.0.0.jar) - **Core** functionallity **plus Spring** specific implementation **without** it's dependencies
* [loop54-spring-all-1.0.0.jar](http://files.loop54.com/files/libs/loop54-spring-all-1.0.0.jar) - **Core** functionallity **plus Spring** specific implementation **with** all it's dependencies

Or download the source and reference the core and spring modules and build it using gradle.

Requires Java 1.8 or later. If running Java 1.7 or under please use an older version of the library available in the 
GitHub history.

Javadoc for the latest release is available [here](http://files.loop54.com/files/javadoc/core/index.html)

## How to use
The Loop54 Connector is easily configured if running Spring.

### Spring
Simply add the following code to the Spring configuration class marked with `@Configuration`:

    @Bean
    public ILoop54Client loop54Client() {
        Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com");
        return new Loop54Client(
            new RequestManager(settings),
            new SpringRemoteClientInfoProvider()); //Will use Spring classes to extract user data
    }

An `ILoop54Client` is then injectable by using the `@Autowire` annotation

#### Multiple instances

If you are using multiple instances of the Loop54 engine within you application you instead need to do the following:

	@Bean
    public ILoop54ClientProvider loop54ClientProvider() {
        return new Loop54ClientProvider(
                new SpringRemoteClientInfoProvider(),
                Loop54SettingsCollection.create()
                        .add("english", "https://helloworld-en.54proxy.com")
                        .add("swedish", "https://helloworld-se.54proxy.com"));
    }

And then inject the `ILoop54ClientProvider` interface into your application with the `@Autowire` annotation. And obtain 
a specific instance using the `getNamed` method with the same name as above:

	ILoop54Client loop54Client = clientProvider.getNamed("swedish");

### Using the `ILoop54Client`
The `ILoop54Client` contains methods for making all public API calls to the Loop54 e-commerce search engine. It contains 
both synchronous and asynchronous variants of all methods.

    String searchQuery = "banana";
    SearchRequest request = new SearchRequest(searchQuery);
    SearchResponse response = loop54Client.search(request);
    
To add parameters to the `SearchRequest` you could do this:

    request.resultsOptions.skip = 0;
    request.resultsOptions.take = 20;
    request.resultsOptions.addDistinctFacet("Manufacturer");
    request.resultsOptions.addRangeFacet("Price");

All other request types also have their own types to use:

    CreateEventsRequest createEventRequest = new CreateEventsRequest(event);
    
    AutoCompleteRequest autoCompleteRequest = new AutoCompleteRequest(query);
    
    GetRelatedEntitiesRequest getRelatedEntitiesRequest 
        = new GetRelatedEntitiesRequest(entityType, entityId);
        
    GetEntitiesRequest getEntitiesRequest = new GetEntitiesRequest();
    
    GetEntitiesByAttributeRequest getEntitiesByAttributeRequest 
        = new GetEntitiesByAttributeRequest(attributeName, attributeValue);

### But wait! I'm not using Spring
There is still hope for you. If not using the above mentioned frameworks you can implement some of the functionality 
yourself and use the client just as well. You will need to implement the `IRemoteClientInfoProvider` interface and the 
`IRemoteClientInfo` interface and after doing that you can create a new instance of `ILoop54Client` like this:

    IRemoteClientInfoProvider myRemoteClientInfoProvider = new MySuperAwesomeCustomRemoteClientInfoProvider();
    Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com");
    ILoop54Client loop54Client = new Loop54Client(new RequestManager(settings), myRemoteClientInfoProvider);
    
And you are good to go!

## Features
- Native support for search, autoComplete, createEvent, getEntities, getEntitiesByAttribute and getRelatedEntities call. 
With intuitive APIs to call them.
- Handles user identification using random-generated cookies.
- Uses X-Forwarded-For as client IP if it's available.
- Configurable HTTP timeout.
- GZIP support.
- Relays HTTP data to engine:
    - Referer
    - UserAgent
    - Library version
    
## Building and testing

_Building, generating javadoc and running the test apps requires **JDK 1.8**._ 
It might work with later versions, but has not been tested.

First of all, make sure you have JDK installed by running (assuming Ubuntu Linux):

	java -version
	
If this tells you that java is not installed, you need to run:

	sudo apt-get update
	sudo apt install openjdk-8-jdk-headless
	
If you're on windows, download the 1.8 JDK from https://www.java.com/

To build the library simply use the supplied gradle wrapper by going to the root directory of the repository in the command line and issue the following command:

    gradlew build --info

The `--info` flag is so that you get detailed error information when tests fail, instead of just a short summary of which tests failed.

This will download gradle 5.1 and all dependencies, build all modules and run the unit tests. 

If you get an error saying `Could not determine java version from '[x]'` that means that your locally installed Java version is newer than the gradle wrapper supports. You need to either upgrade the gradle wrapper or downgrade your Java version.

If you get an error saying `Could not set unknown property '[x]'` for the `web` or `codeexamples` projects, that means that `gretty` plugin has released a new version that is not compatible with your gradle version. You need to either upgrade the gradle wrapper or specify an exact `gretty` version in the `build.gradle` cofig files in the `web` and `codeexamples` projects.

You may run into some flaky tests. Some of the tests use `helloworld.54proxy.com` instead of a mock engine. This engine can become stale (no behavior data) and this might cause some tests to fail, especially the `autoComplete` tests. To fix this, you need to search a few times for phrases beginning with the strings that fail. For instance, if the "be" test fails, search a few times for "beef" to make sure there is data in the engine.

To generate the javadoc, run:

    gradlew javadoc

Run the test application in the `web` module by using the following command:

    gradlew :web:appRun
    
And the application will start and be hosted on http://localhost:8080/web.