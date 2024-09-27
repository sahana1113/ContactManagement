package com.example;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsBean {
     String username;
     String usermail;
     String gender;
     String phonenumber;
     public int getTotal_contacts() {
		return total_contacts;
	}
	public void setTotal_contacts(int total_contacts) {
		this.total_contacts = total_contacts;
	}
	public int getTotal_categories() {
		return total_categories;
	}
	public void setTotal_categories(int total_categories) {
		this.total_categories = total_categories;
	}
	String birthday;
     String password;
     int user_id,total_contacts,total_categories;
     String altmail;
     String altphone;
     List<String>allMail=new ArrayList<>();
     List<String>allPhone=new ArrayList<>();
     
     public List<String> getAllMail() {
		return allMail;
	}
	public void setAllMail(List<String> allMail) {
		this.allMail = allMail;
	}
	public List<String> getAllPhone() {
		return allPhone;
	}
	public void setAllPhone(List<String> allPhone) {
		this.allPhone = allPhone;
	}
	public String getAltmail() {
		return altmail;
	}
	public void setAltmail(String altmail) {
		this.altmail = altmail;
	}
	public String getAltphone() {
		return altphone;
	}
	public void setAltphone(String altphone) {
		this.altphone = altphone;
	}
	public UserDetailsBean() {
    	 
     }
	public UserDetailsBean(String username, String phonenumber) {
		this.username = username;
		this.phonenumber = phonenumber;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsermail() {
		return usermail;
	}
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
