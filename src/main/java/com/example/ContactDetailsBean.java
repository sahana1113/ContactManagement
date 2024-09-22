package com.example;

public class ContactDetailsBean {
     String contactname;
     String contactmail;
     String gender;
     String phonenumber;
     String birthday;
     String location;
     int contact_id;
	public ContactDetailsBean() {

	}
	public ContactDetailsBean(String contactname, String phonenumber,int contact_id) {

		this.contactname = contactname;
		this.phonenumber = phonenumber;
		this.contact_id=contact_id;
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
