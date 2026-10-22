package com.loop54;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.RequestManager;
import com.loop54.model.Entity;
import com.loop54.model.request.AutoCompleteRequest;
import com.loop54.model.request.GetBasketRecommendationsRequest;
import com.loop54.model.request.GetComplementaryEntitiesRequest;
import com.loop54.model.request.GetEntitiesByAttributeRequest;
import com.loop54.model.request.GetEntitiesRequest;
import com.loop54.model.request.GetPopularEntitiesRequest;
import com.loop54.model.request.GetRecentEntitiesRequest;
import com.loop54.model.request.GetRecommendedEntitiesRequest;
import com.loop54.model.request.GetRelatedEntitiesRequest;
import com.loop54.model.request.Request;
import com.loop54.model.request.RequestAliasData;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.SuggestRequest;
import com.loop54.model.request.parameters.EntitySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.request.parameters.facets.DistinctFacetItemSortingParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.request.parameters.filters.FilterComparisonMode;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.EntityCollection;
import com.loop54.user.NullClientInfo;
import com.loop54.user.UserMetaData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallMethodsTest {
    private Loop54Client getClient() {
        final var settings = new Loop54Settings("https://helloworld.54proxy.com", "TestApiKey", true, 5000);
        return new Loop54Client(new RequestManager(settings), NullClientInfo::new);
    }

    private UserMetaData createMetaData() {
        final var metaData = new UserMetaData("testUser");
        metaData.ipAddress = "0.0.0.0";
        return metaData;
    }

    @Test
    void autoCompleteHasResults() throws Loop54Exception {
        for (final var query : Arrays.asList("b", "be", "bee", "beef", "c", "ch", "chi", "chic", "chick", "chicke", "chicken")) {
            autoComplete(query);
        }
    }

    private void autoComplete(final String query) throws Loop54Exception {
        final var response = getClient().autoComplete(Loop54Client.getRequestContainer(new AutoCompleteRequest(query), createMetaData()));

        assertTrue(response.queries.count > 0, "\"" + query + "\" returned a count of " + response.queries.count);
        assertFalse(response.queries.items.isEmpty(), "\"" + query + "\" returned no items");
    }

    @Test
    @Disabled("Endpoint does not exist yet")
    void suggestHasResults() throws Loop54Exception {
        for (final var query : Arrays.asList("b", "be", "bee", "beef", "c", "ch", "chi", "chic", "chick", "chicke", "chicken")) {
            final var response = getClient().suggest(Loop54Client.getRequestContainer(new SuggestRequest(query), createMetaData()));

            assertTrue(response.queries.count > 0, "\"" + query + "\" returned a count of " + response.queries.count);
            assertFalse(response.queries.items.isEmpty(), "\"" + query + "\" returned no items");
        }

        final var request = new SuggestRequest("steak").withResults(r -> r.page(0, 10));
        final var response = getClient().suggest(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0, response.results.count + "");
        assertFalse(response.results.items.isEmpty(), response.results.items.size() + "");
    }

    @Test
    void searchHasResults() throws Loop54Exception {
        search("steak");
        search("chicken breast");
    }

    private void search(final String query) throws Loop54Exception {
        final var response = getClient().search(Loop54Client.getRequestContainer(new SearchRequest(query), createMetaData()));

        assertTrue(response.results.count > 0, response.results.count + "");
        assertFalse(response.results.items.isEmpty(), response.results.items.size() + "");
        assertTrue(response.relatedResults.count > 0, response.relatedResults.count + "");
        assertFalse(response.relatedResults.items.isEmpty(), response.relatedResults.items.size() + "");
    }

    @Test
    void searchLimits() throws Loop54Exception {
        for (final var query : Arrays.asList("steak", "chicken breast")) {
            for (final int count : Arrays.asList(2, 5, 10, 100)) {
                searchLimits(query, count);
            }
        }
    }

    private void searchLimits(final String query, final int count) throws Loop54Exception {
        final var searchRequest = new SearchRequest(query).withResults(r -> r.page(0, count));

        final var response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));

        assertCount(response.results, count);
    }

    @Test
    void searchWithCustomData() throws Loop54Exception {
        final var searchRequest = new SearchRequest("banana").withCustomData("message", "ping");
        final var response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));
        final var responseMessage = response.getCustomDataOrDefault("responseMessage", String.class);
        assertEquals("pong", responseMessage);
    }
    
    @Test
    void searchWithSortedFacets() throws Loop54Exception {
        final var facetName = "Manufacturer";
        // Add facets to the search request, with the facet options sorted by count, descending
        final var countDesc = new DistinctFacetItemSortingParameter(DistinctFacetItemSortingParameter.Types.COUNT, SortOrders.DESC);
        final var searchRequest = new SearchRequest("a").withResults(r -> r.distinctFacet(facetName, null, null, List.of(countDesc)));
        final var response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));
        assertTrue(response.results.count > 0, response.results.count + "");
        assertFalse(response.results.items.isEmpty(), response.results.items.size() + "");
        assertFalse(response.results.facets.isEmpty(), response.results.facets.size() + "");
        
        final var facet = response.results.getDistinctFacetByName(facetName);
        assertNotNull(facet, "Expected facet was not found");
        assertTrue(facet.hasValues(), "Expected facet had no values");
        
        final var facetItems = facet.getItems();
        
        // Sort the facet list in descending count order
        final List<DistinctFacet.DistinctFacetItem> facetItemsToSort = new ArrayList<>(facet.getItems());
        
        facetItemsToSort.sort((result1, result2) -> Integer.compare(result2.count, result1.count));
        
        // The facet list should already have been sorted, so no difference after sorting.
        assertIterableEquals(facetItems, facetItemsToSort);
    }

    private void assertCount(final EntityCollection results, final int desiredCount) {
        // do not return more than exist
        assertTrue(results.items.size() <= results.count,
                "The engine returned more results than the engine reported existed " +
                "(" + results.items.size() + " vs " + results.count + ")");

        // do not return more than we asked for
        assertTrue(results.items.size() <= desiredCount,
                "The engine returned more results than we asked for " +
                "(" + results.items.size() + " vs " + desiredCount + ")");

        // if there are more than we asked for
        if (results.count > desiredCount) {
            assertEquals(results.items.size(), desiredCount); //return exactly as many as we asked for
        } else {
            assertEquals(results.items.size(), results.count); //return exactly as many as exist
        }
    }

    @Test
    void getRelatedEntitiesHasResults() throws Loop54Exception {
        //Should be a wheat flour
        final var request = new GetRelatedEntitiesRequest("Product", "13");
        
        final var response = getClient().getRelatedEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    @Disabled
    void getComplementaryEntitiesHasResults() throws Loop54Exception {
        // wheat flour (13) is often bought with cinnamon roll (2)
        final var request = new GetComplementaryEntitiesRequest("Product", "13");
        
        final var response = getClient().getComplementaryEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    @Disabled
    void getBasketRecommendationsHasResults() throws Loop54Exception {
        // wheat flour (13) is often bought with cinnamon roll (2)
        final var request = new GetBasketRecommendationsRequest(List.of(new Entity("Product", "2")));
        
        final var response = getClient().getBasketRecommendations(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    void getRecommendedEntitiesHasResults() throws Loop54Exception {
        final var request = new GetRecommendedEntitiesRequest();
        
        final var response = getClient().getRecommendedEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    void getPopularEntitiesHasResults() throws Loop54Exception {
        final var request = new GetPopularEntitiesRequest("click");
        
        final var response = getClient().getPopularEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    void getRecentEntitiesHasResults() throws Loop54Exception {
        final var request = new GetRecentEntitiesRequest("click");
        
        final var response = getClient().getRecentEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    void getEntitiesHasResults() throws Loop54Exception {
        //Should result in an expensive steak
        final var request = new GetEntitiesRequest()
                .withResults(r -> r.filter(new AttributeFilterParameter<>("Price", 100.0)
                        .withComparisonMode(FilterComparisonMode.GREATER_THAN_OR_EQUALS)));

        final var response = getClient().getEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }
    
    @Test
    void getEntitiesByAttributeHasResults() throws Loop54Exception {
        //Should result in two flour products
        final var request = new GetEntitiesByAttributeRequest("Manufacturer", "Grinders inc")
                .withRequestAlias(new RequestAliasData("ManufacturerAlias", "GrindersAlias",
                        "This request was generated by the Java Connector tests."));
        final var response = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
    }

    @Test
    void getEntitiesByAttributeHasResultsMulti() throws Loop54Exception {
        //Should result in two flour products
        final var request = new GetEntitiesByAttributeRequest("Manufacturer", "Grinders inc");
        final var response = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
        
        
        final var request2 = new GetEntitiesByAttributeRequest("Manufacturer", new String[] {"Grinders inc", "Happy birds"});
        final var response2 = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request2, createMetaData()));
        assertTrue(response2.results.count > response.results.count); //one more manufacturer should yield more results
        assertFalse(response2.results.items.isEmpty());
    }
    
    @Test
    void getEntitiesByAttributeSort() throws Loop54Exception {
        final var request = new GetEntitiesByAttributeRequest("Category", "meat")
                .withResults(r -> r.sortBy(new EntitySortingParameter("Price", SortOrders.DESC))); // Primary sorting: Sort on attribute Price, descending order
        final var response = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertFalse(response.results.items.isEmpty());
        
        final var items = response.results.items;
        
        // Sort the item list in descending price order
        final List<Entity> itemsToSort = new ArrayList<>(response.results.items);
        itemsToSort.sort((result1, result2) -> {
            final var price1 = result1.getAttributeValueOrNull("price", Double.class);
            final var price2 = result2.getAttributeValueOrNull("price", Double.class);
            return price2.compareTo(price1);
        });
        
        // The item list should already have been sorted, so no difference after sorting.
        assertIterableEquals(items, itemsToSort);
    }
    
    @Test
    void sync() throws Loop54Exception {
        final var response = getClient().sync(Loop54Client.getRequestContainer(new Request(), createMetaData()));
    }
}
