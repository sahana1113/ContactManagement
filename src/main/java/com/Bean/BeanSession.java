package com.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanSession {
      String session_id;
      int user_id;
      LocalDateTime creation_time;
      LocalDateTime accessed_time;
      LocalDateTime expiry_time;
      private static List<BeanSession> list=new ArrayList<>();
	public BeanSession(String session_id, LocalDateTime accessed_time) {
		this.session_id = session_id;
		this.accessed_time = accessed_time;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public LocalDateTime getCreation_time() {
		return creation_time;
	}
	public void setCreation_time(LocalDateTime creation_time) {
		this.creation_time = creation_time;
	}
	public LocalDateTime getAccessed_time() {
		return accessed_time;
	}
	public void setAccessed_time(LocalDateTime accessed_time) {
		this.accessed_time = accessed_time;
	}
	public LocalDateTime getExpiry_time() {
		return expiry_time;
	}
	public void setExpiry_time(LocalDateTime expiry_time) {
		this.expiry_time = expiry_time;
	}
	public static List<BeanSession> getList() {
		return list;
	}
	public static void setList(List<BeanSession> list) {
		BeanSession.list = list;
	} 
	 
		 
}
