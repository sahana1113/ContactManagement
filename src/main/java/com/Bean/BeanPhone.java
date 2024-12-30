package com.Bean;

import java.util.Objects;

public class BeanPhone implements Bean{
	private int user_id;
	private String altPhone;
	private boolean is_primary;
	private long created_time;
	private long updated_time;
	
	public BeanPhone() {
	}
	
	public BeanPhone(String string,int user_id) {
		this.altPhone=string;
		this.user_id=user_id;
	}

	public BeanPhone(String parameter) {
		this.altPhone=parameter;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAltPhone() {
		return altPhone;
	}

	public void setAltPhone(String phonenumber) {
		this.altPhone = phonenumber;
	}
	public boolean isIs_primary() {
		return is_primary;
	}
	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
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

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanPhone beanPhone = (BeanPhone) obj;
        return user_id == beanPhone.user_id && 
               is_primary == beanPhone.is_primary && 
               Objects.equals(altPhone, beanPhone.altPhone);
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(user_id, altPhone, is_primary);
    }
	
	
	
}
