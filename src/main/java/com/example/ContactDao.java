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

public class ContactDao {
	int user_id;
	public ContactDao(int user_id){
		this.user_id=user_id;
	}
	public ContactDao() {
		
	}
	public List<ContactDetailsBean> display() throws SQLException
	{
		List<ContactDetailsBean>list=new ArrayList<>();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
        PreparedStatement pst = con.prepareStatement("select name,phonenumber,contact_id from contactDetails where user_id=?;");
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
        return contact;
        
	}

}
