package com.loop54.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.Facet;
import com.loop54.model.response.RangeFacet;

import java.io.IOException;

public class FacetJsonDeserializer extends JsonDeserializer<Facet> {

    @Override
    public Facet deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        String type = node.get("type").asText();
        if ("distinct".equals(type))
            return deserializeDistinct(node);
        else if ("range".equals(type))
            return deserializeRange(node);

        throw new IllegalArgumentException("FacetType '" + type + "' cannot be deserialized");
    }

    private DistinctFacet deserializeDistinct(JsonNode node) {
        return Serializer.MAPPER.convertValue(node, DistinctFacet.class);
    }

    private RangeFacet deserializeRange(JsonNode node) {
        return Serializer.MAPPER.convertValue(node, RangeFacet.class);
    }
}
