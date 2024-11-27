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
	QueryBuilder andWhere(String condition);
	QueryBuilder orWhere(String condition);
	int executeInsert(String query, Object obj, Column[] columns) throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException;
	int executeUpdateDelete(String sql, Object entity, Column[] columns) throws SQLException;
	QueryBuilder innerJoin(Tables table, String onCondition);
	<T> List<T> executeSelect(String query, Object entity, Class<T> type, Column[] columns) throws SQLException;
	QueryBuilder conditions(Column[] conditionsColumns, String[] logics, boolean alias);
	
}