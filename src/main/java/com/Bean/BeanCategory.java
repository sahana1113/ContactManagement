package com.Bean;

public class BeanCategory implements Bean{

    private int category_id;
    private int user_id;
    private int contact_id;
    private String category_name;
    private long created_time;
	private long updated_time;
    
	public long getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(long updated_time) {
		this.updated_time = updated_time;
	}
	public BeanCategory() {
    	
    }
    public BeanCategory(int c_id, String category) {
        this.category_id = c_id;
        this.category_name = category;
    }
    public BeanCategory(String categoryName) {
		this.category_name=categoryName;
	}
    public BeanCategory(int user_id) {
		this.user_id=user_id;
	}
    @Override
	public int getUser_id() {
		return user_id;
	}
    @Override
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
    public long getCreated_time() {
		return created_time;
	}
	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getPrimaryId() {
		return category_id;
	}
	@Override
	public String getPrimaryColumn() {
		// TODO Auto-generated method stub
		return "category_id";
	}
}
