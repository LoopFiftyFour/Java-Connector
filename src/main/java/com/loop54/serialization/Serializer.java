package com.loop54.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loop54.exceptions.Loop54Exception;
import com.loop54.exceptions.SerializationException;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

public class Serializer {
    private static final ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

    public static String serialize(Object data) throws Loop54Exception {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException jpe) {
            throw new SerializationException("Could not deserialize object of type " + data.getClass().getName(), jpe);
        }
    }

    public static <T> T deserialize(String data, Class<T> clazz) throws Loop54Exception {
        try {
            return MAPPER.readValue(data, clazz);
        } catch (IOException ioe) {
            throw new SerializationException("Could not deserialize object of type " + clazz.getName(), ioe);
        }
    }
}
