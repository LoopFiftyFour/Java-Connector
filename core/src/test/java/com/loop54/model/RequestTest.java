package com.loop54.model;

import com.loop54.model.request.Request;
import com.loop54.model.request.parameters.EntityCollectionParameters;
import com.fasterxml.jackson.databind.JsonNode;
import com.loop54.exceptions.Loop54ArgumentException;
import com.loop54.model.request.SearchRequest;
import com.loop54.model.request.parameters.TypedEntityCollectionParameters;
import com.loop54.serialization.Serializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestTest {
    @Test
    public void addCustomData() {
        Request request = new Request();

        String stringValue = "Hjalmar Söderberg";
        double doubleValue = 13.37d;
        EntityCollectionParameters complexValue = new EntityCollectionParameters();
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
    public void addCustomDataMultipleTimes() {
        Request request = new Request();

        String stringValue = "Hjalmar Söderberg";
        String stringValueAgain = "August Strindberg";

        request.addCustomData("stringKey", stringValue);
        request.addCustomData("stringKey", stringValueAgain);

        Assertions.assertEquals(stringValueAgain, request.customData.get("stringKey"));
    }

    @Test
    public void addCustomDataCaseSensitivity() {
        Request request = new Request();

        String stringValue = "Hjalmar Söderberg";
        String stringValue2 = "August Strindberg";

        request.addCustomData("stringKey", stringValue);
        request.addCustomData("STRINGKey", stringValue2);

        Assertions.assertEquals(stringValue, request.customData.get("stringKey"));
        Assertions.assertEquals(stringValue2, request.customData.get("STRINGKey"));
    }

    @Test
    public void searchRequestSerializesContentResultsOptions() throws Exception {
        SearchRequest request = new SearchRequest("shoes");
        request.addContentResultsOptions("Brand").take = 3;
        request.addContentResultsOptions("Article").skip = 5;

        JsonNode options = Serializer.MAPPER.readTree(Serializer.serialize(request)).get("contentResultsOptions");
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("Brand", options.get(0).get("entityType").asText());
        Assertions.assertEquals(3, options.get(0).get("take").asInt());
        Assertions.assertEquals("Article", options.get(1).get("entityType").asText());
        Assertions.assertEquals(5, options.get(1).get("skip").asInt());
    }

    @Test
    public void searchRequestWithoutContentOptionsOmitsField() throws Exception {
        JsonNode json = Serializer.MAPPER.readTree(Serializer.serialize(new SearchRequest("shoes")));
        Assertions.assertFalse(json.has("contentResultsOptions"));
    }

    @Test
    public void typedEntityCollectionParametersRequiresEntityType() {
        Assertions.assertThrows(Loop54ArgumentException.class, () -> new TypedEntityCollectionParameters(null));
        Assertions.assertThrows(Loop54ArgumentException.class, () -> new TypedEntityCollectionParameters(" "));
    }

    @Test
    public void addContentResultsOptionsTwiceReturnsSameEntry() {
        SearchRequest request = new SearchRequest("summit");
        request.addContentResultsOptions("Brand").take = 2;
        request.addContentResultsOptions("Brand").skip = 1;
        request.addContentResultsOptions("Article");

        Assertions.assertEquals(2, request.contentResultsOptions.size(), "Brand should not be added twice");
        Assertions.assertEquals("Brand", request.contentResultsOptions.get(0).entityType);
        Assertions.assertEquals(2, request.contentResultsOptions.get(0).take);
        Assertions.assertEquals(1, request.contentResultsOptions.get(0).skip);
        Assertions.assertEquals("Article", request.contentResultsOptions.get(1).entityType);
    }
}
