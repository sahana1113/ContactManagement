package com.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.Bean.Bean;
import com.Query.Enum.Tables;

public interface QueryBuilder {
	QueryBuilder select(Column... columns);
    QueryBuilder from(Tables table);
    QueryBuilder insert(Tables table, String... columns);
    QueryBuilder values(String... values);
    QueryBuilder update(Tables table);
    QueryBuilder set(String... columnValuePairs);
    QueryBuilder deleteFrom(Tables table);
    String build();  
	QueryBuilder andWhere(String condition);
	QueryBuilder orWhere(String condition);
	QueryBuilder where(Condition condition);
	QueryBuilder insertBatch(Tables table, String... columns);
	QueryBuilder values(List<String> categoryNames, int placeholderIndex);
	<T> List<T> executeSelect(String query, Bean entity, Class<T> type, Column[] columns) throws Exception;
	QueryBuilder setColumns(String function, Column... columns);
	QueryBuilder join(Join[] joins);
	QueryBuilder conditions(Condition conditions);
	int executeUpdate(String sql, Bean entity, Bean audit, Column[] columns)
			throws SQLException, NoSuchFieldException, IllegalAccessException;
	int executeInsert(String query, Bean entity, Column[] columns, boolean batch, Bean audit)
			throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException;
	int executeDelete(String sql, Bean entity, Bean audit, Column[] columns)
			throws SQLException, NoSuchFieldException, IllegalAccessException;
	
}