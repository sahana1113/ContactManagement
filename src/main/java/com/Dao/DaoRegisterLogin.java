package com.Dao;
import org.mindrot.jbcrypt.BCrypt;

import com.Bean.BeanCategory;
import com.Bean.BeanContactDetails;
import com.Bean.BeanUserDetails;
import com.Query.Column;
import com.Query.Enum.*;
import com.Query.QueryLayer;
import com.example.HikariCPDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class DaoRegisterLogin{
	 int user_id;
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
			     Object[]obj=new Object[] {user.getUsername(),user.getUsermail(),user.getGender(),user.getPhonenumber(),user.getBirthday()};
			     Column[]col=new Column[] {UserDetails.username,UserDetails.usermail,UserDetails.gender,UserDetails.phonenumber,UserDetails.birthday};
	            int key=QueryLayer.buildInsertQuery(Tables.userDetails,obj,col);
	            System.out.print(key);
	            if(key!=-1)
	            {
	            	user.setUser_id(key);
	            }
	            if(key!=0)
	            {
	            	rs=true;
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
			String hashPassword=hashPassword(user.getPassword());
			Object[] object=new Object[] {user.getUser_id(),hashPassword,true};
			int key=QueryLayer.buildInsertQuery(Tables.credentials,object);
			if(key!=0)
			{
				rs=true;
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean allMailInsert(BeanUserDetails user) throws SQLException
	{
		boolean rs=false;
		try {
			Object[] object=new Object[] {user.getUser_id(),user.getUsermail(),true};
			int key=QueryLayer.buildInsertQuery(Tables.all_mail,object);
			if(key!=0)
			{
				rs=true;
			}
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
			Object[] object=new Object[] {user.getUser_id(),user.getPhonenumber(),true};
			int key=QueryLayer.buildInsertQuery(Tables.all_phone,object);
			if(key!=0)
			{
				rs=true;
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public int validateLogin(String usermail,String password)
	{
		int user_id=-1;
	     try (Connection con = HikariCPDataSource.getConnection()){
	    	 PreparedStatement pst = con.prepareStatement("SELECT c.user_id,c.password,c.flag FROM credentials c INNER JOIN all_mail a ON c.user_id = a.user_id WHERE a.user_email = ? ;");
	            pst.setString(1, usermail);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	                String storedHash = rs.getString("password");
	                 if (rs.getInt("flag")==1) {
	                	 if (checkSHA512Hash(password, storedHash)) {
	                	 String bcryptHash = hashPassword(password);
	                     updateUserHashInDatabase(rs.getInt("user_id"), bcryptHash); // Update the hash in the database
	                     user_id = rs.getInt("user_id");
	                     System.out.println("Password migrated to bcrypt.");
	                     return user_id;
	                	 }
	                 }
	                 if (rs.getInt("flag")==0) {
	                	 if (BCrypt.checkpw(password, storedHash)) {
	                         user_id = rs.getInt("user_id");
	                         return user_id;  
	                     }
	                 }
	            }
	            return -1;

	     }catch (Exception e) {
	            e.printStackTrace();
	        }
	     return -1;
	}
	public void updateUserHashInDatabase(int userId, String bcryptHash) {
	
		try {
			Column[] condition=new Column[] {Credentials.user_id};
			Column[] setColumns=new Column[] {Credentials.password,Credentials.flag};
			Object[]obj=new Object[] {bcryptHash,true,userId};
			int key=QueryLayer.buildUpdateQuery(Tables.credentials,condition,null,obj,setColumns);
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public  String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
    }
	public boolean updateUserDetails(BeanUserDetails user) {
		boolean rs=false;
		try {
			Column[] condition=new Column[] {UserDetails.user_id};
			Column[] setColumns=new Column[] {UserDetails.username,UserDetails.gender,UserDetails.birthday};
			Object[]obj=new Object[] {user.getUsername(),user.getGender(),user.getBirthday(),user.getUser_id()};
			int key=QueryLayer.buildUpdateQuery(Tables.userDetails,condition,null,obj,setColumns);
			if(key!=0)
			{
				rs=true;
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
	}
	public boolean addAltMail(BeanUserDetails user) {
		boolean rs=false;
		Object[]obj=new Object[] {user.getUser_id(),user.getAltmail(),false };
		try {
			int k=QueryLayer.buildInsertQuery(Tables.all_mail,obj);
			if(k!=0)
			{
				rs=true;
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean addAltPhone(BeanUserDetails user) {
		boolean rs=false;
		Object[]obj=new Object[] {user.getUser_id(),user.getAltphone(),false };
		try {
			int k=QueryLayer.buildInsertQuery(Tables.all_phone,obj);
			if(k!=0)
			{
				rs=true;
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean updatePrimaryMail(BeanUserDetails user) {
		boolean rs=false;
		try {
			Column[]condition=new Column[] {UserDetails.user_id};
			Column[]column=new Column[] {UserDetails.usermail};
			Object[]obj=new Object[] {user.getUsermail(),user.getUser_id()};
			
			int k=QueryLayer.buildUpdateQuery(
					Tables.userDetails,
					condition,
					null, 
					obj,
					column);
			
			condition=new Column[] {AllMail.user_id,AllMail.is_primary};
			String[] logic=new String[] {"AND"};
			column=new Column[] {AllMail.is_primary};
			obj=new Object[] {false,user.getUser_id(),true};
			
			int k2=QueryLayer.buildUpdateQuery(
					Tables.all_mail, 
					condition,
					logic,
					obj,
					column);
			
			condition=new Column[] {AllMail.user_email,AllMail.user_id};
			column=new Column[] {AllMail.is_primary};
			obj=new Object[] {true,user.getUsermail(),user.getUser_id()};
			
			int k1=QueryLayer.buildUpdateQuery(
					Tables.all_mail,
					condition,
					logic,
					obj,
					column);
			
			if(k1!=0 && k!=0 && k2!=0)
			{
				rs=true;
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean updatePrimaryPhone(BeanUserDetails user) {
		boolean rs=false;
		try {
	        Column[] userDetailsCondition = new Column[] { UserDetails.user_id };
	        Column[] userDetailsColumns = new Column[] { UserDetails.phonenumber };
	        Object[] userDetailsValues = new Object[] { user.getPhonenumber(), user.getUser_id() };

	        int userDetailsUpdateCount = QueryLayer.buildUpdateQuery(
	            Tables.userDetails,
	            userDetailsCondition,
	            null,
	            userDetailsValues,
	            userDetailsColumns
	        );
	        Column[] allPhoneCondition = new Column[] { AllPhone.user_id, AllPhone.is_primary };
	        String[] logic = new String[] { "AND" };
	        Column[] allPhoneColumns = new Column[] { AllPhone.is_primary };
	        Object[] allPhoneValues = new Object[] { false, user.getUser_id(), true };

	        int allPhoneUpdateCount = QueryLayer.buildUpdateQuery(
	            Tables.all_phone,
	            allPhoneCondition,
	            logic,
	            allPhoneValues,
	            allPhoneColumns
	        );
	        Column[] primaryPhoneCondition = new Column[] { AllPhone.phone, AllPhone.user_id };
	        Object[] primaryPhoneValues = new Object[] { true,user.getPhonenumber(), user.getUser_id() };
	        Column[] primaryPhoneColumns = new Column[] { AllPhone.is_primary };

	        int primaryPhoneUpdateCount = QueryLayer.buildUpdateQuery(
	            Tables.all_phone,
	            primaryPhoneCondition,
	            null,
	            primaryPhoneValues,
	            primaryPhoneColumns
	        );

	        rs = (userDetailsUpdateCount > 0 && allPhoneUpdateCount > 0 && primaryPhoneUpdateCount > 0);
		}catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
	}
	public boolean checkSHA512Hash(String password, String storedHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            String hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
            return hashedPassword.equals(storedHash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false; 
    }
	public boolean changePassword(BeanUserDetails user) {
		boolean rs = false;
		try {
		    String hash = hashPassword(user.getPassword());
		    Column[] condition = new Column[] { Credentials.user_id };
		    Object[] values = new Object[] { hash, user.getUser_id() };
		    int updateCount = QueryLayer.buildUpdateQuery(
		        Tables.credentials,
		        condition,
		        null,  
		        values,
		        new Column[] { Credentials.password }
		    );

		    rs = (updateCount > 0);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return rs;
		
	}
	public boolean updateContactDetails(BeanContactDetails user) {
		boolean rs = false;
		try {
		    Column[] contactDetailsCondition = new Column[] { ContactDetails.contact_id };
		    Object[] contactDetailsValues = new Object[] {
		        user.getName(),
		        user.getGender(),
		        user.getBirthday(),
		        user.getMail(),
		        user.getPhonenumber(),
		        user.getContact_id()
		    };
		    int contactDetailsUpdateCount = QueryLayer.buildUpdateQuery(
		        Tables.contactDetails,
		        contactDetailsCondition,
		        null,
		        contactDetailsValues,
		        new Column[] {
		            ContactDetails.name,
		            ContactDetails.gender,
		            ContactDetails.birthday,
		            ContactDetails.mail,
		            ContactDetails.phonenumber
		        }
		    );
		    Column[] categoryCondition = new Column[] { CategoryUsers.contact_id };
		    Object[] categoryValues = new Object[] { user.getContact_id() };
		    int categoryDeleteCount = QueryLayer.buildDeleteQuery(Tables.category_users,categoryCondition,null,categoryValues);
		    List<String> list = user.getCategory();
		    if (list != null) {
		        insertCategory(user);
		    }
		    rs = (contactDetailsUpdateCount > 0 || categoryDeleteCount > 0);
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
	}
	public boolean deleteContactById(int id) throws SQLException
    {
		boolean rs = false;
		try {
		    Column[] categoryCondition = new Column[] { CategoryUsers.contact_id };
		    Object[] categoryValues = new Object[] { id };
		    int categoryDeleteCount = QueryLayer.buildDeleteQuery(
		        Tables.category_users,
		        categoryCondition,
		        null,
		        categoryValues
		    );
		    Column[] contactDetailsCondition = new Column[] { ContactDetails.contact_id };
		    Object[] contactDetailsValues = new Object[] { id };
		    int contactDetailsDeleteCount = QueryLayer.buildDeleteQuery(
		        Tables.contactDetails,
		        contactDetailsCondition,
		        null,
		        contactDetailsValues
		    );
		    rs = (categoryDeleteCount > 0 || contactDetailsDeleteCount > 0);
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		return rs;
         
    }
	public void insertCategory(BeanContactDetails user) throws SQLException {
		List<String> category=user.getCategory();
      for(String s:category) {
		List<BeanCategory>list=QueryLayer.buildSelectQuery(
				Tables.categories,
				new Column[] {Categories.category_id},
				new Column[] {Categories.category_name,Categories.user_id},
				new String[] {"AND"},
				BeanCategory.class,
				new Object[] {s,user_id});
		int c_id=list.get(0).getCategory_id();
		int k=QueryLayer.buildInsertQuery(
				Tables.category_users, 
				new Object[] {c_id,user.getContact_id()},
				new Column[] {CategoryUsers.category_id,CategoryUsers.contact_id});
		}
		
	}
	public void deleteAltMail(BeanUserDetails user) {
		try {
			Column[] condition=new Column[] {AllMail.user_id,AllMail.user_email};
			String[]logics= {"AND"};
			Object[]obj=new Object[] {user.getUser_id(),user.getAltmail()};
			int k=QueryLayer.buildDeleteQuery(Tables.all_mail, condition, logics,obj);
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	public void deleteAltPhone(BeanUserDetails user) {
		try  {
		    Column[] condition = new Column[] { AllPhone.phone, AllPhone.user_id };
		    Object[] values = new Object[] { user.getAltphone(), user.getUser_id() };
		    int deleteCount = QueryLayer.buildDeleteQuery(
		        Tables.all_phone,
		        condition,
		        null, 
		        values
		    );
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
	public boolean deleteContactFromCategory(int contactId,int c_id) {
		boolean rs=false;
		try{
			Column[] condition = new Column[] { CategoryUsers.category_id, CategoryUsers.contact_id };
		    Object[] values = new Object[] { c_id, contactId };
		    int deleteCount = QueryLayer.buildDeleteQuery(
		        Tables.category_users,
		        condition,
		        null,  
		        values
		    );
		    rs = (deleteCount > 0);
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
		
	}
	public boolean insertCategoryById(int contactId,int c_id)
	{
		boolean rs=false;
		try {
			 Column[] insertColumns = new Column[] { CategoryUsers.category_id, CategoryUsers.contact_id };
			    Object[] insertValues = new Object[] { c_id, contactId };

			    int insertCount = QueryLayer.buildInsertQuery(
			        Tables.category_users,
			        insertValues,
			        insertColumns
			    );
			    rs = (insertCount > 0);
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
	}
	public boolean deleteCategory(int c_id) {
		boolean rs=false;
		try {
			Column[] categoryUsersCondition = new Column[] { CategoryUsers.category_id };
		    Object[] categoryUsersValues = new Object[] { c_id };

		    int categoryUsersDeleteCount = QueryLayer.buildDeleteQuery(
		        Tables.category_users,
		        categoryUsersCondition,
		        null,  
		        categoryUsersValues
		    );
		    Column[] categoriesCondition = new Column[] { Categories.category_id };
		    Object[] categoriesValues = new Object[] { c_id };

		    int categoriesDeleteCount = QueryLayer.buildDeleteQuery(
		        Tables.categories,
		        categoriesCondition,
		        null, 
		        categoriesValues
		    );
		    rs = (categoryUsersDeleteCount > 0 || categoriesDeleteCount > 0);
			}catch (Exception e) {
	            e.printStackTrace();
	        }
		return rs;
		
	}
	public int insertCategoryByName(String categoryName) {
		try (Connection con = HikariCPDataSource.getConnection()){
			Object[] values = new Object[] { categoryName, user_id };
		    Column[] columns = new Column[] { Categories.category_name, Categories.user_id };
		    return QueryLayer.buildInsertQuery(Tables.categories, values, columns);
	     }
		catch (Exception e) {
          e.printStackTrace();
        
      }
		return 0;
	}
	public boolean defaultGroup(BeanUserDetails user) {
		boolean rs=false;
		String query="INSERT INTO categories (category_name, user_id) VALUES('Family', ?),('Work', ?),('Friends', ?),('Favourites', ?);";
		try(Connection con = HikariCPDataSource.getConnection()) {
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
