package com.Query;

public interface QueryBuilder {
	QueryBuilder select(String... columns);
    QueryBuilder from(String table);
    QueryBuilder where(String condition);
    QueryBuilder insert(String table, String... columns);
    QueryBuilder values(String... values);
    QueryBuilder update(String table);
    QueryBuilder set(String... columnValuePairs);
    QueryBuilder deleteFrom(String table);
    String build();  // To get the final query
}
