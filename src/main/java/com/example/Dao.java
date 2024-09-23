package com.example;

import java.sql.SQLException;
import java.util.List;

public interface Dao {
	public String getUsername() throws SQLException;
	public String getUsermail() throws SQLException;
	public List<ContactDetailsBean> Contactdisplay() throws SQLException;
	public boolean contactDetailsRegister(ContactDetailsBean user);
	public ContactDetailsBean getContactDetailsById(int contact_id) throws SQLException;
	public UserDetailsBean getUserDetailsById(int user_id) throws SQLException;
	public UserDetailsBean getPrimeDetailsById(int userId) throws SQLException;
	
	
}
