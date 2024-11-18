package com.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.Query.Enum.Tables;
import com.rowMapper.RowMapper;

public interface QueryBuilder {
	QueryBuilder select(Column... columns);
    QueryBuilder from(Tables table);
    QueryBuilder where(String condition);
    QueryBuilder insert(Tables table, String... columns);
    QueryBuilder values(String... values);
    QueryBuilder update(Tables table);
    QueryBuilder set(String... columnValuePairs);
    QueryBuilder deleteFrom(Tables table);
    String build();  
	int executeInsert(String query, Object... obj) throws SQLException;
	QueryBuilder andWhere(String condition);
	QueryBuilder orWhere(String condition);
	int executeUpdateDelete(String sql, Object... parameters) throws SQLException;
	QueryBuilder conditions(Column[] conditionsColumns, String[] logics);
	<T> List<T> executeSelect(String query, Object[] params, Class<T> type) throws SQLException;
	
}