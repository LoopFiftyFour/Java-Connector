package com.loop54.model.response;

import java.util.List;

/** A query with scopes. Scopes can be categories, brands or another attribute of the entities related to the query to do faceted autocomplete. */
public class ScopedQueryResult extends QueryResult {
    /** Scopes where this query is relevant. Based on which entity attribute values will be present in the search results. Use together with faceting of search results for this query. */
    public List<String> scopes;
	
	/** The name of the attribute from which the scopes were created */
	public String scopeAttributeName;
}
