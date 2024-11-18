package com.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.Bean.BeanUserDetails;

public class UserRowMapper implements RowMapper<BeanUserDetails> {
    @Override
    public BeanUserDetails mapRow(ResultSet rs) throws SQLException {
        BeanUserDetails user = new BeanUserDetails();
//        if (rs.findColumn("user_id") > 0) {
//            user.setUser_id(rs.getInt("user_id"));
//        }
//        if (rs.findColumn("usermail") > 0) {
//            user.setUsermail(rs.getString("usermail"));
//        }
        if (rs.findColumn("username") > 0) {
            user.setUsername(rs.getString("username"));
        }
        
//        if (rs.findColumn("gender") > 0) {
//            user.setGender(rs.getString("gender"));
//        }
//        if (rs.findColumn("phonenumber") > 0) {
//            user.setPhonenumber(rs.getString("phonenumber"));
//        }
//        if (rs.findColumn("birthday") > 0) {
//            user.setBirthday(rs.getString("birthday"));
//        }
//        if (rs.findColumn("password") > 0) {
//            user.setPassword(rs.getString("password"));
//        }
        return user;
    }
}
