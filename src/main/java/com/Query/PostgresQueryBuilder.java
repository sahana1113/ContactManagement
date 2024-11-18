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
	public int executeInsert(String query, Object... obj) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
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
	public int executeUpdateDelete(String sql, Object... parameters) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public QueryBuilder deleteFrom(Tables table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QueryBuilder conditions(Column[] conditionsColumns, String[] logics) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> List<T> executeSelect(String query, Object[] params, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}

