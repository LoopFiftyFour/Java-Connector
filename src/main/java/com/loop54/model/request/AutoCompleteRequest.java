package com.loop54.model.request;

import com.loop54.model.request.parameters.QueryCollectionParameters;

/** This class is used for performing auto-complete requests to the Loop54 e-commerce search engine. */
public class AutoCompleteRequest extends Request {
    /** The partial query that the user has entered. */
    public AutoCompleteRequest(String query)
    {
        this.query = query;
    }

    /** The partial query that the user has entered. */
    public final String query;

    /** Parameters for specifying how the auto-complete results should be retrieved. Contains paging and sorting options. */
    public final QueryCollectionParameters queriesOptions = new QueryCollectionParameters();
}
