package com.Dao;

import org.mindrot.jbcrypt.BCrypt;
import com.Bean.*;
import com.Query.*;
import com.Query.Enum.*;
import com.example.HikariCPDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DaoRegisterLogin {
	int user_id;

	public DaoRegisterLogin() {
	}

	public DaoRegisterLogin(int user_id) {
		this.user_id = user_id;
	}

	public boolean UserDetailsRegister(BeanUserDetails user) {
		boolean rs = false;
		try {
			Column[] col = new Column[] { UserDetails.username, UserDetails.usermail, UserDetails.gender,
					UserDetails.phonenumber, UserDetails.birthday };
			int key = QueryLayer.buildInsertQuery(Tables.USER_DETAILS, user, col);
			if (key != -1) {
				user.setUser_id(key);
			}
			if (key != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean credentialsInsert(BeanUserDetails user) {
		boolean rs = false;
		try {
			String hashPassword = hashPassword(user.getPassword());
			user.setPassword(hashPassword);
			user.setFlag(false);
			int key = QueryLayer.buildInsertQuery(Tables.CREDENTIALS, user);
			if (key != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean allMailInsert(BeanMail user) throws SQLException {
		boolean rs = false;
		try {
			user.setIs_primary(true);
			int key = QueryLayer.buildInsertQuery(Tables.ALL_MAIL,user);
			if (key != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean allPhoneInsert(BeanPhone user) {
		boolean rs = false;
		try {
			user.setIs_primary(true);
			int key = QueryLayer.buildInsertQuery(Tables.ALL_PHONE, user);
			if (key != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int validateLogin(String usermail, String password) throws Exception {
		int user_id = -1;
		BeanMail user=new BeanMail();
		user.setAltMail(usermail);
		Condition condition=new Condition(AllMail.altMail,"=",true);
		List<BeanUserDetails> userList=QueryLayer.buildSelectQuery(
				new Tables[] {Tables.CREDENTIALS,Tables.ALL_MAIL},
				new Column[] {Credentials.user_id,Credentials.password,Credentials.flag},
				condition,
				BeanUserDetails.class,
				user,
				new Column[][] {{Credentials.user_id,AllMail.user_id}},
				"INNER JOIN");
			if (!userList.isEmpty()) {
				String storedHash = userList.get(0).getPassword();
				if (userList.get(0).isFlag()) {
					if (checkSHA512Hash(password, storedHash)) {
						String bcryptHash = hashPassword(password);
						BeanUserDetails obj=new BeanUserDetails();
						obj.setPassword(bcryptHash);
						obj.setUser_id(user_id);
						updateUserHashInDatabase(obj); 
						user_id = userList.get(0).getUser_id();
						System.out.println("Password migrated to bcrypt.");
						return user_id;
					}
				}
				if (!userList.get(0).isFlag()) {
					if (BCrypt.checkpw(password, storedHash)) {
						user_id = userList.get(0).getUser_id();
						return user_id;
					}
				}
			}
			return -1;
	}

	public void updateUserHashInDatabase(BeanUserDetails user) {

		try {
			Condition condition = new Condition( Credentials.user_id,"=",false);
			Column[] setColumns = new Column[] { Credentials.password, Credentials.flag };
			user.setFlag(false);
			int key = QueryLayer.buildUpdateQuery(Tables.CREDENTIALS, condition, null, user, setColumns);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean updateUserDetails(BeanUserDetails user) {
		boolean rs = false;
		try {
			Condition condition = new Condition ( UserDetails.user_id,"=",false );
			Column[] setColumns = new Column[] { UserDetails.username, UserDetails.gender, UserDetails.birthday };
			int key = QueryLayer.buildUpdateQuery(Tables.USER_DETAILS, condition, null, user, setColumns);
			if (key != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean addAltMail(BeanMail mail) {
		boolean rs = false;
		mail.setIs_primary(false);
		try {
			int k = QueryLayer.buildInsertQuery(Tables.ALL_MAIL, mail);
			if (k != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean addAltPhone(BeanPhone phone) {
		boolean rs = false;
		phone.setIs_primary(false);
		try {
			int k = QueryLayer.buildInsertQuery(Tables.ALL_PHONE, phone);
			if (k != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean updatePrimaryMail(BeanUserDetails user) throws Exception {
		boolean rs = false;
		DaoUserContact obj1=new DaoUserContact(user.getUser_id());
		String s=obj1.getUsermail();
		try {
			Condition condition = new Condition ( UserDetails.user_id,"=",false );
			Column[] column = new Column[] { UserDetails.usermail };

			int k = QueryLayer.buildUpdateQuery(Tables.USER_DETAILS, condition, null, user, column);
            
			user.setIs_primary(true);
			Condition con1=new Condition(AllMail.altMail,"=",false);
			Condition con2=new Condition(AllMail.user_id,"=",false);
			Condition and=new Condition("AND").addSubCondition(con1).addSubCondition(con2);

			int k1 = QueryLayer.buildUpdateQuery(Tables.ALL_MAIL, and, null,user, column);

            user.setUsermail(s);
            user.setIs_primary(false);
			column = new Column[] { AllMail.is_primary };
			
			int k2 = QueryLayer.buildUpdateQuery(Tables.ALL_MAIL, and, null, user, column);
             

			if (k1 != 0 && k != 0 && k2 != 0) {
				rs = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean updatePrimaryPhone(BeanUserDetails user) throws Exception {
		boolean rs = false;
		DaoUserContact obj1=new DaoUserContact(user.getUser_id());
		String s=obj1.getUserphone();
		try {
			Condition userDetailsCondition = new Condition( UserDetails.user_id,"=",false );
			Column[] userDetailsColumns = new Column[] { UserDetails.phonenumber };

			int userDetailsUpdateCount = QueryLayer.buildUpdateQuery(Tables.USER_DETAILS, userDetailsCondition, null,
					user, userDetailsColumns);
			
			user.setIs_primary(true);
			Condition con1=new Condition(AllPhone.altPhone,"=",false);
			Condition con2=new Condition(AllPhone.user_id,"=",false);
			Condition and=new Condition("AND").addSubCondition(con1).addSubCondition(con2);
			Column[] allPhoneColumns = new Column[] { AllPhone.is_primary };

			int allPhoneUpdateCount = QueryLayer.buildUpdateQuery(Tables.ALL_PHONE, and,null,user, allPhoneColumns);
			
			user.setPhonenumber(s);
			user.setIs_primary(false);
			Column[] primaryPhoneCondition = new Column[] { AllPhone.user_id, AllPhone.altPhone };
			Column[] primaryPhoneColumns = new Column[] { AllPhone.is_primary };

			int primaryPhoneUpdateCount = QueryLayer.buildUpdateQuery(Tables.ALL_PHONE, and, null,
					user, primaryPhoneColumns);

			rs = (userDetailsUpdateCount > 0 && allPhoneUpdateCount > 0 && primaryPhoneUpdateCount > 0);
		} catch (Exception e) {
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
			user.setPassword(hash);
			Condition condition = new Condition(Credentials.user_id ,"=",false);
			int updateCount = QueryLayer.buildUpdateQuery(Tables.CREDENTIALS, condition, null, user,
					new Column[] { Credentials.password });

			rs = (updateCount > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public boolean updateContactDetails(BeanContactDetails user) {
		boolean rs = false;
		try {
			Condition contactDetailsCondition = new Condition (ContactDetails.contact_id,"=",false );
			int contactDetailsUpdateCount = QueryLayer.buildUpdateQuery(
					Tables.CONTACT_DETAILS, 
					contactDetailsCondition,
					null,
					user, 
					new Column[] { ContactDetails.name, ContactDetails.gender,ContactDetails.birthday, ContactDetails.mail, ContactDetails.phonenumber });
			Condition cond=new Condition(CategoryUsers.contact_id,"=",false);
			int categoryDeleteCount = QueryLayer.buildDeleteQuery(Tables.CATEGORY_USERS, cond, null, user);
			List<BeanCategory> list = user.getCategory();
			if (list != null) {
				insertCategory(user);
			}
			rs = (contactDetailsUpdateCount > 0 || categoryDeleteCount > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean deleteContactById(int id) throws SQLException {
		boolean rs = false;
		try {
			Condition categoryCondition = new Condition(CategoryUsers.contact_id,"=",false);
			BeanContactDetails user=new BeanContactDetails();
			user.setContact_id(id);
			int categoryDeleteCount = QueryLayer.buildDeleteQuery(Tables.CATEGORY_USERS, categoryCondition, null,
					user);
			Condition contactDetailsCondition = new Condition (ContactDetails.contact_id,"=",false);
			int contactDetailsDeleteCount = QueryLayer.buildDeleteQuery(Tables.CONTACT_DETAILS, contactDetailsCondition,
					null, user);
			rs = (categoryDeleteCount > 0 || contactDetailsDeleteCount > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;

	}

	public void insertCategory(BeanContactDetails user)
			throws Exception {
		List<BeanCategory> category = user.getCategory();
		for (BeanCategory s : category) {
			user.setCategory_name(s.getCategory_name());
			user.setUser_id(user_id);
			Condition con1=new Condition(Categories.category_name,"=",false);
			Condition con2=new Condition(Categories.user_id,"=",false);
			Condition and=new Condition("AND").addSubCondition(con1).addSubCondition(con2);
			List<BeanCategory> list = QueryLayer.buildSelectQuery(new Tables[] {Tables.CATEGORIES},
					new Column[] { Categories.category_id },
					and,
					BeanCategory.class, user,null,null);
			int c_id = list.get(0).getCategory_id();
			BeanCategory obj=new BeanCategory();
			obj.setCategory_id(c_id);
			obj.setContact_id(user.getContact_id());
			int k = QueryLayer.buildInsertQuery(Tables.CATEGORY_USERS, obj,new Column[] { CategoryUsers.category_id, CategoryUsers.contact_id });
		}
	}

	public void deleteAltMail(BeanMail mail) {
		try {
			Condition condition1 = new Condition (AllMail.altMail,"=",false);
			Condition condition2 = new Condition (AllMail.user_id,"=",false );
			Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition2);
			int k = QueryLayer.buildDeleteQuery(Tables.ALL_MAIL, and, null, mail);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteAltPhone(BeanPhone phone) {
		try {
			Condition condition1 = new Condition (AllPhone.altPhone,"=",false);
			Condition condition2 = new Condition (AllPhone.user_id,"=",false );
			Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition2);
			int deleteCount = QueryLayer.buildDeleteQuery(Tables.ALL_PHONE, and, null, phone);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean deleteContactFromCategory(int contactId, int c_id) {
		boolean rs = false;
		try {
			Condition condition1 = new Condition (CategoryUsers.category_id,"=",false);
			Condition condition2 = new Condition (CategoryUsers.contact_id,"=",false );
			Condition and=new Condition("AND").addSubCondition(condition1).addSubCondition(condition2);
			BeanCategory obj=new BeanCategory();
			obj.setCategory_id(c_id);
			obj.setContact_id(contactId);
			int deleteCount = QueryLayer.buildDeleteQuery(Tables.CATEGORY_USERS, and, null, obj);
			rs = (deleteCount > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public boolean insertCategoryById(int contactId, int c_id) {
		boolean rs = false;
		try {
			Column[] insertColumns = new Column[] { CategoryUsers.category_id, CategoryUsers.contact_id };
			BeanCategory obj=new BeanCategory();
			obj.setCategory_id(c_id);
			obj.setContact_id(contactId);
			int insertCount = QueryLayer.buildInsertQuery(Tables.CATEGORY_USERS, obj, insertColumns);
			if (insertCount != 0)
			{
				rs=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean deleteCategory(int c_id) {
		boolean rs = false;
		try {
			Condition categoryUsersCondition = new Condition(CategoryUsers.category_id,"=",false);
			BeanCategory obj=new BeanCategory();
			obj.setCategory_id(c_id);
			int categoryUsersDeleteCount = QueryLayer.buildDeleteQuery(Tables.CATEGORY_USERS, categoryUsersCondition,
					null, obj);
			Condition categoriesCondition = new Condition (Categories.category_id,"=",false);

			int categoriesDeleteCount = QueryLayer.buildDeleteQuery(Tables.CATEGORIES, categoriesCondition, null,
					obj);
			rs = (categoryUsersDeleteCount > 0 || categoriesDeleteCount > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public int insertCategoryByName(BeanCategory category)
			throws SQLException, NoSuchFieldException, IllegalAccessException {
           category.setUser_id(user_id); 
		Column[] columns = new Column[] { Categories.category_name, Categories.user_id };
		return QueryLayer.buildInsertQuery(Tables.CATEGORIES, category, columns);

	}

	public boolean defaultGroup(BeanUserDetails user) throws SQLException {
		boolean rs = false;

		String query = "INSERT INTO categories (category_name, user_id) VALUES('Family', ?),('Work', ?),('Friends', ?),('Favourites', ?);";
		try (Connection con = HikariCPDataSource.getConnection()) {
			PreparedStatement ps = con.prepareStatement(query);

			ps.setInt(1, user.getUser_id());
			ps.setInt(2, user.getUser_id());
			ps.setInt(3, user.getUser_id());
			ps.setInt(4, user.getUser_id());

			ps.executeUpdate();

			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

}
