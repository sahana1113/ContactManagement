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
	QueryBuilder conditions(Column[] conditionsColumns, String[] logics, boolean alias);
	int executeInsert(String query, Bean entity, Column[] columns) throws SQLException;
	int executeUpdateDelete(String sql, Bean entity, Column[] columns) throws SQLException;
	QueryBuilder join(Tables table, String onCondition, String join);
	QueryBuilder where(Condition condition);
	<T> List<T> executeSelect(String query, Bean entity, Class<T> type, List<Column> columns) throws Exception;
	
}