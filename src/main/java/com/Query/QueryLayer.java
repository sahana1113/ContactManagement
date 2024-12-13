package com.Query;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.Bean.Bean;
import com.Bean.BeanUserDetails;
import com.Query.Enum.Tables;
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
    public static <T> List<T> buildSelectQuery(Tables[] table, Column[] columns, Condition conditions, Class<T> type,Bean obj,Column[][]join,String joining) throws Exception {
        QueryBuilder builder = getQueryBuilder().select(columns).from(table[0]);
        for (int i = 1; i < table.length; i++) {
        	String left=join[i-1][0].getAlias()+"."+join[i-1][0];
        	String right=join[i-1][1].getAlias()+"."+join[i-1][1];
            builder = ((MySQLQueryBuilder) builder).join(table[i], left + " = " + right," "+joining+" ");  
        }
        if (conditions != null) {
            builder = ((MySQLQueryBuilder) builder).where(conditions);
        }
        String query=builder.build();
        
        return getQueryBuilder().executeSelect(query,obj,type,conditions.getFieldNames());    }

    public static int buildInsertQuery(Tables table,Bean obj) throws SQLException, NoSuchFieldException, IllegalAccessException{
        Column[] columns = getColumnsByTable(table);
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].getColumnName();
        }
        String query= getQueryBuilder().insert(table, columnNames).values(columnNames).build();
        return getQueryBuilder().executeInsert(query, obj,columns);
    }
    
    public static int buildInsertQuery(Tables table,Bean obj, Column... columns) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].getColumnName();
        }
        String query=getQueryBuilder().insert(table, columnNames).values(columnNames).build();
        return getQueryBuilder().executeInsert(query, obj,columns);
    }

    public static int buildUpdateQuery(Tables table, Column[] conditionsColumns, String[] logics,Bean obj, Column... columns) throws SQLException {
        String[] columnValuePairs = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnValuePairs[i] = columns[i].getColumnName() + " = ?";
        }
        QueryBuilder builder = getQueryBuilder().update(table).set(columnValuePairs);
        if (conditionsColumns != null && conditionsColumns.length > 0) {
        	builder = ((MySQLQueryBuilder) builder).conditions(conditionsColumns, logics,false);
        }
        String query=builder.build();
        Column[] all=combineColumns(columns,conditionsColumns);
        return getQueryBuilder().executeUpdateDelete(query, obj,all);
    }

	public static int buildDeleteQuery(Tables table, Column[] conditionsColumns, String[] logics,Bean obj) throws SQLException {
        QueryBuilder builder = getQueryBuilder().deleteFrom(table);
        if (conditionsColumns != null && conditionsColumns.length > 0) {
        	builder = ((MySQLQueryBuilder) builder).conditions(conditionsColumns, logics,false);
        }
        String query=builder.build();
        return getQueryBuilder().executeUpdateDelete(query, obj,conditionsColumns);
    }
    

    public static Column[] getColumnsByTable(Tables table) {
        switch (String.valueOf(table)) {
            case "UserDetails":
                return Enum.UserDetails.getColumnNames();
            case "ALL_MAIL":
                return Enum.AllMail.getColumnNames();
            case "ALL_PHONE":
                return Enum.AllPhone.getColumnNames();
            case "CATEGORIES":
                return Enum.Categories.getColumnNames();
            case "CATEGORY_USERS":
                return Enum.CategoryUsers.getColumnNames();
            case "CONTACT_DETAILS":
                return Enum.ContactDetails.getColumnNames();
            case "CREDENTIALS":
                return Enum.Credentials.getColumnNames();
            case "SESSION":
                return Enum.Session.getColumnNames();
            default:
                throw new IllegalArgumentException("Unsupported table: " + String.valueOf(table));
        }
    }
    private static Column[] combineColumns(Column[] updateColumns, Column[] conditionColumns) {
        if (conditionColumns == null || conditionColumns.length == 0) {
            return updateColumns; 
        }
        Column[] allColumns = new Column[updateColumns.length + conditionColumns.length];
        System.arraycopy(updateColumns, 0, allColumns, 0, updateColumns.length);
        System.arraycopy(conditionColumns, 0, allColumns, updateColumns.length, conditionColumns.length);
        return allColumns;
    }
}
