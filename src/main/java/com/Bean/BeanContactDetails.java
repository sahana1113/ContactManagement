package com.Bean;
import java.util.*;
public class BeanContactDetails {
    
	String contactname;
     String contactmail;
     String gender;
     String phonenumber;
     String birthday;
     String location;
     int contact_id,user_id;
     public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	String alt_mail,alt_phone;
     List<String>category;
	public  List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public BeanContactDetails() {

	}
	public BeanContactDetails(String contactname, String phonenumber,int contact_id) {

		this.contactname = contactname;
		this.phonenumber = phonenumber;
		this.contact_id=contact_id;
	}
	 public String getAlt_mail() {
			return alt_mail;
		}
		public void setAlt_mail(String alt_mail) {
			this.alt_mail = alt_mail;
		}
		public String getAlt_phone() {
			return alt_phone;
		}
		public void setAlt_phone(String alt_phone) {
			this.alt_phone = alt_phone;
		}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getContactmail() {
		return contactmail;
	}
	public void setContactmail(String contactmail) {
		this.contactmail = contactmail;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
     
}
