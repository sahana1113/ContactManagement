package com.Bean;

import java.util.List;

/**
 * Represents the contact details of a person.
 * This class encapsulates various attributes related to a contact, 
 * including name, email, phone number, and other personal details.
 * 
 * @author Your Name
 * @version 1.0
 */
public class BeanContactDetails {

    private String contactname;
    private String contactmail;
    private String gender;
    private String phonenumber;
    private String birthday;
    private String location;
    private int contact_id;
    private int user_id;
    private String alt_mail;
    private String alt_phone;
    private List<String> category;

    /**
     * Default constructor for BeanContactDetails.
     * Initializes a new instance with default values.
     */
    public BeanContactDetails() {
    }

    /**
     * Constructs a new BeanContactDetails with the specified name, phone number, and contact ID.
     *
     * @param contactname The name of the contact.
     * @param phonenumber The phone number of the contact.
     * @param contact_id The ID of the contact.
     */
    public BeanContactDetails(String contactname, String phonenumber, int contact_id) {
        this.contactname = contactname;
        this.phonenumber = phonenumber;
        this.contact_id = contact_id;
    }

    /**
     * Retrieves the user ID associated with this contact.
     *
     * @return The user ID.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets the user ID for this contact.
     *
     * @param user_id The new user ID.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves the alternative email of the contact.
     *
     * @return The alternative email.
     */
    public String getAlt_mail() {
        return alt_mail;
    }

    /**
     * Sets the alternative email for the contact.
     *
     * @param alt_mail The new alternative email.
     */
    public void setAlt_mail(String alt_mail) {
        this.alt_mail = alt_mail;
    }

    /**
     * Retrieves the alternative phone number of the contact.
     *
     * @return The alternative phone number.
     */
    public String getAlt_phone() {
        return alt_phone;
    }

    /**
     * Sets the alternative phone number for the contact.
     *
     * @param alt_phone The new alternative phone number.
     */
    public void setAlt_phone(String alt_phone) {
        this.alt_phone = alt_phone;
    }

    /**
     * Retrieves the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getContactname() {
        return contactname;
    }

    /**
     * Sets the name of the contact.
     *
     * @param contactname The new name of the contact.
     */
    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    /**
     * Retrieves the email of the contact.
     *
     * @return The email of the contact.
     */
    public String getContactmail() {
        return contactmail;
    }

    /**
     * Sets the email of the contact.
     *
     * @param contactmail The new email of the contact.
     */
    public void setContactmail(String contactmail) {
        this.contactmail = contactmail;
    }

    /**
     * Retrieves the gender of the contact.
     *
     * @return The gender of the contact.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the contact.
     *
     * @param gender The new gender of the contact.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the phone number of the contact.
     *
     * @return The phone number of the contact.
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets the phone number of the contact.
     *
     * @param phonenumber The new phone number of the contact.
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * Retrieves the birthday of the contact.
     *
     * @return The birthday of the contact.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the contact.
     *
     * @param birthday The new birthday of the contact.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Retrieves the location of the contact.
     *
     * @return The location of the contact.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the contact.
     *
     * @param location The new location of the contact.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the contact ID.
     *
     * @return The contact ID.
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Sets the contact ID.
     *
     * @param contact_id The new contact ID.
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Retrieves the categories associated with the contact.
     *
     * @return A list of categories.
     */
    public List<String> getCategory() {
        return category;
    }

    /**
     * Sets the categories for the contact.
     *
     * @param category A list of categories to associate with the contact.
     */
    public void setCategory(List<String> category) {
        this.category = category;
    }
}
