package com.loop54.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an attribute on an entity in the loop54 search engine.
 * This could for instance be "Price", "Name" or "Category" values that belong to an entity.
 */
public class EntityAttribute {
    private String name;

    private EntityAttributeType type;

    private List<Object> values;

    @JsonCreator
    public EntityAttribute(
            @JsonProperty(required = true, value = "name") String name,
            @JsonProperty(required = true, value = "type") EntityAttributeType type,
            @JsonProperty(required = true, value = "values") List<Object> values) {
        this.name = name;
        this.type = type;
        this.values = values;
    }

    /** @return Name of the attribute. For instance "Price", "Name" or "Category". */
    public String getName() { return name; }

    public EntityAttributeType getType() { return type; }

    /**
     * Casts the first value to the specified generic type and returns it, or null if there are no values.
     * @return The first (or only) value or null if empty.
     */
    public <T> T getValue(Class<T> clazz) {
        return values.isEmpty() ? null : ModelUtils.numberSafeCast(values.get(0), clazz);
    }

    /**
     * Casts the values to the specified generic type and return them all as a list.
     * @return All values with the desired type as a list.
     */
    public <T> List<T> getValues(Class<T> clazz) {
        return values.stream().map(value -> ModelUtils.numberSafeCast(value, clazz)).collect(Collectors.toList());
    }
}
