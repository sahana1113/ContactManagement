package com.Query;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.Query.Enum.Tables;
import com.Query.Enum.UserDetails;
import com.rowMapper.RowMapper;

public class QueryLayer {
    private static String dbType;

    private QueryLayer() {}

    static {
        try (InputStream input = QueryLayer.class.getClassLoader().getResourceAsStream("/config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);
                dbType = prop.getProperty("db.type").toLowerCase();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getDbType() {
        return dbType != null ? dbType : "mysql"; 
    }

    public static QueryBuilder getQueryBuilder() throws SQLException {
        if ("mysql".equals(dbType)) {
            return new MySQLQueryBuilder();
        } else if ("postgres".equals(dbType)) {
            return new PostgresQueryBuilder();
        } else {
            throw new UnsupportedOperationException("Unsupported database type: " + dbType);
        }
    }

    public static <T> List<T> buildSelectQuery(Tables table, Column[] columns, Column[] conditions, String[] logics, Class<T> type,Object[]obj) throws SQLException {
        QueryBuilder builder = getQueryBuilder().select(columns).from(table);
 
        if (conditions != null && conditions.length > 0) {
            builder = ((MySQLQueryBuilder) builder).conditions(conditions, logics);
        }
        String query=builder.build();
        System.out.print(query);
        return getQueryBuilder().executeSelect(query,obj,type);
    }


    public static int buildInsertQuery(Tables table,Object[] obj) throws SQLException {
        String[] columns = getColumnsByTable(table);
        String query= getQueryBuilder().insert(table, columns).values(columns).build();
        return getQueryBuilder().executeInsert(query, obj);
    }
    public static int buildInsertQuery(Tables table,Object[] obj, Column... columns) throws SQLException {
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].getColumnName();
        }
        String query=getQueryBuilder().insert(table, columnNames).values(columnNames).build();
        return getQueryBuilder().executeInsert(query, obj);
    }

    public static int buildUpdateQuery(Tables table, Column[] conditionsColumns, String[] logics,Object[]obj, Column... columns) throws SQLException {
        String[] columnValuePairs = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnValuePairs[i] = columns[i].getColumnName() + " = ?";
        }
        QueryBuilder builder = getQueryBuilder().update(table).set(columnValuePairs);
        if (conditionsColumns != null && conditionsColumns.length > 0) {
        	builder = ((MySQLQueryBuilder) builder).conditions(conditionsColumns, logics);
        }
        String query=builder.build();
        return getQueryBuilder().executeUpdateDelete(query, obj);
    }

    

	public static int buildDeleteQuery(Tables table, Column[] conditionsColumns, String[] logics,Object[]obj) throws SQLException {
        QueryBuilder builder = getQueryBuilder().deleteFrom(table);
        if (conditionsColumns != null && conditionsColumns.length > 0) {
        	builder = ((MySQLQueryBuilder) builder).conditions(conditionsColumns, logics);
        }
        String query=builder.build();
        return getQueryBuilder().executeUpdateDelete(query, obj);
    }


    public static String[] getColumnsByTable(Tables table) {
        switch (String.valueOf(table)) {
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
                throw new IllegalArgumentException("Unsupported table: " + String.valueOf(table));
        }
    }
}
