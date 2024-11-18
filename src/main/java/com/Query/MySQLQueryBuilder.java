package com.Query;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Bean.BeanUserDetails;
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
        String[] columnNames = Arrays.stream(columns)
                                      .map(Column::getColumnName) 
                                      .toArray(String[]::new);

        query.append("SELECT ").append(String.join(", ", columnNames));
        return this;
    }

    @Override
    public QueryBuilder from(Tables table) {
        query.append(" FROM ").append(table);
        return this;
    }

    @Override
    public QueryBuilder where(String condition) {
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
    public QueryBuilder conditions(Column[] conditionsColumns, String[] logics) {
    	this.where(conditionsColumns[0].getColumnName() + " = ?");
        for (int i = 1; i < conditionsColumns.length; i++) {
            String condition = conditionsColumns[i].getColumnName() + " = ?";
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
        query.append("INSERT INTO ").append(table)
             .append(" (").append(String.join(", ", columns)).append(")");
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
        query.append("UPDATE ").append(table);
        return this;
    }

    @Override
    public QueryBuilder set(String... columnValuePairs) {
        query.append(" SET ").append(String.join(", ", columnValuePairs));
        return this;
    }

    @Override
    public QueryBuilder deleteFrom(Tables table) {
        query.append("DELETE FROM ").append(table);
        return this;
    }

    @Override
    public String build() {
        return query.toString();
    }

	@Override
	public int executeInsert(String query, Object... obj) throws SQLException {
		Connection con=getCon();
		preparedStatement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        System.out.println(query);
        for (int i = 0; i < obj.length; i++) {
            preparedStatement.setObject(i + 1, obj[i]);
        }
        preparedStatement.executeUpdate();
        ResultSet key=preparedStatement.getGeneratedKeys();
        if(key.next())
        {
        	con.close();
        	return key.getInt(1);
        }
        con.close();
		return -1;
	}
	@Override
	public int executeUpdateDelete(String sql, Object... parameters) throws SQLException {
		Connection con=getCon();
	     preparedStatement = con.prepareStatement(sql);
	        for (int i = 0; i < parameters.length; i++) {
	            preparedStatement.setObject(i + 1, parameters[i]);
	        }
	        int k= preparedStatement.executeUpdate();
	        con.close();
	        return k;
	}

//	@Override
//	public <T> List<T> executeSelect(String query, Object[] obj, RowMapper<T> map) throws SQLException {
//		preparedStatement = con.prepareStatement(query);
//	    for (int i = 0; i < obj.length; i++) {
//	        preparedStatement.setObject(i + 1, obj[i]);
//	    }
//
//	    ResultSet resultSet = preparedStatement.executeQuery();
//	    List<T> results = new ArrayList<>();
//
//	    while (resultSet.next()) {
//	        results.add(map.mapRow(resultSet));
//	    }
//
//	    resultSet.close();
//	    return results;
//	}
	@Override
    public <T> List<T> executeSelect(String query, Object[] params, Class<T> type) throws SQLException {
		Connection con=getCon();
        preparedStatement = con.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> results = new ArrayList<>();

        while (resultSet.next()) {
            results.add(mapRowUsingReflection(resultSet, type));
        }
        
        resultSet.close();
        con.close();
        return results;
    }

    private <T> T mapRowUsingReflection(ResultSet rs, Class<T> type) throws SQLException {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Set<String> columnNames = new HashSet<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnLabel(i));
            }
            System.out.println(columnNames);
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = field.getName();
                if (columnNames.contains(columnName)) {
                	try {
                		Object value = rs.getObject(columnName);
                		if (value != null) {
                			field.set(instance, value);
                		}
                	} catch (SQLException e) {
                		e.printStackTrace();
                	}
                }
            }
            return instance;
        } catch (Exception e) {
            throw new SQLException("Error mapping row to " + type.getName(), e);
        }
    }


    
}
