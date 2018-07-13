package com.loop54.model.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SortOrders {
    @JsonProperty("asc")
    ASC,

    @JsonProperty("desc")
    DESC,
}
