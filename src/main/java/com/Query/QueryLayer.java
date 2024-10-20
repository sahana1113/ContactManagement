package com.Query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryLayer {
	private static String dbType;
	private static QueryBuilder queryBuilder;
	private QueryLayer() {}
	static {
        try (InputStream input = QueryLayer.class.getClassLoader().getResourceAsStream("/config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                
            }else {
            prop.load(input);

            dbType = prop.getProperty("db.type").toLowerCase();
            if ("mysql".equals(dbType)) {
                queryBuilder = new MySQLQueryBuilder();
            } else if ("postgres".equals(dbType)) {
                queryBuilder = new PostgresQueryBuilder();
            } else {
                throw new UnsupportedOperationException("Unsupported database type: " + dbType);
            }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static String getDbType() {
        return dbType != null ? dbType : "mysql"; 
    }
    public static String buildSelectQuery(String table, String... columns) {
        return queryBuilder.select(columns).from(table).build();
    }

    public static String buildInsertQuery(String table, String... columns) {
        return queryBuilder.insert(table, columns).values("?").build();
    }

    public static String buildUpdateQuery(String table, String condition, String... setValues) {
        return queryBuilder.update(table).set(setValues).where(condition).build();
    }


    public static String buildDeleteQuery(String table, String condition) {
        return queryBuilder.deleteFrom(table).where(condition).build();
    }
}
