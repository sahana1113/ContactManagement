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
import com.Query.Enum.Tables;
import com.Query.Enum.Test;
import com.Query.Enum.UserDetails;
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
		Condition condition=new Condition(Categories.category_id,"=");
		List<BeanCategory>list=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.CATEGORIES}, 
				new Column[] {Categories.category_name} , 
				condition, 
				BeanCategory.class,
				obj, 
				null,null);
		//System.out.print(list);
        return list.get(0).getCategory_name();
	}
	public String getUsername() throws Exception
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=");
		//List<BeanUserDetails>list=QueryLayer.getQueryBuilder().select(new Column[] {UserDetails.username}).from(Tables.USER_DETAILS).conditions(new Column[] {UserDetails.user_id}, null, true).build();
		List<BeanUserDetails>user=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.USER_DETAILS},
				new Column[] {UserDetails.username},
				condition,
		        BeanUserDetails.class,
				user1,
			   null,null
		);
        return user.get(0).getUsername();
     }
	
	public String getUsermail() throws Exception
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=");
		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
		            new Tables[] {Tables.USER_DETAILS},
		            new Column[] {UserDetails.usermail},  
		            condition,
		            BeanUserDetails.class,
		            user1,
		            null,null
		    );
		    if (!userDetailsList.isEmpty()) {
		        return userDetailsList.get(0).getUsermail();  
		    }
        return "";
	}
	public String getUserphone() throws Exception
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=");
		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
		            new Tables[] {Tables.USER_DETAILS},
		            new Column[] {UserDetails.phonenumber},  
		            condition,
		            BeanUserDetails.class,
		            user1,
		            null,null
		    );
		    if (!userDetailsList.isEmpty()) {
		        return userDetailsList.get(0).getPhonenumber();  
		    }
        return "";
	}
	
	public List<BeanContactDetails> Contactdisplay() throws Exception
	{
		BeanContactDetails obj=new BeanContactDetails(user_id);
		obj.setIs_archive(false);
        Condition condition1=new Condition(ContactDetails.user_id,"=");
        Condition condition2=new Condition(ContactDetails.is_archive,"=");
        Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition2);
        Condition condition3=new Condition(ContactDetails.name,"=");
        Condition or=new Condition("OR").addSubCondition(and).addSubCondition(condition3);
        System.out.print(or.toString());
		List<BeanContactDetails>list=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.CONTACT_DETAILS},
				new Column[] {ContactDetails.name,ContactDetails.phonenumber,ContactDetails.contact_id}, 
				and,
				BeanContactDetails.class,
				obj,
				null,null);
        return list;
	}
	public boolean contactDetailsRegister(BeanContactDetails user) throws Exception {
		boolean rs=false;
		user.setUser_id(user_id);
		int key=QueryLayer.buildInsertQuery(
				Tables.CONTACT_DETAILS, 
				user, 
				new Column[] {ContactDetails.name,ContactDetails.mail,ContactDetails.phonenumber,ContactDetails.gender,ContactDetails.birthday,ContactDetails.location,ContactDetails.user_id,ContactDetails.created_time});
		if(key!=-1)
		{
			user.setContact_id(key);
			rs=true;
		}
		 DaoRegisterLogin rld=new DaoRegisterLogin(user_id);
         if(user.getCategory()!=null)
         rld.insertCategory(user);
         return rs;
	}
	public BeanContactDetails getContactDetailsById(int contact_id) throws Exception
	{
		  BeanContactDetails obj=new BeanContactDetails();
		  obj.setContact_id(contact_id);
			Condition condition=new Condition(ContactDetails.contact_id,"=");
		  List<BeanContactDetails>list=QueryLayer.buildSelectQuery(
				  new Tables[] {Tables.CONTACT_DETAILS},
				  ContactDetails.getColumnNames(), 
				  condition, 
				  BeanContactDetails.class,
				  obj,
				  null,null);
		  List<BeanCategory> list2=QueryLayer.buildSelectQuery(
				  new Tables[] {Tables.CATEGORIES,Tables.CATEGORY_USERS},
				  new Column[] {Categories.category_name},
				  condition,
				  BeanCategory.class,
				  obj,
				  new Column[][] {{Categories.category_id,CategoryUsers.category_id}},"INNER JOIN");
		  list.get(0).setCategory(list2);
		  return list.get(0);
        
	}
	public BeanUserDetails getUserDetailsById(int user_id) throws Exception
	{
		BeanUserDetails obj=new BeanUserDetails();
		obj.setUser_id(user_id);
		Condition condition=new Condition(UserDetails.user_id,"=");
		List<BeanUserDetails> user=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.USER_DETAILS,Tables.ALL_MAIL,Tables.ALL_PHONE},
				new Column[] {UserDetails.birthday,UserDetails.phonenumber,UserDetails.usermail,UserDetails.username,UserDetails.gender,AllMail.altMail,AllPhone.altPhone},
				condition,
				BeanUserDetails.class,
				obj,
				new Column[][] {{UserDetails.user_id,AllMail.user_id},{UserDetails.user_id,AllPhone.user_id}},"LEFT JOIN");
		return user.get(0);
//        Connection con = HikariCPDataSource.getConnection();
//        PreparedStatement pst = con.prepareStatement("select * from userDetails where user_id=?;");
//        BeanUserDetails contact=new BeanUserDetails();
//        pst.setInt(1,user_id);
//        ResultSet rs=pst.executeQuery();
//        if (rs.next()) {
//        contact.setUsermail(rs.getString("altMail"));
//        contact.setUsername(rs.getString("username"));
//        contact.setPhonenumber(rs.getString("altPhone"));
//        contact.setGender(rs.getString("gender"));
//        contact.setBirthday(rs.getString("birthday"));
//        }
//        
//        PreparedStatement pst1=con.prepareStatement("select altMail from all_mail where user_id=? && is_primary=false;");
//        pst1.setInt(1, user_id);
//        ResultSet rs1=pst1.executeQuery();
//        List<BeanMail>mail=new ArrayList<>();
//        while (rs1.next()) {
//        	mail.add(new BeanMail(rs1.getString(1)));
//        }
//        contact.setAltMail(mail);
//        PreparedStatement pst2=con.prepareStatement("select altPhone from all_phone where user_id=? && is_primary=false;");
//        pst2.setInt(1, user_id);
//        ResultSet rs2=pst2.executeQuery();
//        List<BeanPhone>phone=new ArrayList<>();
//        while (rs2.next()) {
//        	phone.add(new BeanPhone(rs2.getString(1)));
//        }
//        contact.setAltPhone(phone);
//        return contact;
        
	}
    public BeanUserDetails getPrimeDetailsById(int userId) throws Exception{
    	BeanUserDetails user=new BeanUserDetails();
    	user.setUser_id(userId);
		Condition condition=new Condition(UserDetails.user_id,"=");
    	List<BeanUserDetails> list=QueryLayer.buildSelectQuery(
    			new Tables[] {Tables.USER_DETAILS},
    			new Column[] {UserDetails.phonenumber,UserDetails.usermail},
    			condition,
    			BeanUserDetails.class,
    			user,
    			null,null);
        return list.get(0);
    }
    public List<BeanCategory> getCategoriesByUserId() throws Exception
    {
    	BeanCategory obj=new BeanCategory();
    	obj.setUser_id(user_id);
		Condition condition=new Condition(Categories.user_id,"=");
    	List<BeanCategory>categories=QueryLayer.buildSelectQuery(
    			new Tables[] {Tables.CATEGORIES},
    			new Column[] {Categories.category_name,Categories.category_id},
    			condition,
    			BeanCategory.class, 
    			obj, null,null);
		return categories;
    	
    }
    public List<BeanContactDetails> getContactsInCategory(int category) throws Exception
    {
    	BeanCategory obj=new BeanCategory();
    	obj.setCategory_id(category);
		Condition condition=new Condition(CategoryUsers.category_id,"=");
    	List<BeanContactDetails>categories=QueryLayer.buildSelectQuery(
    			new Tables[] {Tables.CONTACT_DETAILS,Tables.CATEGORY_USERS},
    			new Column[] {ContactDetails.contact_id,ContactDetails.name,ContactDetails.phonenumber},
    			condition,
    		    BeanContactDetails.class,
    		    obj,
    		    new Column[][] {{ContactDetails.contact_id,CategoryUsers.contact_id}},"INNER JOIN");
        return categories;
        
    }
    public List<BeanContactDetails> getContactsNotInCategory(int category) throws Exception {
    	
//    	List<BeanContactDetails> contactsNotInCategory = QueryLayer.buildSelectQuery(
//    			new Tables[] {Tables.CONTACT_DETAILS,Tables.CATEGORY_USERS},
//    			ContactDetails.getColumnNames(),
//    			null, null, null, STR, null);
//        
    	List<BeanContactDetails> contactsNotInCategory = new ArrayList<>();
        List<BeanContactDetails> contactsInCategory = getContactsInCategory(category);

        Set<Integer> contactsInCategoryIds = new HashSet<>();
        for (BeanContactDetails contact : contactsInCategory) {
            contactsInCategoryIds.add(contact.getContact_id());
        }
//    	List<BeanContactDetails> contactsNotInCategory = QueryLayer.buildSelectQuery(
//		new Tables[] {Tables.CONTACT_DETAILS,Tables.CATEGORY_USERS},
//		ContactDetails.getColumnNames(),
//		null, null, null, STR, null);
//
        Connection con = HikariCPDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT contact_id FROM contactDetails WHERE user_id=?;");
        pst.setInt(1, user_id);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int contactId = rs.getInt("contact_id");
            if (!contactsInCategoryIds.contains(contactId)) {
                contactsNotInCategory.add(getContactDetailsById(contactId));
            }
        }

        return contactsNotInCategory;
    } 
}
//SELECT 
//ud.usermail, 
//ud.username, 
//ud.phonenumber, 
//ud.gender, 
//ud.birthday, 
//am.usermail AS alt_usermail, 
//ap.phonenumber AS alt_phonenumber
//FROM 
//userDetails ud
//LEFT JOIN 
//all_mail am ON ud.user_id = am.user_id AND am.is_primary = false
//LEFT JOIN 
//all_phone ap ON ud.user_id = ap.user_id AND ap.is_primary = false
//WHERE 
//ud.user_id = ?
