package com.Bean;

/**
 * Represents a category_name with an ID and a name.
 * This class is used to manage category_name data in the application.
 * @author Sahana
 * @version 1.0
 */
public class BeanCategory implements Bean{

    private int category_id;
    private int user_id;
    private int contact_id;
    private String category_name;
    public BeanCategory() {
    	
    }
    public BeanCategory(int c_id, String category) {
        this.category_id = c_id;
        this.category_name = category;
    }
    public BeanCategory(String categoryName) {
		this.category_name=categoryName;
	}
    public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int c_id) {
        this.category_id = c_id;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category) {
        this.category_name = category;
    }
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
}
