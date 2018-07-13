package com.loop54.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EntityAttributeType {
    @JsonProperty("string")
    STRING,

    @JsonProperty("boolean")
    BOOLEAN,

    @JsonProperty("integer")
    INTEGER,

    @JsonProperty("number")
    NUMBER,

    @JsonProperty("array")
    ARRAY,

    @JsonProperty("object")
    OBJECT,

    @JsonProperty("date")
    DATE,
}
