package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterLoginDao implements DetailsDao{
	public boolean UserDetailsRegister(UserDetailsBean user) {
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst = con.prepareStatement("INSERT INTO userDetails (username,usermail,gender,phonenumber,birthday) VALUES (?, ?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	            pst.setString(1,user.getUsername());
	            pst.setString(2,user.getUsermail());
	            pst.setString(3,user.getGender());
	            pst.setString(4,user.getPhonenumber());      
	            pst.setString(5,user.getBirthday());
	            pst.executeUpdate();
	            rs=true;
	            ResultSet key=pst.getGeneratedKeys();
	            int userid=0;
	            if(key.next())
	            {
	            	user.setUser_id(key.getInt(1));
	            }
	
	     }
		catch (Exception e) {
            e.printStackTrace();
          
        }
		return rs;
	}
	public boolean credentialsInsert(UserDetailsBean user)
	{
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst2 = con.prepareStatement("INSERT INTO credentials(user_id,password) values(?,?)");
	            pst2.setInt(1,user.getUser_id());
	            String hashPassword=hashPassword(user.getPassword());
	            pst2.setString(2, hashPassword);
	            pst2.executeUpdate();
	            rs=true;
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean allMailInsert(UserDetailsBean user)
	{
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst3 = con.prepareStatement("INSERT INTO all_mail(user_id,user_email,is_primary) values(?,?,true)");
	            pst3.setInt(1,user.getUser_id());
	            pst3.setString(2,user.getUsermail());
	            pst3.executeUpdate();
	            rs=true;
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean allPhoneInsert(UserDetailsBean user)
	{
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst3 = con.prepareStatement("INSERT INTO all_phone(user_id,phone,is_primary) values(?,?,true)");
	            pst3.setInt(1,user.getUser_id());
	            pst3.setString(2,user.getPhonenumber());
	            pst3.executeUpdate();
	            rs=true;
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public int validateLogin(String usermail,String password)
	{
		int user_id=-1;
	     try {
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	    	 PreparedStatement pst = con.prepareStatement("SELECT c.user_id FROM credentials c INNER JOIN all_mail a ON c.user_id = a.user_id WHERE a.user_email = ? AND c.password = ? ;");
	            pst.setString(1, usermail);
	            pst.setString(2, hashPassword(password));
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	            	user_id=rs.getInt("user_id");
	            	return user_id;
	            } 
	            return -1;

	     }catch (Exception e) {
	            e.printStackTrace();
	        }
	     return -1;
	}
	public  String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	public boolean updateUserDetails(UserDetailsBean user) {
		boolean rs=false;
		String updateQuery = "UPDATE userDetails SET username = ?, gender = ?, birthday = ? WHERE user_id = ?";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(updateQuery);
		        ps.setString(1, user.getUsername());
		        ps.setString(2, user.getGender());
		        ps.setString(3, user.getBirthday());
		        ps.setInt(4, user.getUser_id());
               
		        ps.executeUpdate();

		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
	}
	public boolean addAltMail(UserDetailsBean user) {
		boolean rs=false;
		String query="Insert into all_mail(user_id,user_email,is_primary) values(?,?,false)";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setInt(1, user.getUser_id());
		        ps.setString(2, user.getAltmail());
               
		        ps.executeUpdate();

		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean addAltPhone(UserDetailsBean user) {
		boolean rs=false;
		String query="Insert into all_phone(user_id,phone,is_primary) values(?,?,false)";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setInt(1, user.getUser_id());
		        ps.setString(2, user.getAltphone());
               
		        ps.executeUpdate();

		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean updatePrimaryMail(UserDetailsBean user) {
		boolean rs=false;
		String query="update userDetails set usermail=? where user_id=? ";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setString(1,user.getUsermail());
                ps.setInt(2,user.getUser_id());
		        ps.executeUpdate();
		    
            PreparedStatement ps1=con.prepareStatement("update all_mail set user_email=? where user_id=? && is_primary=true;");
            ps1.setString(1, user.getUsermail());
            ps1.setInt(2, user.getUser_id());
            ps1.executeUpdate();
		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean updatePrimaryPhone(UserDetailsBean user) {
		boolean rs=false;
		String query="update userDetails set phonenumber=? where user_id=?;";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setString(1,user.getPhonenumber());
                ps.setInt(2,user.getUser_id());
		        ps.executeUpdate();
            PreparedStatement ps2=con.prepareStatement("update all_phone set phone=? where user_id=? && is_primary=true;");
            ps2.setString(1, user.getPhonenumber());
            ps2.setInt(2, user.getUser_id());
            ps2.executeUpdate();
		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean changePassword(UserDetailsBean user) {
		String query="update credentials set password=? where user_id=?;";
		boolean rs=false;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
			 String hash=hashPassword(user.getPassword());
			 ps.setString(1, hash);
			 ps.setInt(2, user.getUser_id());
			 ps.executeUpdate();
			 rs=true;
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
		
	}


}
