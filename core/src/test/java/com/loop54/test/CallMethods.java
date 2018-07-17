package com.loop54.test;

import com.loop54.Loop54Client;
import com.loop54.Loop54Settings;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.http.RequestManager;
import com.loop54.model.request.*;
import com.loop54.model.request.parameters.filters.AttributeFilterParameter;
import com.loop54.model.request.parameters.filters.FilterComparisonMode;
import com.loop54.model.response.*;
import com.loop54.user.UserMetaData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CallMethods {
    private Loop54Client getClient() {
        Loop54Settings settings = new Loop54Settings("https://helloworld.54proxy.com", null, true, 5000);
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

        assertTrue(response.queries.count > 0, response.queries.count + "");
        assertFalse(response.queries.items.isEmpty(), response.queries.items.size() + "");
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
        GetRelatedEntitiesResponse response = getClient().getRelatedEntities(Loop54Client.getRequestContainer(new GetRelatedEntitiesRequest("Product", "13"), createMetaData()));
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
}
