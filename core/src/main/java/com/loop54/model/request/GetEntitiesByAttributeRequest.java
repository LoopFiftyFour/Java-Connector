package com.loop54.model.request;

import com.loop54.model.request.parameters.EntityCollectionParameters;

/** This class is used to configure a GetEntities request to the Loop54 e-commerce search engine. */
public class GetEntitiesByAttributeRequest extends Request {

    public GetEntitiesByAttributeRequest(String attributeName, String attributeValue, RequestAliasData requestAlias) {
		AttributeNameValuePairSingle attr = new AttributeNameValuePairSingle();
        attr.name = attributeName;
        attr.value = attributeValue;
		attribute = attr;
		this.requestAlias = requestAlias;
    }
	
	public GetEntitiesByAttributeRequest(String attributeName, String[] attributeValues, RequestAliasData requestAlias) {
		AttributeNameValuePairMulti attr = new AttributeNameValuePairMulti();
        attr.name = attributeName;
        attr.value = attributeValues;
		attribute = attr;
		this.requestAlias = requestAlias;
    }

    public GetEntitiesByAttributeRequest(String attributeName, String attributeValue) {
		this(attributeName, attributeValue, null);
    }
	
	public GetEntitiesByAttributeRequest(String attributeName, String[] attributeValues) {
		this(attributeName, attributeValues, null);
    }

    /**
     * The attribute name-value-pair to find entities connected to. Note: this attribute needs to be indexed in the
     * engine. See the endpoint GetIndexedAttribute.
     */
    public AttributeNameValuePair attribute;
    
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
