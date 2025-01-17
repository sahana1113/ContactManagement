package com.Bean;

import java.util.ArrayList;
import java.util.List;

public class BeanUserDetails implements Bean {

	private String username;
	private String usermail;
	private String gender;
	private String phonenumber;
	private String birthday;
	private String password;
	private long created_time;
	private long updated_time;
	private boolean flag;
	private boolean is_primary;
	private int user_id;
	private int id;
	private List<BeanMail> altMail = new ArrayList<>();
	private List<BeanPhone> altPhone = new ArrayList<>();

	public BeanUserDetails() {
	}

	public BeanUserDetails(String username, String phonenumber) {
		this.username = username;
		this.phonenumber = phonenumber;
	}

	public BeanUserDetails(int userid) {
		this.user_id=userid;
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
	

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public long getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(long updated_time) {
		this.updated_time = updated_time;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isIs_primary() {
		return is_primary;
	}

	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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
  
	public List<BeanMail> getAltMail() {
		return altMail;
	}

	public void setAltMail(List<BeanMail> allMail) {
		this.altMail = allMail;
	}

	public List<BeanPhone> getAltPhone() {
		return altPhone;
	}

	public void setAltPhone(List<BeanPhone> allPhone) {
		this.altPhone = allPhone;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
	    return "BeanUserDetails {" +
	            "username='" + username + '\'' +
	            ", usermail='" + usermail + '\'' +
	            ", gender='" + gender + '\'' +
	            ", phonenumber='" + phonenumber + '\'' +
	            ", birthday='" + birthday + '\'' +
	            ", password='" + password + '\'' +
	            ", flag=" + flag +
	            ", is_primary=" + is_primary +
	            ", user_id=" + user_id +
	            ", altMail=" + altMail.toString() +
	            ", altPhone=" + altPhone.toString() +
	            '}';
	}

	@Override
	public int getPrimaryId() {
		return user_id;
	}

	@Override
	public String getPrimaryColumn() {
		// TODO Auto-generated method stub
		return "user_id";
	}

}
