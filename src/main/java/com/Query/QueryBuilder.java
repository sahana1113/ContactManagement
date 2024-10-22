package com.Query;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryBuilder {
	QueryBuilder select(String... columns);
    QueryBuilder from(String table);
    QueryBuilder where(String condition);
    QueryBuilder insert(String table, String... columns);
    QueryBuilder values(String... values);
    QueryBuilder update(String table);
    QueryBuilder set(String... columnValuePairs);
    QueryBuilder deleteFrom(String table);
    String build();  
	int executeInsert(String query, Object... obj) throws SQLException;
	void setCon() throws SQLException;
}
