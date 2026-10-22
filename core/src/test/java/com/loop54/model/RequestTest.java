package com.loop54.model;

import com.loop54.model.request.Request;
import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.loop54.exceptions.Loop54ArgumentException;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.SuggestRequest;
import com.loop54.model.request.parameters.EntitySortingParameter;
import com.loop54.model.request.parameters.QuerySortingParameter;
import com.loop54.model.request.parameters.SortOrders;
import com.loop54.model.request.parameters.TypedEntityCollectionParameters;
import com.loop54.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RequestTest {
    @Test
    void addCustomData() {
        final var request = new Request();

        final var stringValue = "Hjalmar Söderberg";
        final var doubleValue = 13.37d;
        final var complexValue = new EntityCollectionParameters();
        complexValue.skip = 0;
        complexValue.take = 10;

        request.addCustomData("stringKey", stringValue);
        request.addCustomData("doubleKey", doubleValue);
        request.addCustomData("complexKey", complexValue);

        Assertions.assertEquals(stringValue, request.customData.get("stringKey"));
        Assertions.assertEquals(doubleValue, request.customData.get("doubleKey"));
        Assertions.assertSame(complexValue, request.customData.get("complexKey"));
    }

    @Test
    void addCustomDataMultipleTimes() {
        final var request = new Request();

        final var stringValue = "Hjalmar Söderberg";
        final var stringValueAgain = "August Strindberg";

        request.addCustomData("stringKey", stringValue);
        request.addCustomData("stringKey", stringValueAgain);

        Assertions.assertEquals(stringValueAgain, request.customData.get("stringKey"));
    }

    @Test
    void addCustomDataCaseSensitivity() {
        final var request = new Request();

        final var stringValue = "Hjalmar Söderberg";
        final var stringValue2 = "August Strindberg";

        request.addCustomData("stringKey", stringValue);
        request.addCustomData("STRINGKey", stringValue2);

        Assertions.assertEquals(stringValue, request.customData.get("stringKey"));
        Assertions.assertEquals(stringValue2, request.customData.get("STRINGKey"));
    }

    @Test
    void searchRequestSerializesContentResultsOptions() throws Exception {
        final var request = new SearchRequest("shoes");
        request.addContentResultsOptions("Brand").take = 3;
        request.addContentResultsOptions("Article").skip = 5;

        final var options = Serializer.MAPPER.readTree(Serializer.serialize(request)).get("contentResultsOptions");
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("Brand", options.get(0).get("entityType").asText());
        Assertions.assertEquals(3, options.get(0).get("take").asInt());
        Assertions.assertEquals("Article", options.get(1).get("entityType").asText());
        Assertions.assertEquals(5, options.get(1).get("skip").asInt());
    }

    @Test
    void searchRequestWithoutContentOptionsOmitsField() throws Exception {
        final var json = Serializer.MAPPER.readTree(Serializer.serialize(new SearchRequest("shoes")));
        Assertions.assertFalse(json.has("contentResultsOptions"));
    }

    @Test
    void typedEntityCollectionParametersRequiresEntityType() {
        Assertions.assertThrows(Loop54ArgumentException.class, () -> new TypedEntityCollectionParameters(null));
        Assertions.assertThrows(Loop54ArgumentException.class, () -> new TypedEntityCollectionParameters(" "));
    }

    @Test
    void addContentResultsOptionsTwiceReturnsSameEntry() {
        final var request = new SearchRequest("summit");
        request.addContentResultsOptions("Brand").take = 2;
        request.addContentResultsOptions("Brand").skip = 1;
        request.addContentResultsOptions("Article");

        Assertions.assertEquals(2, request.contentResultsOptions.size(), "Brand should not be added twice");
        Assertions.assertEquals("Brand", request.contentResultsOptions.get(0).entityType);
        Assertions.assertEquals(2, request.contentResultsOptions.get(0).take);
        Assertions.assertEquals(1, request.contentResultsOptions.get(0).skip);
        Assertions.assertEquals("Article", request.contentResultsOptions.get(1).entityType);
    }

    @Test
    void searchRequestFluentApiSerializesLikeFieldAccess() throws Exception {
        final var fieldStyle = new SearchRequest("meat");
        fieldStyle.resultsOptions.skip = 0;
        fieldStyle.resultsOptions.take = 20;
        fieldStyle.resultsOptions.addDistinctFacet("Manufacturer", List.of("MeatNStuff"));
        fieldStyle.resultsOptions.addRangeFacet("Price");
        fieldStyle.resultsOptions.sortBy = List.of(new EntitySortingParameter("Price", SortOrders.DESC));
        fieldStyle.relatedResultsOptions.take = 5;
        fieldStyle.relatedResultsOptions.skip = 0;
        fieldStyle.spellingSuggestionsOptions.take = 3;
        fieldStyle.spellingSuggestionsOptions.skip = 0;
        fieldStyle.relatedQueriesOptions.take = 4;
        fieldStyle.relatedQueriesOptions.skip = 0;

        final var fluent = new SearchRequest("meat")
                .withResults(r -> r.page(0, 20)
                        .distinctFacet("Manufacturer", List.of("MeatNStuff"))
                        .rangeFacet("Price")
                        .sortBy(new EntitySortingParameter("Price", SortOrders.DESC)))
                .withRelatedResults(r -> r.page(0, 5))
                .withSpellingSuggestions(q -> q.page(0, 3))
                .withRelatedQueries(q -> q.page(0, 4));

        Assertions.assertEquals(Serializer.MAPPER.readTree(Serializer.serialize(fieldStyle)),
                Serializer.MAPPER.readTree(Serializer.serialize(fluent)));
    }

    @Test
    void suggestRequestFluentApiConfiguresOptions() {
        final var request = new SuggestRequest("banan", true)
                .withQueries(q -> q.page(1, 10).sortBy(new QuerySortingParameter(QuerySortingParameter.Types.ALPHABETIC, SortOrders.ASC)))
                .withResults(r -> r.page(2, 20).distinctFacet("Category"));

        Assertions.assertEquals(1, request.queriesOptions.skip);
        Assertions.assertEquals(10, request.queriesOptions.take);
        Assertions.assertEquals(QuerySortingParameter.Types.ALPHABETIC, request.queriesOptions.sortBy.get(0).type);
        Assertions.assertEquals(2, request.resultsOptions.skip);
        Assertions.assertEquals(20, request.resultsOptions.take);
        Assertions.assertEquals(1, request.resultsOptions.facets.size());
    }

    @Test
    void sortByAppendsInOrder() {
        final var options = new EntityCollectionParameters()
                .sortBy(new EntitySortingParameter("Price", SortOrders.DESC))
                .sortBy(new EntitySortingParameter(EntitySortingParameter.Types.RELEVANCE, SortOrders.DESC));

        Assertions.assertEquals(2, options.sortBy.size());
        Assertions.assertEquals("Price", options.sortBy.get(0).attributeName);
        Assertions.assertEquals(EntitySortingParameter.Types.RELEVANCE, options.sortBy.get(1).type);
    }
}
