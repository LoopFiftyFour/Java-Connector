package com.loop54;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.RequestManager;
import com.loop54.model.Entity;
import com.loop54.model.request.*;
import com.loop54.model.request.parameters.EntitySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.request.parameters.facets.DistinctFacetItemSortingParameter;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.request.parameters.filters.FilterComparisonMode;
import com.loop54.model.response.*;
import com.loop54.model.response.DistinctFacet.DistinctFacetItem;
import com.loop54.user.UserMetaData;
import com.loop54.user.NullClientInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CallMethodsTest {
    private Loop54Client getClient() {
        Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com", "TestApiKey", true, 5000);
        return new Loop54Client(new RequestManager(settings), () -> new NullClientInfo());
    }

    private UserMetaData createMetaData() {
        UserMetaData metaData = new UserMetaData("testUser");
        metaData.ipAddress = "0.0.0.0";
        return metaData;
    }

    @Test
    public void autoCompleteHasResults() throws Loop54Exception {
        for (String query : Arrays.asList("b", "be", "bee", "beef", "c", "ch", "chi", "chic", "chick", "chicke", "chicken"))
            autoComplete(query);
    }

    private void autoComplete(String query) throws Loop54Exception {
        AutoCompleteResponse response = getClient().autoComplete(Loop54Client.getRequestContainer(new AutoCompleteRequest(query), createMetaData()));

        assertTrue(response.queries.count > 0, "\"" + query + "\" returned a count of " + response.queries.count);
        assertFalse(response.queries.items.isEmpty(), "\"" + query + "\" returned no items");
    }

    @Test
    public void searchHasResults() throws Loop54Exception {
        search("steak");
        search("chicken breast");
    }

    private void search(String query) throws Loop54Exception {
        SearchResponse response = getClient().search(Loop54Client.getRequestContainer(new SearchRequest(query), createMetaData()));

        assertTrue(response.results.count > 0, response.results.count + "");
        assertFalse(response.results.items.isEmpty(), response.results.items.size() + "");
        assertTrue(response.relatedResults.count > 0, response.relatedResults.count + "");
        assertFalse(response.relatedResults.items.isEmpty(), response.relatedResults.items.size() + "");
    }

    @Test
    public void searchLimits() throws Loop54Exception {
        for (String query : Arrays.asList("steak", "chicken breast"))
            for (int count : Arrays.asList(2, 5, 10, 100))
                searchLimits(query, count);
    }

    private void searchLimits(String query, int count) throws Loop54Exception {
        SearchRequest searchRequest = new SearchRequest(query);
        searchRequest.resultsOptions.skip = 0;
        searchRequest.resultsOptions.take = count;

        SearchResponse response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));

        assertCount(response.results, count);
    }

    @Test
    public void searchWithCustomData() throws Loop54Exception {
        SearchRequest searchRequest = new SearchRequest("banana");
        searchRequest.addCustomData("message", "ping");
        SearchResponse response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));
        String responseMessage = response.getCustomDataOrDefault("responseMessage", String.class);
        Assertions.assertEquals("pong", responseMessage);
    }
    
    @Test
    public void searchWithSortedFacets() throws Loop54Exception {
    	String facetName = "Manufacturer";
        SearchRequest searchRequest = new SearchRequest("a");
		// Add facets to the search request
        ArrayList<DistinctFacetItemSortingParameter> sortBy = new ArrayList<>();
        sortBy.add(new DistinctFacetItemSortingParameter(){{ type = DistinctFacetItemSortingParameter.Types.COUNT; order = SortOrders.DESC; }});
        searchRequest.resultsOptions.addDistinctFacet(facetName, null, null, sortBy);
        SearchResponse response = getClient().search(Loop54Client.getRequestContainer(searchRequest, createMetaData()));
        assertTrue(response.results.count > 0, response.results.count + "");
        assertFalse(response.results.items.isEmpty(), response.results.items.size() + "");
        assertFalse(response.results.facets.isEmpty(), response.results.facets.size() + "");
        
        DistinctFacet facet = response.results.getDistinctFacetByName(facetName);
        assertNotNull(facet, "Expected facet was not found");
        assertTrue(facet.hasValues(), "Expected facet had no values");
        
        List<DistinctFacetItem> facetItems = facet.getItems();
        
        // Sort the facet list in descending count order
        List<DistinctFacetItem> facetItemsToSort = new ArrayList<>();
        facetItemsToSort.addAll(facet.getItems());    
        
        Collections.sort(facetItemsToSort, new Comparator<DistinctFacetItem>() {
            public int compare(DistinctFacetItem result1, DistinctFacetItem result2) {
                if(result1.count > result2.count)
                	return -1;
                if(result1.count < result2.count)
                	return 1;
                return 0;
            }
        });
        
        // The facet list should already have been sorted, so no difference after sorting.
        assertIterableEquals(facetItems, facetItemsToSort);
    }

    private void assertCount(EntityCollection results, int desiredCount) {
        // do not return more than exist
        assertTrue(results.items.size() <= results.count,
                "The engine returned more results than the engine reported existed " +
                "(" + results.items.size() + " vs " + results.count + ")");

        // do not return more than we asked for
        assertTrue(results.items.size() <= desiredCount,
                "The engine returned more results than we asked for " +
                "(" + results.items.size() + " vs " + desiredCount + ")");

        // if there are more than we asked for
        if (results.count > desiredCount)
            assertEquals(results.items.size(), desiredCount); //return exactly as many as we asked for
        else
            assertEquals(results.items.size(), results.count); //return exactly as many as exist
    }

    @Test
    public void getRelatedEntitiesHasResults() throws Loop54Exception {
        //Should be a wheat flour
        GetRelatedEntitiesRequest request = new GetRelatedEntitiesRequest("Product", "13");
        
        GetRelatedEntitiesResponse response = getClient().getRelatedEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
    }

    @Disabled("Not released to HelloWorld engine yet")
    @Test
    public void getComplementaryEntitiesHasResults() throws Loop54Exception {
        //Should be a wheat flour
        GetComplementaryEntitiesRequest request = new GetComplementaryEntitiesRequest("Product", "13");
        
        GetComplementaryEntitiesResponse response = getClient().getComplementaryEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
    }

    @Disabled("Not released to HelloWorld engine yet")
    @Test
    public void getBasketRecommendationsHasResults() throws Loop54Exception {
        List<Entity> entities = Arrays.asList(new Entity("Product", "26397727"));

        GetBasketRecommendationsRequest request = new GetBasketRecommendationsRequest(entities);
        
        GetBasketRecommendationsResponse response = getClient().getBasketRecommendations(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
    }

    @Test
    public void getEntitiesHasResults() throws Loop54Exception {
        //Should result in an expensive steak
        GetEntitiesRequest request = new GetEntitiesRequest();

        AttributeFilterParameter<Double> filter = new AttributeFilterParameter<>("Price", 100.0);
        filter.comparisonMode = FilterComparisonMode.GREATER_THAN_OR_EQUALS;

        request.resultsOptions.filter = filter;

        GetEntitiesResponse response = getClient().getEntities(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
    }

    @Test
    public void getEntitiesByAttributeHasResults() throws Loop54Exception {
        //Should result in two flour products
        GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Manufacturer", "Grinders inc");
        GetEntitiesByAttributeResponse response = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
    }
    
    @Test
    public void getEntitiesByAttributeSort() throws Loop54Exception {
        GetEntitiesByAttributeRequest request = new GetEntitiesByAttributeRequest("Category", "meat");
        request.resultsOptions.sortBy = new ArrayList<>();
 		request.resultsOptions.sortBy.add(new EntitySortingParameter("Price"){{ order = SortOrders.DESC; }}); // Primary sorting: Sort on attribute Price, descending order
        GetEntitiesByAttributeResponse response = getClient().getEntitiesByAttribute(Loop54Client.getRequestContainer(request, createMetaData()));
        assertTrue(response.results.count > 0);
        assertTrue(response.results.items.size() > 0);
        
        List<Entity> items = response.results.items;
        
        // Sort the item list in descending price order
        List<Entity> itemsToSort = new ArrayList<>();
        itemsToSort.addAll(response.results.items);    
        Collections.sort(itemsToSort, new Comparator<Entity>() {
            public int compare(Entity result1, Entity result2) {
            	Double price1 = result1.getAttributeValueOrNull("price", Double.class);
            	Double price2 = result2.getAttributeValueOrNull("price", Double.class);
                if(price1 > price2)
                	return -1;
                if(price1 < price2)
                	return 1;
                return 0;
            }
        });    
        
        // The item list should already have been sorted, so no difference after sorting.
        assertIterableEquals(items, itemsToSort);
    }
	
    @Test
    public void sync() throws Loop54Exception {
        Response response = getClient().sync(Loop54Client.getRequestContainer(new Request(), createMetaData()));
    }
}
