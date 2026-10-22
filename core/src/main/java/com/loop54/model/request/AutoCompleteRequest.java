package com.loop54.model.request;

import com.loop54.model.request.parameters.QueryCollectionParameters;

import java.util.function.Consumer;

/** This class is used for performing auto-complete requests to the Loop54 e-commerce search engine. */
public class AutoCompleteRequest extends Request {
    /**
     * The partial query that the user has entered.
     * @param query the query to get auto complete suggestions for.
     */
    public AutoCompleteRequest(String query) {
        this.query = query;
    }

    /** The partial query that the user has entered. */
    public final String query;

    /** Parameters for specifying how the auto-complete results should be retrieved. Contains paging and sorting options. */
    public final QueryCollectionParameters queriesOptions = new QueryCollectionParameters();

    /**
     * Configures {@link #queriesOptions}, e.g. {@code request.withQueries(q -> q.page(0, 10))}.
     * @param configurator Applied to {@link #queriesOptions}.
     * @return This request, for chaining.
     */
    public AutoCompleteRequest withQueries(final Consumer<QueryCollectionParameters> configurator) {
        configurator.accept(queriesOptions);
        return this;
    }

    @Override
    public AutoCompleteRequest withCustomData(final String key, final Object value) {
        super.withCustomData(key, value);
        return this;
    }
}
