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

import javax.naming.NamingException;

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
	public QueryBuilder join(Tables table, String onCondition,String join) {
	    query.append(join).append(table.withAlias()).append(" ON ").append(onCondition);
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
	public QueryBuilder conditions(Column[] conditionsColumns, String[] logics,boolean alias) {
//		if(alias) {
//		this.where(conditionsColumns[0].getColumnNamesWithAlias() + " = ?");
//		}
//		else {
//			this.where(conditionsColumns[0].getColumnName() + " = ?");
//		}
		for (int i = 1; i < conditionsColumns.length; i++) {
			String condition;
			if(alias) {
			condition= conditionsColumns[i].getColumnNamesWithAlias() + " = ?";
			}
			else {
				condition=conditionsColumns[i].getColumnName() + " = ?";
			}
			if (logics != null && i - 1 < logics.length) {
				if ("OR".equalsIgnoreCase(logics[i - 1])) {
					this.orWhere(condition);
				} else {
					this.andWhere(condition);
				}
			} else {
				this.andWhere(condition);
			}
		}
		return this;
	}

	@Override
	public QueryBuilder insert(Tables table, String... columns) {
		query.append("INSERT INTO ").append(table.getTableName()).append(" (").append(String.join(", ", columns)).append(")");
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
	public QueryBuilder update(Tables table) {
		query.append("UPDATE ").append(table.getTableName());
		return this;
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
	public int executeInsert(String query, Bean entity, Column[] columns) throws SQLException{
		Connection con = getCon();
		preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
	public int executeUpdateDelete(String sql, Bean entity,Column[]columns) throws SQLException {
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
	public <T> List<T> executeSelect(String query, Bean entity, Class<T> type,List<Column> columns) throws Exception {
		Connection con = getCon();
		preparedStatement = con.prepareStatement(query);
		System.out.println(query);
		Class<?> clazz = entity.getClass();
	    try {
	        for (int i = 0; i < columns.size(); i++) {
	            String fieldName = columns.get(i).getColumnName(); 
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true); 
	            Object value = field.get(entity); 
	            preparedStatement.setObject(i + 1, value); 
	        }
	    } catch (NoSuchFieldException | IllegalAccessException e) {
	        throw new RuntimeException("Error mapping object fields to query parameters", e);
	    }
	  //  System.out.println(preparedStatement.);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<T> results = mapUsingReflection(resultSet,type);
     //   System.out.println(results);
//		while (resultSet.next()) {
//			results.add(mapRowUsingReflection(resultSet, type));
//		}

		resultSet.close();
		con.close();
		return results; 
	}
	
	private <T> List<T> mapUsingReflection(ResultSet rs, Class<T> type) throws Exception {
		List<T> results = new ArrayList<>();
	    Map<Object, T> groupedResults = new HashMap<>();
	    ResultSetMetaData metaData = rs.getMetaData();
	    int columnCount = metaData.getColumnCount();

	    List<String> columnNames = new ArrayList<>();
	    for (int i = 1; i <= columnCount; i++) {
	        columnNames.add(metaData.getColumnLabel(i));
	    }

	    while (rs.next()) {
	    	Object groupingKey = null;
	    	try {
	    	    groupingKey = rs.getObject("usermail"); 
	    	} catch (SQLException e) {
	    	    try {
	    	        groupingKey = rs.getObject("username");
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
	
	private <T> void populateMainFields(ResultSet rs, T instance, List<String> columnNames) throws Exception {
	    for (Field field : instance.getClass().getDeclaredFields()) {
	        field.setAccessible(true);

	        String columnName = field.getName();
	        if (columnNames.contains(columnName)) {
	            Object value = rs.getObject(columnName);
	            if (value != null && !List.class.isAssignableFrom(field.getType())) {
	            	//System.out.println("value:"+value+" feild:"+field);
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

	private <T> void populateNestedFields(ResultSet rs, T instance, List<String> columnNames) throws Exception {
		for (Field field : instance.getClass().getDeclaredFields()) {
	        field.setAccessible(true);

	        if (List.class.isAssignableFrom(field.getType())) {
	            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
	            Class<?> listType = (Class<?>) genericType.getActualTypeArguments()[0];

	            Object nestedInstance = listType.getDeclaredConstructor().newInstance();
	            boolean populated = false;
                System.out.println(nestedInstance);
	            for (Field nestedField : listType.getDeclaredFields()) {
	            	//System.out.println("Feild:"+nestedField);
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
	                if((nestedList!=null && !nestedList.contains(nestedInstance)))
	                nestedList.add(nestedInstance);
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