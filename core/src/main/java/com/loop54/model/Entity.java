package com.loop54.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** An entity represents one Product (or document). It is identified by the id and type and contains named attributes. */
public class Entity {
    /** Needed for deserialization. */
    private Entity() { }

    /**
     * @param type The type of the entity. This is usually "Product".
     * @param id   The unique id of the entity. Could for instance be a SKU id.
     */
    public Entity(String type, String id) {
        if (type == null)
            throw new IllegalArgumentException("Type was null");
        if (id == null)
            throw new IllegalArgumentException("Id was null");

        this.type = type;
        this.id = id;
    }

    /** The type of the entity. This is usually "Product". This is used to distinguish between products and non-product entities (such as content). */
    public String type;

    /** The unique id of the entity. Could for instance be a SKU id. */
    public String id;

    /**
     * All attributes available on the entity. When sending an Entity with a CreateEvent call these are not necessary to be set.
     * @return All attributes available on the entity.
     */
    public List<EntityAttribute> getAttributes() {
        return internalAttributeMapping == null ? null : internalAttributeMapping.values().stream().collect(Collectors.toList());
    }

    /**
     * See {@link #getAttributes}.
     * @param attributes A list of attributes to set on the entity
     */
    public void setAttributes(List<EntityAttribute> attributes) {
        // In the engine attributes are treated case-insensitive.
        internalAttributeMapping = new HashMap<>();
        for (EntityAttribute attribute : attributes)
            internalAttributeMapping.put(attribute.getName().toLowerCase(), attribute);
    }

    private Map<String, EntityAttribute> internalAttributeMapping;

    /**
     * Gets the attribute value with the desired name and type.
     *
     * @param name  Name of the attribute.
     * @param clazz Type of the attribute. This needs to match what the library has deserialized or an exception will be thrown.
     * @param <T> The expected type of the value in the attribute.
     * @return The attribute value. If more than one attribute exist the first one will be returned. If the attribute does not exist null will be returned.
     */
    public <T> T getAttributeValueOrNull(String name, Class<T> clazz) {
        List<T> attributeValues = getAttributeValuesOrNull(name, clazz);

        if (attributeValues == null || attributeValues.size() == 0)
            return null;

        return attributeValues.get(0);
    }

    /**
     * Gets the attribute values with the desired name and type.
     *
     * @param name  Name of the attribute.
     * @param clazz Type of the attribute. This needs to match what the library has deserialized or an exception will be thrown.
     * @param <T> The expected type of the value in the attribute.
     * @return All values related to the attribute as an array or null if the attribute does not exist.
     */
    public <T> List<T> getAttributeValuesOrNull(String name, Class<T> clazz) {
        if (name == null)
            throw new IllegalArgumentException("Name was null");

        EntityAttribute attribute = internalAttributeMapping.get(name);
        return attribute == null ? null : attribute.getValues(clazz);
    }
}