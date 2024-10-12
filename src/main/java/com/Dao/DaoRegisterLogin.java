package com.Dao;
import org.mindrot.jbcrypt.BCrypt;

import com.Bean.BeanContactDetails;
import com.Bean.BeanUserDetails;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class DaoRegisterLogin{
	static int user_id;
	public DaoRegisterLogin()
	{
	}
	public DaoRegisterLogin(int user_id)
	{
		this.user_id=user_id;
	}
	public boolean UserDetailsRegister(BeanUserDetails user) {
		boolean rs=false;
		try {
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
	public boolean credentialsInsert(BeanUserDetails user)
	{
		boolean rs=false;
		try {
	          //  Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst2 = con.prepareStatement("INSERT INTO credentials(user_id,password,flag) values(?,?,0)");
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
	public boolean allMailInsert(BeanUserDetails user)
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
	public boolean allPhoneInsert(BeanUserDetails user)
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
	    	 PreparedStatement pst = con.prepareStatement("SELECT c.user_id,c.password,c.flag FROM credentials c INNER JOIN all_mail a ON c.user_id = a.user_id WHERE a.user_email = ? ;");
	            pst.setString(1, usermail);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	            	 String storedHash = rs.getString("password");
	                 if (rs.getInt("flag")==1) {
	                	 String bcryptHash = hashPassword(password);
	                     updateUserHashInDatabase(rs.getInt("user_id"), bcryptHash); // Update the hash in the database
	                     user_id = rs.getInt("user_id");
	                     System.out.println("Password migrated to bcrypt.");
	                     return user_id;
	                 }
	                 if (rs.getInt("flag")==0) {
	                     user_id = rs.getInt("user_id");
	                     return user_id;
	                 }
	            }
	            return -1;

	     }catch (Exception e) {
	            e.printStackTrace();
	        }
	     return -1;
	}
	private boolean checkSHA512Hash(String password,String sha512Hash) {
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] hash = md.digest(password.getBytes());
	        StringBuilder hexString = new StringBuilder();

	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString().equals(sha512Hash);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	}
	public void updateUserHashInDatabase(int userId, String bcryptHash) {
	    String sql = "UPDATE credentials SET password = ?,flag=0 WHERE user_id = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, bcryptHash);
	        pstmt.setInt(2, userId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public  String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
    }
	public boolean updateUserDetails(BeanUserDetails user) {
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
	public boolean addAltMail(BeanUserDetails user) {
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
	public boolean addAltPhone(BeanUserDetails user) {
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
	public boolean updatePrimaryMail(BeanUserDetails user) {
		boolean rs=false;
		String query="update userDetails set usermail=? where user_id=? ";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setString(1,user.getUsermail());
                ps.setInt(2,user.getUser_id());
		        ps.executeUpdate();
		        PreparedStatement ps1=con.prepareStatement("update all_mail set is_primary=false where user_id=? && is_primary=true;");
	           
	            ps1.setInt(1, user.getUser_id());
	            ps1.executeUpdate();
            PreparedStatement ps2=con.prepareStatement("update all_mail set is_primary=true where user_email=? && user_id=?");
            ps2.setString(1, user.getUsermail());
            ps2.setInt(2, user.getUser_id());
            ps2.executeUpdate();
		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean updatePrimaryPhone(BeanUserDetails user) {
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
	public boolean changePassword(BeanUserDetails user) {
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
	public boolean updateContactDetails(BeanContactDetails user) {
		boolean rs=false;
		String updateQuery = "UPDATE contactDetails SET name = ?, gender = ?, birthday = ?,mail=?,phonenumber=? WHERE contact_id = ?";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(updateQuery);
		        ps.setString(1, user.getContactname());
		        ps.setString(2, user.getGender());
		        ps.setString(3, user.getBirthday());
		        ps.setString(4, user.getContactmail());
		        ps.setString(5, user.getPhonenumber());
		        ps.setInt(6, user.getContact_id());
               
		        ps.executeUpdate();
             List<String>list =user.getCategory();
			 PreparedStatement ps1 = con.prepareStatement("delete from category_users where contact_id=?;");
			 ps1.setInt(1, user.getContact_id());
			 ps1.executeUpdate();
			 if(list!=null)
			 insertCategory(user);

		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
	}
	public boolean deleteContactById(int id) throws SQLException
    {
		boolean rs=false;
		try {
    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
    	 PreparedStatement pst1 =con.prepareStatement("delete from category_users where contact_id=?;");
    	 pst1.setInt(1, id);
    	 pst1.executeUpdate();
         PreparedStatement pst = con.prepareStatement("delete from contactDetails where contact_id=?;");
         pst.setInt(1, id);
         pst.executeUpdate();
         rs=true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
         
    }
	public static void insertCategory(BeanContactDetails user) throws SQLException {
		try {
			 
	     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
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
		
	}
	public void deleteAltMail(BeanUserDetails user) {
		try {
    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
         PreparedStatement pst = con.prepareStatement("delete from all_mail where user_email=? && user_id=?;");
         pst.setString(1, user.getAltmail());
         pst.setInt(2, user.getUser_id());
         pst.executeUpdate();
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	public void deleteAltPhone(BeanUserDetails user) {
		try {
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement pst = con.prepareStatement("delete from all_phone where phone=? && user_id=?;");
	         pst.setString(1, user.getAltphone());
	         pst.setInt(2, user.getUser_id());
	         pst.executeUpdate();
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	public boolean deleteContactFromCategory(int contactId,int c_id) {
		boolean rs=false;
		try {
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement pst = con.prepareStatement("delete from category_users where category_id=? && contact_id=?;");
	         pst.setInt(1, c_id);
	         pst.setInt(2, contactId);
	         pst.executeUpdate();
	         rs=true;
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
		
	}
	public boolean insertCategoryById(int contactId,int c_id)
	{
		boolean rs=false;
		try {
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement pst = con.prepareStatement("insert into category_users(category_id,contact_id) values(? ,?);");
	         pst.setInt(1, c_id);
	         pst.setInt(2, contactId);
	         pst.executeUpdate();
	         rs=true;
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
	}
	public boolean deleteCategory(int c_id) {
		boolean rs=false;
		try {
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement pst = con.prepareStatement("delete from category_users where category_id=?;");
	         pst.setInt(1, c_id);
	         pst.executeUpdate();
	         PreparedStatement pst1 = con.prepareStatement("delete from categories where category_id=?;");
	         pst1.setInt(1, c_id);
	         pst1.executeUpdate();
	         rs=true;
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
		
	}
	public int insertCategoryByName(String categoryName) {
		try {
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	            PreparedStatement pst = con.prepareStatement("INSERT INTO categories (category_name,user_id) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS);
	            pst.setString(1,categoryName);
	            pst.setInt(2,user_id);
	            pst.executeUpdate();
	            ResultSet key=pst.getGeneratedKeys();
	            if(key.next())
	            {
	            	return key.getInt(1);
	            }
	
	     }
		catch (Exception e) {
          e.printStackTrace();
        
      }
		return 0;
	}
	public boolean defaultGroup(BeanUserDetails user) {
		boolean rs=false;
		String query="INSERT INTO categories (category_name, user_id) VALUES('Family', ?),('Work', ?),('Friends', ?),('Favourites', ?);";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
			 PreparedStatement ps = con.prepareStatement(query);
		        
		        ps.setInt(1, user.getUser_id());
		        ps.setInt(2, user.getUser_id());
		        ps.setInt(3, user.getUser_id());
		        ps.setInt(4, user.getUser_id());
               
		        ps.executeUpdate();

		        rs = true;
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
		
	}
	


}
