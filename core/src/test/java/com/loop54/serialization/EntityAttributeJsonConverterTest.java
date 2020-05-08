package com.loop54.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.loop54.model.EntityAttribute;
import com.loop54.model.EntityAttributeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityAttributeJsonConverterTest {
    @Test
    public void deserializeStringEntityAttribute() throws IOException {
        String entityAttributeString = "{\"name\": \"category1\", \"type\": \"string\", \"values\": [\"Toys\"]}";

        ObjectMapper mapper = Serializer.MAPPER;
        EntityAttribute attribute = mapper.readValue(entityAttributeString, EntityAttribute.class);

        assertEquals("category1", attribute.getName());
        assertEquals(EntityAttributeType.STRING, attribute.getType());
        assertEquals("Toys", attribute.getValue(String.class));
    }

    @Test
    public void deserializeEmptyStringEntityAttribute() throws IOException {
        String entityAttributeString = "{\"name\": \"category1\", \"type\": \"string\", \"values\": []}";

        ObjectMapper mapper = Serializer.MAPPER;
        EntityAttribute attribute = mapper.readValue(entityAttributeString, EntityAttribute.class);

        assertEquals("category1", attribute.getName());
        assertEquals(EntityAttributeType.STRING, attribute.getType());
        assertEquals(null, attribute.getValue(String.class));
    }

    @Test
    public void deserializeDoubleEntityAttribute() throws IOException {
        String entityAttributeString = "{\"name\": \"Price\", \"type\": \"number\", \"values\": [12, 13.37]}";

        ObjectMapper mapper = Serializer.MAPPER;
        EntityAttribute attribute = mapper.readValue(entityAttributeString, EntityAttribute.class);

        assertEquals("Price", attribute.getName());
        assertEquals(EntityAttributeType.NUMBER, attribute.getType());
        assertEquals(12, attribute.getValue(Double.class), 1e-9);
        assertEquals(12, attribute.getValues(Double.class).get(0), 1e-9);
        assertEquals(13.37, attribute.getValues(Double.class).get(1), 1e-9);
    }

    @Test
    public void deserializeEntityAttributeMissingRequired() {
        ObjectMapper mapper = Serializer.MAPPER;

        String noName = "{\"type\": \"number\", \"values\": [12, 13.37]}";
        Exception exception = assertThrows(MismatchedInputException.class, () -> mapper.readValue(noName, EntityAttribute.class));
        Assertions.assertTrue(exception.getMessage().contains("name"), exception.getMessage());

        String noType = "{\"name\": \"Price\", \"values\": [12, 13.37]}";
        exception = assertThrows(MismatchedInputException.class, () -> mapper.readValue(noType, EntityAttribute.class));
        Assertions.assertTrue(exception.getMessage().contains("type"), exception.getMessage());

        String noValues = "{\"type\": \"number\", \"name\": \"Price\"}";
        exception = assertThrows(MismatchedInputException.class, () -> mapper.readValue(noValues, EntityAttribute.class));
        Assertions.assertTrue(exception.getMessage().contains("values"), exception.getMessage());
    }
}
