package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class GetEntitiesByAttributeRequest extends Request {

    public GetEntitiesByAttributeRequest(String attributeName, String attributeValue) {
        attribute.name = attributeName;
        attribute.value = attributeValue;
    }
    
    public GetEntitiesByAttributeRequest(String attributeName, String attributeValue, RequestAliasData requestAlias) {
        this(attributeName, attributeValue);
        this.requestAlias = requestAlias;
    }

    /**
     * The attribute name-value-pair to find entities connected to. Note: this attribute needs to be indexed in the
     * engine. See the endpoint GetIndexedAttribute.
     */
    public final AttributeNameValuePair attribute = new AttributeNameValuePair();
    
    /**
     * Provides human-readable labels in the Portal.
     */
    public RequestAliasData requestAlias = null;

    /**
     * Parameters for specifying which entities to retrieve. Such as filtering, faceting, sorting and paging.
     * Note that filtering is advised when doing this request.
     */
    public final EntityCollectionParameters resultsOptions = new EntityCollectionParameters();
}
