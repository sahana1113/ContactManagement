package com.Dao;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Bean.*;
import com.Query.Column;
import com.Query.Condition;
import com.Query.Enum.AllMail;
import com.Query.Enum.AllPhone;
import com.Query.Enum.Categories;
import com.Query.Enum.CategoryUsers;
import com.Query.Enum.ContactDetails;
import com.Query.Enum.Default;
import com.Query.Enum.Tables;
import com.Query.Enum.UserDetails;
import com.Query.Join;
import com.Query.QueryLayer;
import com.example.HikariCPDataSource;
public class DaoUserContact{
	int user_id;
	public DaoUserContact(int user_id){
		this.user_id=user_id;
	}
	public DaoUserContact() {
		
	}
	public String getCategoryName(int id) throws Exception{
		BeanCategory obj=new BeanCategory();
		obj.setCategory_id(id);
		Tables.USER_DETAILS.getClass();
		Condition condition=new Condition(Categories.category_id,"=",false);
		List<BeanCategory>list=QueryLayer.buildSelectQuery(
				Tables.CATEGORIES, 
				new Column[] {Categories.category_name} , 
				condition, 
				BeanCategory.class,
				obj, 
				null,new Column[] {Categories.category_id});
        return list.get(0).getCategory_name();
	}
	public String getUsername() throws Exception
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=",false);
		//List<BeanUserDetails>list=QueryLayer.getQueryBuilder().select(new Column[] {UserDetails.username}).from(Tables.USER_DETAILS).conditions(new Column[] {UserDetails.user_id}, null, true).build();
		
		List<BeanUserDetails>user=QueryLayer.buildSelectQuery(
				Tables.USER_DETAILS,
				new Column[] {UserDetails.username},
				condition,
		        BeanUserDetails.class,
				user1,
			   null,new Column[] {UserDetails.user_id});
        return user.get(0).getUsername();
     }
	
	public String getUsermail() throws Exception
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=",false);
		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
		            Tables.USER_DETAILS,
		            new Column[] {UserDetails.usermail},  
		            condition,
		            BeanUserDetails.class,
		            user1,
		            null,new Column[] {UserDetails.user_id} );
		    if (!userDetailsList.isEmpty()) {
		        return userDetailsList.get(0).getUsermail();  
		    }
        return "";
	}
//	public String getUserphone() throws Exception
//	{
//		BeanUserDetails user1=new BeanUserDetails();
//		user1.setUser_id(user_id);
//		Condition condition=new Condition(UserDetails.user_id,"=",false);
//		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
//		            Tables.USER_DETAILS,
//		            new Column[] {UserDetails.phonenumber},  
//		            condition,
//		            BeanUserDetails.class,
//		            user1,
//		            null,new Column[] {UserDetails.user_id});
//		    if (!userDetailsList.isEmpty()) {
//		        return userDetailsList.get(0).getPhonenumber();  
//		    }
//        return "";
//	}
	
	public List<BeanContactDetails> Contactdisplay() throws Exception
	{
		BeanContactDetails obj=new BeanContactDetails(user_id);
		obj.setIs_archive(false);
        Condition condition1=new Condition(ContactDetails.user_id,"=",false);
        Condition condition2=new Condition(ContactDetails.is_archive,"=",false);
        Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition2);
		List<BeanContactDetails>list=QueryLayer.buildSelectQuery(
				Tables.CONTACT_DETAILS,
				new Column[] {ContactDetails.name,ContactDetails.phonenumber,ContactDetails.contact_id}, 
				and,
				BeanContactDetails.class,
				obj,
				null,new Column[] {ContactDetails.user_id,ContactDetails.is_archive});
        return list;
	}
	
	public boolean contactDetailsRegister(BeanContactDetails user,BeanAudit audit) throws Exception {
		boolean rs=false;
		user.setUser_id(user_id);
		int key=QueryLayer.buildInsertQuery(
				Tables.CONTACT_DETAILS, 
				user,
				audit,
				new Column[] {ContactDetails.name,ContactDetails.mail,ContactDetails.phonenumber,ContactDetails.gender,ContactDetails.birthday,ContactDetails.location,ContactDetails.user_id,ContactDetails.created_time,ContactDetails.updated_time});
		if(key!=-1)
		{
			user.setContact_id(key);
			rs=true;
		}
		 DaoRegisterLogin rld=new DaoRegisterLogin(user_id);
         if(user.getCategory()!=null)
         rld.insertCategory(user,audit);
         return rs;
	}
	
	public BeanContactDetails getContactDetailsById(int contact_id,int user_id) throws Exception
	{
		  BeanContactDetails obj=new BeanContactDetails();
		  obj.setContact_id(contact_id);
		  obj.setUser_id(user_id);
			Condition condition=new Condition(ContactDetails.contact_id,"=",true);
			Condition condition1=new Condition(ContactDetails.user_id,"=",true);
			Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition);
			Join join1=new Join("LEFT",Tables.CATEGORY_USERS).on(ContactDetails.contact_id, "=", CategoryUsers.contact_id);
			Join join2=new Join("LEFT",Tables.CATEGORIES).on(CategoryUsers.category_id, "=", Categories.category_id);
			
		  List<BeanContactDetails>list=QueryLayer.buildSelectQuery(
				  Tables.CONTACT_DETAILS,
				  new Column[] {ContactDetails.contact_id, 
						  ContactDetails.user_id, 
						  ContactDetails.name, 
						  ContactDetails.mail, 
						  ContactDetails.phonenumber, 
						  ContactDetails.gender, 
						  ContactDetails.birthday, 
						  ContactDetails.location, 
						  ContactDetails.created_time, 
						  ContactDetails.is_archive,
						  ContactDetails.updated_time,
						  Categories.category_name} ,
				  and, 
				  BeanContactDetails.class,
				  obj,
				  new Join[] {join1,join2},
				  new Column[] {ContactDetails.user_id,ContactDetails.contact_id});
		  return list.get(0);
        
	}
	public BeanUserDetails getUserDetailsById(int user_id) throws Exception
	{
		BeanUserDetails obj=new BeanUserDetails();
		obj.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=",true);
		Join join1=new Join("LEFT",Tables.ALL_MAIL).on(UserDetails.user_id,"=",AllMail.user_id).on(AllMail.is_primary, "=", Default.FALSE);
		Join join2=new Join("LEFT",Tables.ALL_PHONE).on(UserDetails.user_id,"=",AllPhone.user_id).on(AllPhone.is_primary, "=", Default.FALSE);
		
		List<BeanUserDetails> user=QueryLayer.buildSelectQuery(
				Tables.USER_DETAILS,
				new Column[] {UserDetails.birthday,UserDetails.phonenumber,UserDetails.usermail,UserDetails.username,UserDetails.gender,AllMail.altMail,AllPhone.altPhone,AllMail.email_id,AllPhone.phone_id},
				condition,
				BeanUserDetails.class,
				obj,
				new Join[] {join1,join2},new Column[] {UserDetails.user_id});
				
		return user.get(0);
        
	}
	
    public BeanUserDetails getPrimeDetailsById(int userId) throws Exception{
    	BeanUserDetails user=new BeanUserDetails();
    	user.setUser_id(userId);
		Condition condition=new Condition(UserDetails.user_id,"=",false);
    	List<BeanUserDetails> list=QueryLayer.buildSelectQuery(
    			Tables.USER_DETAILS,
    			new Column[] {UserDetails.phonenumber,UserDetails.usermail},
    			condition,
    			BeanUserDetails.class,
    			user,
    			null,new Column[] {UserDetails.user_id});
        return list.get(0);
    }
   
    public List<BeanCategory> getCategoriesByUserId() throws Exception
    {
    	BeanCategory obj=new BeanCategory();
    	obj.setUser_id(user_id);
		Condition condition=new Condition(Categories.user_id,"=",false);
    	List<BeanCategory>categories=QueryLayer.buildSelectQuery(
    			Tables.CATEGORIES,
    			new Column[] {Categories.category_name,Categories.category_id},
    			condition,
    			BeanCategory.class, 
    			obj,null,new Column[] {Categories.user_id});
		return categories;
    	
    }
    public List<BeanContactDetails> getContactsInCategory(int category) throws Exception
    {
    	BeanCategory obj=new BeanCategory();
    	obj.setCategory_id(category);
		Condition condition=new Condition(CategoryUsers.category_id,"=",true);
		Join join=new Join("INNER",Tables.CATEGORY_USERS).on(ContactDetails.contact_id,"=",CategoryUsers.contact_id);
    	List<BeanContactDetails>categories=QueryLayer.buildSelectQuery(
    			Tables.CONTACT_DETAILS,
    			new Column[] {ContactDetails.contact_id,ContactDetails.name,ContactDetails.phonenumber},
    			condition,
    		    BeanContactDetails.class,
    		    obj,
    		    new Join[] {join},
    		    new Column[] {CategoryUsers.category_id});
        return categories;
        
    }
    public List<BeanContactDetails> getContactsNotInCategory(int category,int user_id) throws Exception {
    	BeanCategory category1=new BeanCategory(user_id);
    	category1.setCategory_id(category);
       Condition cond=new Condition(CategoryUsers.contact_id,"IS",Default.NULL);
       Condition con1=new Condition(ContactDetails.user_id,"=",Default.QUESTION_MARK);
       Condition and=new Condition("and").addSubCondition(cond).addSubCondition(con1);
       Join join=new Join("LEFT",Tables.CATEGORY_USERS).on(ContactDetails.contact_id,"=", CategoryUsers.contact_id).on(CategoryUsers.category_id, "=",category );
       Join join1=new Join("LEFT",Tables.CATEGORIES).on(CategoryUsers.category_id,"=", Categories.category_id).on(Categories.user_id, "=", user_id);
       
    	List<BeanContactDetails> contactsNotInCategory = QueryLayer.buildSelectQuery 
    			(Tables.CONTACT_DETAILS,
    			 new Column[] {ContactDetails.name,ContactDetails.phonenumber,ContactDetails.contact_id},
    			 and,
    			 BeanContactDetails.class,
    			 category1, 
    			 new Join[] {join,join1},
    			new Column[] {ContactDetails.user_id});

        return contactsNotInCategory;
    } 
}
