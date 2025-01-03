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
import com.Bean.Bean;
import com.Bean.BeanUserDetails;
import com.Dao.DaoUserContact;
import com.Query.Enum.AllMail;
import com.Query.Enum.Tables;
import com.Query.Enum.UserDetails;
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
//	public QueryBuilder join(String joinClause) {
//	    query.append(" ").append(joinClause);
//	    return this;
//	}
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
	public int executeInsert(String query, Bean entity, Column[] columns,boolean batch) throws SQLException, NoSuchFieldException, SecurityException{
		Connection con = getCon();
		preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		Class<?> clazz = entity.getClass();
		System.out.println(query);
		if(batch) {
			int k=1;
			for(int i=1;i<=4;i++)
			{
				try {
			        for (int j = 1; j < columns.length; j++) {
			            String fieldName = columns[j].getColumnName();
			            Field field = clazz.getDeclaredField(fieldName);
			            field.setAccessible(true); 
			            Object value = field.get(entity); 
			            preparedStatement.setObject(k, value); 
			            k++;
			        }
			    } catch (NoSuchFieldException | IllegalAccessException e) {
			        throw new RuntimeException("Error mapping object fields to query parameters", e);
			    }
			}
			System.out.print(preparedStatement.toString());
			return preparedStatement.executeUpdate();
		}
		  try {
		        for (int i = 0; i < columns.length; i++) {
		            String fieldName = columns[i].getColumnName();
		            Field field = clazz.getDeclaredField(fieldName);
		            field.setAccessible(true); 
		            Object value = field.get(entity); 
		            preparedStatement.setObject(i + 1, value); 
		        }
		    } catch (NoSuchFieldException | IllegalAccessException e) {
		        throw new RuntimeException("Error mapping object fields to query parameters", e);
		    }
		preparedStatement.executeUpdate();
		ResultSet key = preparedStatement.getGeneratedKeys();
		if (key.next()) {
			int k = key.getInt(1);
			con.close();
			return k;
		}
		con.close();
		return -1;
	}

	@Override
	public int executeUpdateDelete(String sql, Bean entity,Column[] columns) throws SQLException {
		Connection con = getCon();
		preparedStatement = con.prepareStatement(sql);
		System.out.println(sql);
		 Class<?> clazz = entity.getClass();
		    try {
		        for (int i = 0; i < columns.length; i++) {
		            String fieldName = columns[i].getColumnName(); 
		            Field field = clazz.getDeclaredField(fieldName);
		            field.setAccessible(true); 
		            Object value = field.get(entity); 
		            preparedStatement.setObject(i + 1, value); 
		        }
		    } catch (NoSuchFieldException | IllegalAccessException e) {
		        throw new RuntimeException("Error mapping object fields to query parameters", e);
		    }
		int k = preparedStatement.executeUpdate();
		con.close();
		return k;
	}

	@Override
	public <T> List<T> executeSelect(String query, Bean entity, Class<T> type,Column[] columns) throws Exception {
		Connection con = getCon();
		preparedStatement = con.prepareStatement(query);
		System.out.println(query);
		Class<?> clazz = entity.getClass();
	    try {
	        for (int i = 0; i < columns.length; i++) {
	            String fieldName = columns[i].getColumnName(); 
	          
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true); 
	            Object value = field.get(entity); 
	            preparedStatement.setObject(i + 1, value); 
	        }
	    } catch (NoSuchFieldException | IllegalAccessException e) {
	        throw new RuntimeException("Error mapping object fields to query parameters", e);
	    }
		ResultSet resultSet = preparedStatement.executeQuery();
		List<T> results = mapUsingReflection(resultSet,type);
		resultSet.close();
		con.close();
		return results; 
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