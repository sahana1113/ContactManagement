package com.Query;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
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
                queryBuilder.setCon();
            } else if ("postgres".equals(dbType)) {
                queryBuilder = new PostgresQueryBuilder();
            } else {
                throw new UnsupportedOperationException("Unsupported database type: " + dbType);
            }
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static String getDbType() {
        return dbType != null ? dbType : "mysql"; 
    }
    public static String buildSelectQuery(String table,String condition) {
    	String[] columns = getColumnsByTable(table);
    	 QueryBuilder builder = queryBuilder.select(columns).from(table);

    	    if (condition != null && !condition.isEmpty()) {
    	        builder = builder.where(condition);
    	    }

    	    return builder.build();
    }

    public static String buildInsertQuery(String table) {
    	String[] columns = getColumnsByTable(table);
        return queryBuilder.insert(table, columns).values(columns).build();
    }

    public static String buildUpdateQuery(String table, String condition, String... setValues) {
        QueryBuilder builder = queryBuilder.update(table).set(setValues);

        if (condition != null && !condition.isEmpty()) {
            builder = builder.where(condition);
        }

        return builder.build();
    }

    public static String buildDeleteQuery(String table, String condition) {
        QueryBuilder builder = queryBuilder.deleteFrom(table);

        if (condition != null && !condition.isEmpty()) {
            builder = builder.where(condition);
        }

        return builder.build();
    }
    public static int executeInsertQuery(String query,Object... obj) throws SQLException
    {
    	return queryBuilder.executeInsert(query,obj);
    }
    public static String[] getColumnsByTable(String tableName) {
        switch (tableName.toLowerCase()) {
            case "userDetails":
                return Enum.UserDetails.getColumnNames();
            case "all_mail":
                return Enum.AllMail.getColumnNames();
            case "all_phone":
                return Enum.AllPhone.getColumnNames();
            case "categories":
                return Enum.Categories.getColumnNames();
            case "category_users":
                return Enum.CategoryUsers.getColumnNames();
            case "contactDetails":
                return Enum.ContactDetails.getColumnNames();
            case "credentials":
                return Enum.Credentials.getColumnNames();
            case "session":
                return Enum.Session.getColumnNames();
            default:
                throw new IllegalArgumentException("Unsupported table: " + tableName);
        }
    }

}
