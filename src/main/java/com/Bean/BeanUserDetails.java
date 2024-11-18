package com.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents user details in the application.
 * This class encapsulates information about a user, including personal details,
 * contact information, and lists of alternative contacts.
 * 
 * <p>This class provides methods to get and set user attributes.</p>
 * 
 * @author Sahana
 * @version 1.0
 */
public class BeanUserDetails implements Bean{

    private String username;
    private String usermail;
    private String gender;
    private String phonenumber;
    private String birthday;
    private String password;
    private int user_id;
    private int total_contacts;
    private int total_categories;
    private String altmail;
    private String altphone;
    private List<String> allMail = new ArrayList<>();
    private List<String> allPhone = new ArrayList<>();

    /**
     * Default constructor for BeanUserDetails.
     */
    public BeanUserDetails() {
    }

    /**
     * Constructs a new BeanUserDetails with the specified username and phone number.
     *
     * @param username The username of the user.
     * @param phonenumber The phone number of the user.
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
     * Retrieves the user's phone number.
     *
     * @return The user's phone number.
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets the user's phone number.
     *
     * @param phonenumber The new phone number.
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
     * Retrieves the total number of contacts for the user.
     *
     * @return The total number of contacts.
     */
    public int getTotal_contacts() {
        return total_contacts;
    }

    /**
     * Sets the total number of contacts for the user.
     *
     * @param total_contacts The new total number of contacts.
     */
    public void setTotal_contacts(int total_contacts) {
        this.total_contacts = total_contacts;
    }

    /**
     * Retrieves the total number of categories for the user.
     *
     * @return The total number of categories.
     */
    public int getTotal_categories() {
        return total_categories;
    }

    /**
     * Sets the total number of categories for the user.
     *
     * @param total_categories The new total number of categories.
     */
    public void setTotal_categories(int total_categories) {
        this.total_categories = total_categories;
    }

    /**
     * Retrieves the alternative email for the user.
     *
     * @return The alternative email.
     */
    public String getAltmail() {
        return altmail;
    }

    /**
     * Sets the alternative email for the user.
     *
     * @param altmail The new alternative email.
     */
    public void setAltmail(String altmail) {
        this.altmail = altmail;
    }

    /**
     * Retrieves the alternative phone number for the user.
     *
     * @return The alternative phone number.
     */
    public String getAltphone() {
        return altphone;
    }

    /**
     * Sets the alternative phone number for the user.
     *
     * @param altphone The new alternative phone number.
     */
    public void setAltphone(String altphone) {
        this.altphone = altphone;
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
     * Retrieves all phone numbers associated with the user.
     *
     * @return A list of all phone numbers.
     */
    public List<String> getAllPhone() {
        return allPhone;
    }

    /**
     * Sets the list of all phone numbers associated with the user.
     *
     * @param allPhone The new list of phone numbers.
     */
    public void setAllPhone(List<String> allPhone) {
        this.allPhone = allPhone;
    }
}
