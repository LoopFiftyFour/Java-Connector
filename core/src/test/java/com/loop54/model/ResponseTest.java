package com.loop54.model;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.model.response.ContentResponse;
import com.loop54.model.response.EntityCollection;
import com.loop54.model.response.Response;
import com.loop54.model.response.SearchResponse;
import com.loop54.model.response.SuggestResponse;
import com.loop54.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class ResponseTest {

    private static final String RESPONSE_JSON_WITH_CUSTOM_DATA = "{ \"customData\": { \"stringData\": \"Hjalmar Söderberg\", " +
            "\"doubleData\": 13.37, \"complexData\": { \"count\": 3, \"facets\": [], \"items\": [{\"id\": \"sku-123\"," +
            " \"type\": \"product\"}] }, \"listData\": [1, 2, 3] } }";

    private static final String RESPONSE_JSON_WITHOUT_CUSTOM_DATA = "{}";

    @Test
    void getCustomDataOrDefault() throws Loop54Exception {
        final var responseObject = getResponse(RESPONSE_JSON_WITH_CUSTOM_DATA);

        Assertions.assertEquals("Hjalmar Söderberg", responseObject.getCustomDataOrDefault("stringData", String.class));
        Assertions.assertEquals(13.37d, (double)responseObject.getCustomDataOrDefault("doubleData", Double.class));

        final var complex = responseObject.getCustomDataOrDefault("complexData", EntityCollection.class);
        Assertions.assertEquals(3, complex.count);
        Assertions.assertEquals(0, complex.facets.size());
        Assertions.assertEquals(1, complex.items.size());
        Assertions.assertEquals("sku-123", complex.items.get(0).id);
        Assertions.assertEquals("product", complex.items.get(0).type);

        final List<Integer> list = responseObject.getCustomDataOrDefault("listData", List.class);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, (int)list.get(0));
        Assertions.assertEquals(2, (int)list.get(1));
        Assertions.assertEquals(3, (int)list.get(2));

        Assertions.assertNull(responseObject.getCustomDataOrDefault("nonExistingData", String.class)); //Should not throw, but return null
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrDefault("complexData", Integer.class)); //Should fail to deserialize
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrDefault("stringData", Integer.class)); //Should fail to cast
    }

    @Test
    void getCustomDataOrDefaultNoCustomData() throws Loop54Exception{
        final var responseObject = getResponse(RESPONSE_JSON_WITHOUT_CUSTOM_DATA);

        Assertions.assertNull(responseObject.getCustomDataOrDefault("stringData", String.class));
        Assertions.assertNull(responseObject.getCustomDataOrDefault("doubleData", Double.class));
        Assertions.assertNull(responseObject.getCustomDataOrDefault("complexData", EntityCollection.class));
        Assertions.assertNull(responseObject.getCustomDataOrDefault("listData", List.class));
    }

    @Test
    void getCustomDataOrThrow() throws Loop54Exception {
        final var responseObject = getResponse(RESPONSE_JSON_WITH_CUSTOM_DATA);

        Assertions.assertEquals("Hjalmar Söderberg", responseObject.getCustomDataOrThrow("stringData", String.class));
        Assertions.assertEquals(13.37d, (double)responseObject.getCustomDataOrThrow("doubleData", Double.class));

        final var complex = responseObject.getCustomDataOrThrow("complexData", EntityCollection.class);
        Assertions.assertEquals(3, complex.count);
        Assertions.assertEquals(0, complex.facets.size());
        Assertions.assertEquals(1, complex.items.size());
        Assertions.assertEquals("sku-123", complex.items.get(0).id);
        Assertions.assertEquals("product", complex.items.get(0).type);

        final List<Integer> list = responseObject.getCustomDataOrThrow("listData", List.class);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(1, (int)list.get(0));
        Assertions.assertEquals(2, (int)list.get(1));
        Assertions.assertEquals(3, (int)list.get(2));

        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("nonExistingData", String.class)); //Should throw because it doesn't exist
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("complexData", Integer.class)); //Should fail to deserialize
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("stringData", Integer.class)); //Should fail to cast
    }

    @Test
    void getCustomDataOrThrowNoCustomData() throws Loop54Exception{
        final var responseObject = getResponse(RESPONSE_JSON_WITHOUT_CUSTOM_DATA);

        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("stringData", String.class));
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("doubleData", Double.class));
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("complexData", EntityCollection.class));
        Assertions.assertThrows(Loop54Exception.class, () -> responseObject.getCustomDataOrThrow("listData", List.class));
    }

    private static final String SEARCH_RESPONSE_WITH_CONTENT = "{ \"makesSense\": true, \"results\": { \"count\": 0, \"facets\": [], \"items\": [] }, " +
            "\"contentSearchResults\": [ { \"entityType\": \"Brand\", \"results\": { \"count\": 1, \"facets\": [], " +
            "\"items\": [ { \"id\": \"b-1\", \"type\": \"Brand\" } ] } } ] }";

    @ParameterizedTest
    @ValueSource(classes = { SearchResponse.class, SuggestResponse.class })
	@DisplayName("Responses for requests supporting Content Search deserialize it's result")
    void contentResponseDeserializesContentSearchResults(final Class<? extends ContentResponse> responseClass) throws Loop54Exception {
        final var response = Serializer.deserialize(SEARCH_RESPONSE_WITH_CONTENT, responseClass);
        Assertions.assertEquals(1, response.contentSearchResults.size());

        final var brands = response.getContentSearchResults("Brand");
        Assertions.assertEquals(1, brands.count);
        Assertions.assertEquals("b-1", brands.items.get(0).id);
        Assertions.assertNull(response.getContentSearchResults("Article"));
    }

	@ParameterizedTest
	@ValueSource(classes = { SearchResponse.class, SuggestResponse.class })
	@DisplayName("Responses for requests supporting Content Search don't return 'contentSearchResults', when no results are available")
    void contentResponseWithoutContentResults(final Class<? extends ContentResponse> responseClass) throws Loop54Exception {
        final var response = Serializer.deserialize("{ \"makesSense\": true }", responseClass);
        Assertions.assertNull(response.contentSearchResults);
        Assertions.assertNull(response.getContentSearchResults("Brand"));
    }

    private Response getResponse(final String responseJson) throws Loop54Exception {
        return Serializer.deserialize(responseJson, Response.class);
    }
}
