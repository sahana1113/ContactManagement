package com.Bean;

import java.sql.Timestamp;

public class BeanServer {
    int id;
    String ip_address;
    int port_number;
    Timestamp created_time;
    
	public BeanServer() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public int getPort_number() {
		return port_number;
	}
	public void setPort_number(int port_number) {
		this.port_number = port_number;
	}
	public Timestamp getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
    
}
