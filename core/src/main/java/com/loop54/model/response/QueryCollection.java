package com.loop54.model.response;

import java.util.List;

/** A collection of queries (strings). */
public class QueryCollection {
    /** The total number of queries available before paging. */
    public int count;

    /** The queries after paging. */
    public List<QueryResult> items;
}
