package com.Bean;

import java.util.ArrayList;
import java.util.List;

public class BeanContactDetails implements Bean{

    private String name;
    private String mail;
    private String gender;
    private String phonenumber;
    private String birthday;
    private String location;
    private int contact_id;
    private int user_id;
    private List<BeanCategory> category=new ArrayList<>();
    private String category_name;
    private boolean is_archive;
    private long created_time; 
	private long updated_time;
    public BeanContactDetails() {
    }
    public BeanContactDetails(String contactname, String phonenumber, int contact_id) {
        this.name = contactname;
        this.phonenumber = phonenumber;
        this.contact_id = contact_id;
    }

    public BeanContactDetails(int user_id) {
		this.user_id=user_id;
	}
	public long getCreated_time() {
		return created_time;
	}
	public void setCreated_time(long createdTimeInEpoch) {
		this.created_time = createdTimeInEpoch;
	}
    
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String contactname) {
        this.name = contactname;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String contactmail) {
        this.mail = contactmail;
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

    public List<BeanCategory> getCategory() {
        return category;
    }

    public void setCategory(List<BeanCategory> list) {
        this.category = list;
    }

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public boolean isIs_archive() {
		return is_archive;
	}

	public void setIs_archive(boolean is_archive) {
		this.is_archive = is_archive;
	}
	
	public long getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(long updated_time) {
		this.updated_time = updated_time;
	}
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
	    String categoryString = (category != null) ? category.toString() : "null";
	    return "BeanContactDetails{" +
	            "name='" + name + '\'' +
	            ", mail='" + mail + '\'' +
	            ", gender='" + gender + '\'' +
	            ", phonenumber='" + phonenumber + '\'' +
	            ", birthday='" + birthday + '\'' +
	            ", location='" + location + '\'' +
	            ", contact_id=" + contact_id +
	            ", user_id=" + user_id +
	            ", category=" + categoryString +
	            ", is_archive=" + is_archive +
	            ", created_time=" + created_time +
	            '}';
	}

}
