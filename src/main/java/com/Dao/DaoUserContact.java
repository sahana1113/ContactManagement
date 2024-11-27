package com.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

import com.Bean.BeanCategory;
import com.Bean.BeanContactDetails;
import com.Bean.BeanUserDetails;
import com.Query.Column;
import com.Query.Enum.Tables;
import com.Query.Enum.UserDetails;
import com.Query.QueryLayer;
import com.example.HikariCPDataSource;
import com.rowMapper.UserRowMapper;
public class DaoUserContact{
	int user_id;
	public DaoUserContact(int user_id){
		this.user_id=user_id;
	}
	public DaoUserContact() {
		
	}
	public String getCategoryName(int id) throws SQLException, NamingException{
        Connection con = HikariCPDataSource.getConnection();
		PreparedStatement pst = con.prepareStatement("select category_name from categories where category_id=?;");
        pst.setInt(1,id);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
        {
        	return rs.getString(1);
        }
        return "";
	}
	public String getUsername() throws SQLException, NamingException
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		List<BeanUserDetails>user=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.USER_DETAILS}, 
				new Column[] {UserDetails.username},
				new Column[] {UserDetails.user_id},
				null,
		        BeanUserDetails.class,
				user1,
				null
		);
//        Connection con = HikariCPDataSource.getConnection();
//        PreparedStatement pst = con.prepareStatement("select username from USER_DETAILS where user_id=?;");
//        pst.setInt(1,user_id);
//        ResultSet rs=pst.executeQuery();
//        if(rs.next())
//        {
//        	return rs.getString(1);
        return user.get(0).getUsername();
     }
	
	public String getUsermail() throws SQLException, NamingException
	{
//        Connection con = HikariCPDataSource.getConnection();
//        PreparedStatement pst = con.prepareStatement("select usermail from USER_DETAILS where user_id=?;");
//        pst.setInt(1,user_id);
//        ResultSet rs=pst.executeQuery();
//        if(rs.next())
//        {
//        	return rs.getString(1);
//        }
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
		            new Tables[] {Tables.USER_DETAILS},
		            new Column[] {UserDetails.usermail},  
		            new Column[] {UserDetails.user_id},  
		            null,
		            BeanUserDetails.class,
		            user1,
		            null
		    );
		    if (!userDetailsList.isEmpty()) {
		        return userDetailsList.get(0).getUsermail();  
		    }
        return "";
	}
	public String getUserphone() throws SQLException, NamingException
	{
		BeanUserDetails user1=new BeanUserDetails();
		user1.setUser_id(user_id);
		 List<BeanUserDetails> userDetailsList = QueryLayer.buildSelectQuery(
		            new Tables[] {Tables.USER_DETAILS},
		            new Column[] {UserDetails.phonenumber},  
		            new Column[] {UserDetails.user_id},  
		            null,
		            BeanUserDetails.class,
		            user1,
		            null
		    );
		    if (!userDetailsList.isEmpty()) {
		        return userDetailsList.get(0).getPhonenumber();  
		    }
        return "";
	}
	
	public List<BeanContactDetails> Contactdisplay() throws SQLException, NamingException
	{
		List<BeanContactDetails>list=new ArrayList<>();
        Connection con = HikariCPDataSource.getConnection();
		PreparedStatement pst = con.prepareStatement("select name,phonenumber,contact_id from contactDetails where user_id=? order by name ");
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
        while(rs.next())
        {
        	if(!in_archieve(rs.getInt("contact_id"),user_id)) {
        	BeanContactDetails c=new BeanContactDetails(rs.getString("name"),rs.getString("phonenumber"),rs.getInt("contact_id"));
        	//System.out.print(rs.getString("name"));
        	list.add(c);
        	}
        }
        
        return list;
	}
	private boolean in_archieve(int con_id, int user_id) throws SQLException, NamingException {
        Connection con = HikariCPDataSource.getConnection();
		PreparedStatement pst = con.prepareStatement("select category_id from categories where category_name=?");
        pst.setString(1,"Archieved");
        ResultSet rs=pst.executeQuery();
        int c_id=0;
        if(rs.next())
        {
        	c_id=rs.getInt("category_id");
        }
        PreparedStatement pst1=con.prepareStatement("select * from category_users where category_id=? && contact_id=?;");
        pst1.setInt(1, c_id);
        pst1.setInt(2, con_id);
        ResultSet rs1=pst1.executeQuery();
        if(rs1.next()) {
        	return true;
        }
		return false;
	}
	public boolean contactDetailsRegister(BeanContactDetails user) {
		boolean rs=false;
		try {		
            Connection con = HikariCPDataSource.getConnection();
	            PreparedStatement pst = con.prepareStatement("INSERT INTO contactDetails (name,mail,phonenumber,gender,birthday,location,user_id,created_time) VALUES (?, ?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	            pst.setString(1,user.getName());
	            pst.setString(2,user.getMail());
	            pst.setString(3,user.getPhonenumber()); 
	            pst.setString(4,user.getGender());     
	            pst.setString(5,user.getBirthday());
	            pst.setString(6, user.getLocation());
	            pst.setInt(7,user_id);
	            pst.setLong(8, user.getCreated_time());
	            pst.executeUpdate();
	            rs=true;
	            ResultSet key=pst.getGeneratedKeys();
	            int contact_id = 0;
	            if (key.next()) {
	                contact_id = key.getInt(1);
	                user.setContact_id(contact_id); 
	            }
	            DaoRegisterLogin rld=new DaoRegisterLogin(user_id);
	            if(user.getCategory()!=null)
	            rld.insertCategory(user);
		}
		catch (Exception e) {
            e.printStackTrace();
          
        }
		return rs;
	}
	public BeanContactDetails getContactDetailsById(int contact_id) throws SQLException, NamingException
	{
        Connection con = HikariCPDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from contactDetails where contact_id=?;");
        BeanContactDetails contact=new BeanContactDetails();
        pst.setInt(1,contact_id);
        ResultSet rs=pst.executeQuery();
        if (rs.next()) {
        contact.setMail(rs.getString("mail"));
        contact.setName(rs.getString("name"));
        contact.setCreated_time(rs.getLong("created_time"));
        contact.setPhonenumber(rs.getString("phonenumber"));
        contact.setGender(rs.getString("gender"));
        contact.setBirthday(rs.getString("birthday"));
        contact.setLocation(rs.getString("location"));  
        }
        PreparedStatement pst1=con.prepareStatement("Select category_name from categories c INNER JOIN category_users cm ON c.category_id=cm.category_id where cm.contact_id=?");
        pst1.setInt(1, contact_id);
        ResultSet rs1=pst1.executeQuery();
        List<String>list=new ArrayList<>();
        while(rs1.next())
        {
        	list.add(rs1.getString("category_name"));
        }
        contact.setContact_id(contact_id);
        contact.setCategory(list);
        return contact;
        
	}
	public BeanUserDetails getUserDetailsById(int user_id) throws SQLException, NamingException
	{
        Connection con = HikariCPDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from userDetails where user_id=?;");
        BeanUserDetails contact=new BeanUserDetails();
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
        if (rs.next()) {
        contact.setUsermail(rs.getString("usermail"));
        contact.setUsername(rs.getString("username"));
        contact.setPhonenumber(rs.getString("phonenumber"));
        contact.setGender(rs.getString("gender"));
        contact.setBirthday(rs.getString("birthday"));
        }
        
        PreparedStatement pst1=con.prepareStatement("select usermail from all_mail where user_id=? && is_primary=false;");
        pst1.setInt(1, user_id);
        ResultSet rs1=pst1.executeQuery();
        List<String>mail=new ArrayList<>();
        while (rs1.next()) {
        	mail.add(rs1.getString(1));
        }
        contact.setAllMail(mail);
        PreparedStatement pst2=con.prepareStatement("select phonenumber from all_phone where user_id=? && is_primary=false;");
        pst2.setInt(1, user_id);
        ResultSet rs2=pst2.executeQuery();
        List<String>phone=new ArrayList<>();
        while (rs2.next()) {
        	phone.add(rs2.getString(1));
        }
        contact.setAllPhone(phone);
        return contact;
        
	}
    public BeanUserDetails getPrimeDetailsById(int userId) throws SQLException, NamingException{
    	BeanUserDetails user=new BeanUserDetails();
        Connection con = HikariCPDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("select usermail,phonenumber from userDetails where user_id=?;");
        pst.setInt(1, userId);
        ResultSet rs=pst.executeQuery();
        if(rs.next()) {
        user.setUsermail(rs.getString("usermail"));
        user.setPhonenumber(rs.getString("phonenumber"));
        }
        return user;
    }
    public List<BeanCategory> getCategoriesByUserId() throws SQLException, NamingException
    {
    	List<BeanCategory>categories=new ArrayList<>();
        Connection con = HikariCPDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement("Select category_name,category_id from categories where user_id=?");
        pst.setInt(1, user_id);
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
        	categories.add(new BeanCategory(rs.getInt("category_id"),rs.getString("category_name")));
        }
		return categories;
    	
    }
    public List<BeanContactDetails> getContactsInCategory(int category) throws SQLException, NamingException
    {
    	List<BeanContactDetails>categories=new ArrayList<>();
        Connection con = HikariCPDataSource.getConnection();       
        int c_id=category;
        PreparedStatement pst1 = con.prepareStatement("select contact_id from category_users where category_id=?;");
        pst1.setInt(1, c_id);
        ResultSet rs1=pst1.executeQuery();
        while(rs1.next()) {
        	categories.add(getContactDetailsById(rs1.getInt("contact_id")));
        }
        return categories;
        
    }
    public List<BeanContactDetails> getContactsNotInCategory(int category) throws SQLException, NamingException {
    	List<BeanContactDetails> contactsNotInCategory = new ArrayList<>();
        
        List<BeanContactDetails> contactsInCategory = getContactsInCategory(category);

        Set<Integer> contactsInCategoryIds = new HashSet<>();
        for (BeanContactDetails contact : contactsInCategory) {
            contactsInCategoryIds.add(contact.getContact_id());
        }
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
