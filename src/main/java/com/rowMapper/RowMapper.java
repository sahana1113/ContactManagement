package com.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.Bean.BeanUserDetails;

@FunctionalInterface
public interface RowMapper<T> {
   T mapRow(ResultSet rs) throws SQLException;
}
