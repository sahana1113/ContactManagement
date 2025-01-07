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
	public <T> List<T> executeSelect(String query, Bean entity, Class<T> type, Column[] columns) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder setColumns(String function, Column... columns) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder join(Join[] joins) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder conditions(Condition conditions) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int executeUpdate(String sql, Bean entity, Bean audit, Column[] columns)
			throws SQLException, NoSuchFieldException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int executeInsert(String query, Bean entity, Column[] columns, boolean batch, Bean audit)
			throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int executeDelete(String sql, Bean entity, Bean audit, Column[] columns)
			throws SQLException, NoSuchFieldException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}

