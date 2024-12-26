package com.Query;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

import com.Bean.Bean;
import com.Query.Enum.Tables;

public class PostgresQueryBuilder implements QueryBuilder {
    private StringBuilder query;
    static Connection con;
    public PostgresQueryBuilder() {
        this.query = new StringBuilder();
    }
	@Override
	public QueryBuilder select(Column... columns) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder from(Tables table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder insert(Tables table, String... columns) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder values(String... values) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder update(Tables table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder set(String... columnValuePairs) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String build() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder andWhere(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder orWhere(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder deleteFrom(Tables table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder where(Condition condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public QueryBuilder insertBatch(Tables table, String... columns) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder values(List<String> categoryNames, int placeholderIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int executeInsert(String query, Bean entity, Column[] columns, boolean batch) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public QueryBuilder join(String joinClause) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> List<T> executeSelect(String query, Bean entity, Class<T> type, Column[] columns) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int executeUpdateDelete(String sql, Bean entity, Column[] columns) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}

