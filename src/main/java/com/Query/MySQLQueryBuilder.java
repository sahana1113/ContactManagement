package com.Query;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.Audit.AuditLogService;
import com.Bean.Bean;
import com.Bean.BeanAudit;
import com.Bean.BeanUserDetails;
import com.Dao.DaoAudit;
import com.Dao.DaoUserContact;
import com.Query.Enum.AllMail;
import com.Query.Enum.Tables;
import com.Query.Enum.UserDetails;
import com.Session.SessionData;
import com.example.HikariCPDataSource;

public class MySQLQueryBuilder implements QueryBuilder {
	public Connection getCon() throws SQLException {
		return HikariCPDataSource.getConnection();
	}

	private StringBuilder query;
	private PreparedStatement preparedStatement;

	public MySQLQueryBuilder() {
		this.query = new StringBuilder();
	}

	@Override
	public QueryBuilder select(Column... columns) {
	String[] columnNames = Arrays.stream(columns).map(Column::getColumnNamesWithAlias).toArray(String[]::new);

		query.append("SELECT ").append(String.join(", ", columnNames));
		return this;
	}


	@Override
	public QueryBuilder from(Tables table) {
		query.append(" FROM ").append(table.withAlias());
		return this;
	}
	@Override
	public QueryBuilder join(Join[] joins) {
		if(joins!=null) {
			for (Join join : joins) {
				query.append(" ").append(join.buildJoin()); 
			}
		}
	    return this;
	}
	@Override
	public QueryBuilder conditions(Condition conditions) {
	    if (conditions != null) {
	        query.append(" WHERE ").append(conditions);
	    }
	    return this;
	}


	@Override
	public QueryBuilder where(Condition condition) {
		query.append(" WHERE ").append(condition);
		return this;
	}

	@Override
	public QueryBuilder andWhere(String condition) {
		query.append(" AND ").append(condition);
		return this;
	}

	@Override
	public QueryBuilder orWhere(String condition) {
		query.append(" OR ").append(condition);
		return this;
	}

	@Override
	public QueryBuilder insert(Tables table, String... columns) {
		query.append("INSERT INTO ").append(table.getTableName()).append(" (").append(String.join(", ", columns)).append(")");
		return this;
	}
	@Override
	public QueryBuilder insertBatch(Tables table, String... columns) {
	    query.append("INSERT INTO ").append(table.getTableName()).append(" (").append(String.join(", ", columns)).append(") ");
	    return this;
	}


	@Override
	public QueryBuilder values(String... columns) {
		query.append("VALUES (");
		for (int i = 0; i < columns.length; i++) {
			query.append("?");
			if (i < columns.length - 1) {
				query.append(", ");
			}
		}
		query.append(")");
		return this;
	}
	
	@Override
	public QueryBuilder values(List<String> categoryNames, int index) {
	    query.append("VALUES ");
	    int size = categoryNames.size();
	    for (int i = 0; i < size ; i++) {
	    	int temp=index;
	        query.append("(");
	        query.append("'").append(categoryNames.get(i)).append("'");
	        while(temp>1) {
	        query.append(", ?");
	        temp--;
	        }
	        query.append(")");

	        if (i < size - 1) {
	            query.append(", ");
	        }
	    }
	    return this;
	}
	@Override
	public QueryBuilder update(Tables table) {
		query.append("UPDATE ").append(table.getTableName());
		return this;
	}
	@Override
	public QueryBuilder setColumns(String function,Column... columns) {
        String[] columnValuePairs = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
        	if(function!=null && function.length()!=0)
        	{
        		columnValuePairs[i]= columns[i].getColumnName() +" = "+function+" ( ? , "+columns[i].getColumnName()+" )";
        	}
        	else
            columnValuePairs[i] = columns[i].getColumnName() + " = ?";
        }
        return set(columnValuePairs);
    }

	@Override
	public QueryBuilder set(String... columnValuePairs) {
		query.append(" SET ").append(String.join(", ", columnValuePairs));
		return this;
	}

	@Override
	public QueryBuilder deleteFrom(Tables table) {
		query.append("DELETE FROM ").append(table.getTableName());
		return this;
	}

	@Override
	public String build() {
		return query.toString();
	}
    
	@Override
	public int executeInsert(String query, Bean entity, Column[] columns, boolean batch,BeanAudit audit) throws SQLException, NoSuchFieldException, SecurityException, IllegalAccessException {
		int z=-1;
		
		
	    try (Connection con = getCon()) {
	        preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        Class<?> clazz = entity.getClass();
	        System.out.println(query);

	        if (batch) {
	            int k = 1;
	            for (int i = 1; i <= 4; i++) {
	                setPreparedStatementParameters(entity, columns, clazz, k,null);
	            }
	            System.out.print(preparedStatement.toString());
//	            z= preparedStatement.executeUpdate();
//	            if(audit!=null)
//	            DaoAudit.insertAuditLog(audit);
	            return z;
	        }
	        Map<String,Object>map=new HashMap<>();
	       setPreparedStatementParameters(entity, columns, clazz, 1,map);
	       if(audit!=null) {
	   		audit.setAction("Insert");
	   		audit.setOld_value(null);
	       audit.setNew_value(AuditLogService.prepareValue(map));
	       }
	        preparedStatement.executeUpdate();
	        try (ResultSet key = preparedStatement.getGeneratedKeys()) {
	            if (key.next()) {
	                z= key.getInt(1);
	                if(audit!=null) {
	                audit.setRecordKey(new Condition(entity.getPrimaryColumn(),"=",z).toString());
	                DaoAudit.insertAuditLog(audit);
	                }
	                return z;
	            }
	        }
	        return -1;
	    }
	}

	private void setPreparedStatementParameters(Bean entity, Column[] columns, Class<?> clazz, int startingIndex,Map<String,Object>map) throws NoSuchFieldException, IllegalAccessException, SQLException {
	    int k = startingIndex;
	    for (Column column : columns) {
	        String fieldName = column.getColumnName();
	        Field field = clazz.getDeclaredField(fieldName);
	        field.setAccessible(true);
	        Object value = field.get(entity);
	        if(map!=null)
	        {
	        	map.put(fieldName, value);
	        }
	        preparedStatement.setObject(k++, value);
	    }

	}

	@Override
	public int executeUpdate(String sql, Bean entity,BeanAudit audit, Column[] columns,Column[] updateColumns) throws SQLException, NoSuchFieldException, IllegalAccessException {
	    int k=0;
		try (Connection con = getCon()) {
	        preparedStatement = con.prepareStatement(sql);
	        System.out.println(sql);
	        
	        Class<?> clazz = entity.getClass();
	        setPreparedStatementParameters(entity, columns, clazz, 1,null);
	        k= preparedStatement.executeUpdate();
	        long updatedTime=System.currentTimeMillis()/1000;
	        Map<String,Object>oldMap=new HashMap<>();
	        Map<String,Object>newMap=new HashMap<>();
        	Bean obj=(Bean) SessionData.getAuditCache().get(audit.getTableName()+entity.getPrimaryId());
        	
        	if(obj==null)
        	{
        		Tables table=Tables.fromTableName(audit.getTableName());
        		DaoAudit.selectPrevious(updateColumns,table,entity.getPrimaryId());
        	}
        	
	        setPreparedStatementParameters(entity, updateColumns, clazz, 1,newMap);
	        setPreparedStatementParameters(obj, updateColumns, clazz, 1,oldMap);
            newMap.put("updated_time",updatedTime);
            if(audit!=null) {
            	audit.setAction("Update");
            	audit.setRecordKey(new Condition(entity.getPrimaryColumn(),"=",entity.getPrimaryId()).toString());
            	audit.setOld_value(AuditLogService.convertMapToJsonString(oldMap));
            	audit.setNew_value(AuditLogService.convertMapToJsonString(newMap));
                DaoAudit.insertAuditLog(audit);
            }
            return k;
	    }
	}
	
	@Override
	public int executeDelete(String sql, Bean entity,BeanAudit audit, Column[] columns) throws SQLException, NoSuchFieldException, IllegalAccessException {
		int k=0;
	    try (Connection con = getCon()) {
	        preparedStatement = con.prepareStatement(sql);
	        System.out.println(sql);

	        Class<?> clazz = entity.getClass();
	        setPreparedStatementParameters(entity, columns, clazz, 1,null);
            k= preparedStatement.executeUpdate();
            if(audit!=null) {
    	   		audit.setAction("Delete");
    	   		audit.setRecordKey(new Condition(entity.getPrimaryColumn(),"=",entity.getPrimaryId()).toString());
    	   		audit.setOld_value(AuditLogService.mapFields(entity));
    	        audit.setNew_value(null);
    	        DaoAudit.insertAuditLog(audit);
    	       }
            return k;
	    }
	}

	@Override
	public <T> List<T> executeSelect(String query, Bean entity, Class<T> type, Column[] columns) throws Exception {
	    try (Connection con = getCon()) {
	        preparedStatement = con.prepareStatement(query);
	        System.out.println(query);

	        Class<?> clazz = entity.getClass();
	        if(columns!=null)
	        setPreparedStatementParameters(entity, columns, clazz, 1,null);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        System.out.println(preparedStatement.toString());
	        return mapUsingReflection(resultSet, type);
	    }
	}
	
	private <T> List<T> mapUsingReflection(ResultSet rs, Class<T> type) throws Exception {
		List<T> results = new ArrayList<>();
	    Map<Object, T> groupedResults = new HashMap<>();
	    ResultSetMetaData metaData = rs.getMetaData();
	    int columnCount = metaData.getColumnCount();

	    Set<String> columnNames = new HashSet<>();
	    for (int i = 1; i <= columnCount; i++) {
	        columnNames.add(metaData.getColumnLabel(i));
	    }

	    while (rs.next()) {
	    	Object groupingKey = null;
	    	try {
	    	    groupingKey = rs.getObject("usermail"); 
	    	} catch (SQLException e) {
	    	    try {
	    	        groupingKey = rs.getObject("contact_id");
	    	    } catch (SQLException ex) {
	    	        try {
	    	            groupingKey = rs.getObject("user_id");
	    	        } catch (SQLException ex2) {
	    	            groupingKey = UUID.randomUUID().toString();
	    	        }
	    	    }
	    	}
	        T instance = groupedResults.get(groupingKey);

	        if (instance == null) {
	            instance = type.getDeclaredConstructor().newInstance();
	            populateMainFields(rs, instance, columnNames);
	            groupedResults.put(groupingKey, instance);
	        }

	        if (hasNestedFields(instance)) {
	            populateNestedFields(rs, instance, columnNames);
	        }
	    }

	    results.addAll(groupedResults.values());
	    return results;
    }
	
	private <T> void populateMainFields(ResultSet rs, T instance, Set<String> columnNames) throws Exception {
	    for (Field field : instance.getClass().getDeclaredFields()) {
	        field.setAccessible(true);

	        String columnName = field.getName();
	        if (columnNames.contains(columnName)) {
	            Object value = rs.getObject(columnName);
	            if (value != null && !List.class.isAssignableFrom(field.getType())) {
	                if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
	                    if (value instanceof Integer) {
	                        value = ((Integer) value) == 1;
	                    }
	                }
	                field.set(instance, value);
	            }	
	        }
	    }
	}

	private <T> void populateNestedFields(ResultSet rs, T instance, Set<String> columnNames) throws Exception {
		for (Field field : instance.getClass().getDeclaredFields()) {
	        field.setAccessible(true);

	        if (List.class.isAssignableFrom(field.getType())) {
	            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
	            Class<?> listType = (Class<?>) genericType.getActualTypeArguments()[0];

	            Object nestedInstance = listType.getDeclaredConstructor().newInstance();
	            boolean populated = false;
	            for (Field nestedField : listType.getDeclaredFields()) {
	                nestedField.setAccessible(true);
	                String columnName = nestedField.getName();
	                if (columnNames.contains(columnName)) {
	                    Object value = rs.getObject(columnName);
	                    if (value != null) {
	                        nestedField.set(nestedInstance, value);
	                        populated = true;
	                    }
	                }
	            }

	            if (populated) {
	                List<Object> nestedList = (List<Object>) field.get(instance);
	                if((nestedList!=null && !nestedList.contains(nestedInstance))) {
	                    nestedList.add(nestedInstance);
	                }
	            }
	        }
	   }
	}
	
	private <T> boolean hasNestedFields(T instance) {
	    for (Field field : instance.getClass().getDeclaredFields()) {
	        if (List.class.isAssignableFrom(field.getType())) {
	            return true;
	        }
	    }
	    return false;
	}

	
	
}