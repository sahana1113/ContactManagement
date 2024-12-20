package com.Bean;

import java.util.Objects;

public class BeanPhone implements Bean{
	private int user_id;
	private String altPhone;
	private boolean is_primary;
	
	public BeanPhone() {
	}
	
	public BeanPhone(String string) {
		this.altPhone=string;
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
