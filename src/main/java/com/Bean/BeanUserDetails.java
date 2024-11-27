package com.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents user details in the application. This class encapsulates
 * information about a user, including personal details, contact information,
 * and lists of alternative contacts.
 * 
 * <p>
 * This class provides methods to get and set user attributes.
 * </p>
 * 
 * @author Sahana
 * @version 1.0
 */
public class BeanUserDetails implements Bean {

	private String username;
	private String usermail;
	private String gender;
	private String phonenumber;
	private String birthday;
	private String password;
	private boolean flag;
	private boolean is_primary;
	private int user_id;
	private List<String> allMail = new ArrayList<>();
	private List<String> allPhone = new ArrayList<>();

	/**
	 * Default constructor for BeanUserDetails.
	 */
	public BeanUserDetails() {
	}

	/**
	 * Constructs a new BeanUserDetails with the specified username and phonenumber
	 * number.
	 *
	 * @param username    The username of the user.
	 * @param phonenumber The phonenumber number of the user.
	 */
	public BeanUserDetails(String username, String phonenumber) {
		this.username = username;
		this.phonenumber = phonenumber;
	}

	/**
	 * Retrieves the user ID.
	 *
	 * @return The user ID.
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user ID.
	 *
	 * @param user_id The new user ID.
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * Retrieves the password of the user.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password for the user.
	 *
	 * @param password The new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isIs_primary() {
		return is_primary;
	}

	public void setIs_primary(boolean is_primary) {
		this.is_primary = is_primary;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * Retrieves the username.
	 *
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username The new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the user's email.
	 *
	 * @return The user's email.
	 */
	public String getUsermail() {
		return usermail;
	}

	/**
	 * Sets the user's email.
	 *
	 * @param usermail The new user's email.
	 */
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	/**
	 * Retrieves the user's gender.
	 *
	 * @return The user's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the user's gender.
	 *
	 * @param gender The new gender.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Retrieves the user's phonenumber number.
	 *
	 * @return The user's phonenumber number.
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	/**
	 * Sets the user's phonenumber number.
	 *
	 * @param phonenumber The new phonenumber number.
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * Retrieves the user's birthday.
	 *
	 * @return The user's birthday.
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Sets the user's birthday.
	 *
	 * @param birthday The new birthday.
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
  
	/**
	 * Retrieves all email addresses associated with the user.
	 *
	 * @return A list of all email addresses.
	 */
	public List<String> getAllMail() {
		return allMail;
	}

	/**
	 * Sets the list of all email addresses associated with the user.
	 *
	 * @param allMail The new list of email addresses.
	 */
	public void setAllMail(List<String> allMail) {
		this.allMail = allMail;
	}

	/**
	 * Retrieves all phonenumber numbers associated with the user.
	 *
	 * @return A list of all phonenumber numbers.
	 */
	public List<String> getAllPhone() {
		return allPhone;
	}

	/**
	 * Sets the list of all phonenumber numbers associated with the user.
	 *
	 * @param allPhone The new list of phonenumber numbers.
	 */
	public void setAllPhone(List<String> allPhone) {
		this.allPhone = allPhone;
	}
}
