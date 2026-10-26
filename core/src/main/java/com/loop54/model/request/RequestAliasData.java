package com.loop54.model.request;

/** Provides human-readable labels in the Portal. */
public class RequestAliasData {
    public RequestAliasData() { }

    /**
     * @param name An alias for the attribute name.
     * @param value An alias for the attribute value.
     * @param details A more detailed description for the attribute name–value pair. Will be shown in the Portal.
     */
    public RequestAliasData(final String name, final String value, final String details) {
        this.name = name;
        this.value = value;
        this.details = details;
    }

    /** Specify an alias for this attribute name. */
    public String name;
    /** Specify an alias for this attribute value. */
    public String value;
    /** Specify a more detailed description for this attribute name–value pair. Will be shown in the Portal. */
    public String details;
}
