package com.Bean;

import java.util.Objects;

public class BeanMail implements Bean{
	private int user_id;
	private String altMail;
	private boolean is_primary;
	private long created_time;
	private long updated_time;
	
	public BeanMail() {
	}
	
	public BeanMail(String string,int user_id) {
		this.altMail=string;
		this.user_id=user_id;
	}

	public BeanMail(String parameter) {
		this.altMail=parameter;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAltMail() {
		return altMail;
	}
	public void setAltMail(String usermail) {
		this.altMail = usermail;
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
        BeanMail beanMail = (BeanMail) obj;
        return user_id == beanMail.user_id && 
               is_primary == beanMail.is_primary && 
               Objects.equals(altMail, beanMail.altMail);
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(user_id, altMail, is_primary);
    }
}
