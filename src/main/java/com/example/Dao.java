package com.example;

public interface Dao {
	public boolean UserDetailsRegister(UserDetailsBean user);
	public boolean credentialsInsert(UserDetailsBean user);
	public boolean allMailInsert(UserDetailsBean user);
	public boolean allPhoneInsert(UserDetailsBean user);
	public int validateLogin(String usermail,String password);
	public String hashPassword(String password);
	
}
