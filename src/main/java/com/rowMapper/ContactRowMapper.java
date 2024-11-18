package com.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.Bean.BeanContactDetails;

public class ContactRowMapper implements RowMapper<BeanContactDetails> {
    @Override
    public BeanContactDetails mapRow(ResultSet rs) throws SQLException {
        BeanContactDetails contact = new BeanContactDetails();
        contact.setContact_id(rs.getInt("contact_id"));
        contact.setUser_id(rs.getInt("user_id"));
        contact.setName(rs.getString("name"));
        contact.setMail(rs.getString("mail"));
        contact.setPhonenumber(rs.getString("phonenumber"));
        contact.setGender(rs.getString("gender"));
        contact.setBirthday(String.valueOf(rs.getDate("birthday")));
        contact.setLocation(rs.getString("location"));
        contact.setCreated_time(rs.getLong("created_time"));
        return contact;
    }
}
