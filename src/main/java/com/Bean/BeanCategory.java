package com.Bean;

public class BeanCategory {
   int c_id;
   String category;
   public BeanCategory(int c_id, String category) {
	this.c_id = c_id;
	this.category = category;
  }
public int getC_id() {
	return c_id;
}
public void setC_id(int c_id) {
	this.c_id = c_id;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
}
