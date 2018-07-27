package com.loop54.model;

import com.loop54.model.request.Request;
import com.loop54.model.request.parameters.EntityCollectionParameters;
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
}
