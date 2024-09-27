package com.example;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.example.ContactDetailsBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserContactDao implements Dao{
	int user_id;
	public UserContactDao(int user_id){
		this.user_id=user_id;
	}
	public UserContactDao() {
		
	}
	public List<String> getAllCategories(int user_id) throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select category_name from categories where user_id=?;");
        pst.setInt(1, user_id);
        ResultSet rs=pst.executeQuery();
        List<String>list =new ArrayList<>();
        while(rs.next())
        {
        	list.add(rs.getString("category_name"));
        }
        return list;
	}
	public String getUsername() throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select username from userDetails where user_id=?;");
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
        {
        	return rs.getString(1);
        }
        return "";
        
	}
	public String getUsermail() throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select usermail from userDetails where user_id=?;");
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
        {
        	return rs.getString(1);
        }
        return "";
        
	}
	public List<ContactDetailsBean> Contactdisplay() throws SQLException
	{
		List<ContactDetailsBean>list=new ArrayList<>();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select name,phonenumber,contact_id from contactDetails where user_id=? order by name ");
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
     //   System.out.print(user_id);
        while(rs.next())
        {
        	//System.out.println(rs.getString("name"));
        	ContactDetailsBean c=new ContactDetailsBean(rs.getString("name"),rs.getString("phonenumber"),rs.getInt("contact_id"));
        	list.add(c);
        }
       // System.out.println(list.get(0));
        return list;
	}

	public boolean contactDetailsRegister(ContactDetailsBean user) {
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
			 
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst = con.prepareStatement("INSERT INTO contactDetails (name,mail,phonenumber,gender,birthday,location,user_id) VALUES (?, ?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	            pst.setString(1,user.getContactname());
	            pst.setString(2,user.getContactmail());
	            pst.setString(3,user.getPhonenumber()); 
	            pst.setString(4,user.getGender());     
	            pst.setString(5,user.getBirthday());
	            pst.setString(6, user.getLocation());
	            pst.setInt(7,user_id);
	            pst.executeUpdate();
	            rs=true;
	            ResultSet key=pst.getGeneratedKeys();
	            int contact_id = 0;
	            if (key.next()) {
	                contact_id = key.getInt(1); // Fetch the generated contact ID
	                user.setContact_id(contact_id); 
	            }
	            List<String> category=user.getCategory();
	            for(String s:category) {
	            PreparedStatement pst1=con.prepareStatement("Select category_id from categories where category_name=? && user_id=?;");
	            pst1.setString(1, s);
	            pst1.setInt(2, user_id);
	            ResultSet rs1=pst1.executeQuery();
	            int c_id=0;
	            if(rs1.next())
	            {
	            	c_id=rs1.getInt("category_id");
	            }
	            PreparedStatement pst2=con.prepareStatement("Insert into category_users(category_id,contact_id) values (?,?);");
	            pst2.setInt(1,c_id);
	            pst2.setInt(2, user.getContact_id());
	            pst2.executeUpdate();
	            
	     }
		}
		catch (Exception e) {
            e.printStackTrace();
          
        }
		return rs;
	}
	public ContactDetailsBean getContactDetailsById(int contact_id) throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select * from contactDetails where contact_id=?;");
        ContactDetailsBean contact=new ContactDetailsBean();
        pst.setInt(1,contact_id);
        ResultSet rs=pst.executeQuery();
        if (rs.next()) {
        contact.setContactmail(rs.getString("mail"));
        contact.setContactname(rs.getString("name"));
        
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
        contact.setCategory(list);
        return contact;
        
	}
	public UserDetailsBean getUserDetailsById(int user_id) throws SQLException
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select * from userDetails where user_id=?;");
        UserDetailsBean contact=new UserDetailsBean();
        pst.setInt(1,user_id);
        ResultSet rs=pst.executeQuery();
        if (rs.next()) {
        contact.setUsermail(rs.getString("usermail"));
        contact.setUsername(rs.getString("username"));
        contact.setPhonenumber(rs.getString("phonenumber"));
        contact.setGender(rs.getString("gender"));
        contact.setBirthday(rs.getString("birthday"));
        }
        
        PreparedStatement pst1=con.prepareStatement("select user_email from all_mail where user_id=? && is_primary=false;");
        pst1.setInt(1, user_id);
        ResultSet rs1=pst1.executeQuery();
        List<String>mail=new ArrayList<>();
        while (rs1.next()) {
        	mail.add(rs1.getString(1));
        }
        contact.setAllMail(mail);
        PreparedStatement pst2=con.prepareStatement("select phone from all_phone where user_id=? && is_primary=false;");
        pst2.setInt(1, user_id);
        ResultSet rs2=pst2.executeQuery();
        List<String>phone=new ArrayList<>();
        while (rs2.next()) {
        	phone.add(rs2.getString(1));
        }
        contact.setAllPhone(phone);
        	contact.setTotal_contacts(getTotalContacts(user_id));
        return contact;
        
	}
    public UserDetailsBean getPrimeDetailsById(int userId) throws SQLException{
    	UserDetailsBean user=new UserDetailsBean();
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select usermail,phonenumber from userDetails where user_id=?;");
        pst.setInt(1, userId);
        ResultSet rs=pst.executeQuery();
        if(rs.next()) {
        user.setUsermail(rs.getString("usermail"));
        user.setPhonenumber(rs.getString("phonenumber"));
        }
        return user;
    }
    public int getTotalContacts(int userId) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM contactDetails WHERE user_id = ?");
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            return rs.getInt(1);
        }
        
        return 0;
    }
    public List<String> getCategoriesByUserId() throws SQLException
    {
    	List<String>categories=new ArrayList<>();
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("Select category_name from categories where user_id=?");
        pst.setInt(1, user_id);
        ResultSet rs = pst.executeQuery();
        while(rs.next())
        {
        	categories.add(rs.getString("category_name"));
        }
		return categories;
    	
    }
    
}
