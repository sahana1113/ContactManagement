package com.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.Query.Enum.Tables;
import com.example.HikariCPDataSource;
import com.rowMapper.RowMapper;

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
	public QueryBuilder where(String condition) {
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
	public int executeInsert(String query, Object obj, Column[] columns)
			throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int executeUpdateDelete(String sql, Object entity, Column[] columns) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public QueryBuilder innerJoin(Tables table, String onCondition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> List<T> executeSelect(String query, Object entity, Class<T> type, Column[] columns) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder conditions(Column[] conditionsColumns, String[] logics, boolean alias) {
		// TODO Auto-generated method stub
		return null;
	}

}

