package com.loop54.model.request;

/** Name-value-pair to identify an attribute to filter by. Supports a multiple values. */
public class AttributeNameValuePairMulti extends AttributeNameValuePair {
    /** Value to filter by. */
    public String[] value;
}
