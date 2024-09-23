package com.example;

public interface DetailsDao {
	public boolean UserDetailsRegister(UserDetailsBean user);
	public boolean credentialsInsert(UserDetailsBean user);
	public boolean allMailInsert(UserDetailsBean user);
	public boolean allPhoneInsert(UserDetailsBean user);
	public int validateLogin(String usermail,String password);
	public String hashPassword(String password);
	public boolean updateUserDetails(UserDetailsBean user);
	public boolean addAltMail(UserDetailsBean user);
	public boolean addAltPhone(UserDetailsBean user);
	public boolean updatePrimaryMail(UserDetailsBean user);
	public boolean changePassword(UserDetailsBean user);
}
