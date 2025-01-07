package com.Query;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
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
    public static <T> List<T> buildSelectQuery(Tables table, Column[] columns, Condition conditions, Class<T> type,Bean obj,Join[] joins,Column[] column) throws Exception {
        QueryBuilder builder = getQueryBuilder().select(columns).from(table).join(joins).conditions(conditions);
        String query=builder.build();
        
        return getQueryBuilder().executeSelect(query,obj,type,column);  
        }

    public static int buildInsertQuery(Tables table,Bean obj,Bean audit, Column... columns) throws SQLException, NoSuchFieldException, IllegalAccessException {
        String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].getColumnName();
        }
        String query=getQueryBuilder().insert(table, columnNames).values(columnNames).build();
        return getQueryBuilder().executeInsert(query, obj,columns,false,audit);
    }
    
    public static int buildBatchInsert(Tables table, List<String> data, Column[] columns,Bean obj,Bean audit) throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException {
    	String[] columnNames = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnNames[i] = columns[i].getColumnName();
        }
    	String query=getQueryBuilder().insertBatch(table,columnNames).values(data,columns.length).build();
        return getQueryBuilder().executeInsert(query, obj, columns,true,audit);
    }

	public static int buildUpdateQuery(Tables table, Condition conditionsColumns,Bean obj,Column[]columnConditions,String function,Bean audit,Column... columns) throws SQLException, NoSuchFieldException, IllegalAccessException {

        QueryBuilder builder = getQueryBuilder().update(table).setColumns(function,columns).where(conditionsColumns);
        String query=builder.build();
        Column[] all=combineColumns(columns,columnConditions);
        return getQueryBuilder().executeUpdate(query, obj,audit,all);
    }

	public static int buildDeleteQuery(Tables table, Condition conditionsColumns,Bean obj,Bean audit,Column[]columnValues) throws SQLException, NoSuchFieldException, IllegalAccessException {
        QueryBuilder builder = getQueryBuilder().deleteFrom(table).where(conditionsColumns);
        String query=builder.build();
        return getQueryBuilder().executeDelete(query, obj,audit,columnValues);
    }
    
    private static Column[] combineColumns(Column[] updateColumns,Column[] conditionColumns) {
    	if (conditionColumns == null || conditionColumns.length == 0) {
            return updateColumns; 
        }
        Column[] allColumns = new Column[updateColumns.length + conditionColumns.length];
        System.arraycopy(updateColumns, 0, allColumns, 0, updateColumns.length);
        System.arraycopy(conditionColumns, 0, allColumns, updateColumns.length, conditionColumns.length);
        return allColumns;
    }
}
