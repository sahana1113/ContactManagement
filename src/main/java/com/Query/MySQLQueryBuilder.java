package com.Query;

public class MySQLQueryBuilder implements QueryBuilder {

	private StringBuilder query;

    public MySQLQueryBuilder() {
        this.query = new StringBuilder();
    }

    @Override
    public QueryBuilder select(String... columns) {
        query.append("SELECT ").append(String.join(", ", columns));
        return this;
    }

    @Override
    public QueryBuilder from(String table) {
        query.append(" FROM ").append(table);
        return this;
    }

    @Override
    public QueryBuilder where(String condition) {
        query.append(" WHERE ").append(condition);
        return this;
    }

    @Override
    public QueryBuilder insert(String table, String... columns) {
        query.append("INSERT INTO ").append(table)
             .append(" (").append(String.join(", ", columns)).append(")");
        return this;
    }

    @Override
    public QueryBuilder values(String... values) {
        query.append(" VALUES (").append(String.join(", ", values)).append(")");
        return this;
    }

    @Override
    public QueryBuilder update(String table) {
        query.append("UPDATE ").append(table);
        return this;
    }

    @Override
    public QueryBuilder set(String... columnValuePairs) {
        query.append(" SET ").append(String.join(", ", columnValuePairs));
        return this;
    }

    @Override
    public QueryBuilder deleteFrom(String table) {
        query.append("DELETE FROM ").append(table);
        return this;
    }

    @Override
    public String build() {
        return query.toString();
    }
}

