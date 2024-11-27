package com.Bean;

import java.util.List;

/**
 * Represents the contact details of a person.
 * This class encapsulates various attributes related to a contact, 
 * including name, email, phonenumber number, and other personal details.
 * 
 * @author Sahana
 * @version 1.0
 */
public class BeanContactDetails implements Bean{

    private String name;
    private String mail;
    private String gender;
    private String phonenumber;
    private String birthday;
    private String location;
    private int contact_id;
    private int user_id;
    private String alt_mail;
    private String alt_phone;
    private List<String> category;
    private String category_name;
    private long created_time; // Store the created time in seconds
    public long getCreatedTimeInEpoch() {
		return getCreated_time();
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreatedTimeInEpoch(long createdTimeInEpoch) {
		setCreated_time(createdTimeInEpoch);
	}

	public void setCreated_time(long createdTimeInEpoch) {
		this.created_time = createdTimeInEpoch;
	}

	/**
     * Default constructor for BeanContactDetails.
     * Initializes a new instance with default values.
     */
    public BeanContactDetails() {
    }

    /**
     * Constructs a new BeanContactDetails with the specified name, phonenumber number, and contact ID.
     *
     * @param name The name of the contact.
     * @param phonenumber The phonenumber number of the contact.
     * @param contact_id The ID of the contact.
     */
    public BeanContactDetails(String contactname, String phonenumber, int contact_id) {
        this.name = contactname;
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
     * Retrieves the alternative phonenumber number of the contact.
     *
     * @return The alternative phonenumber number.
     */
    public String getAlt_phone() {
        return alt_phone;
    }

    /**
     * Sets the alternative phonenumber number for the contact.
     *
     * @param alt_phone The new alternative phonenumber number.
     */
    public void setAlt_phone(String alt_phone) {
        this.alt_phone = alt_phone;
    }

    /**
     * Retrieves the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contact.
     *
     * @param name The new name of the contact.
     */
    public void setName(String contactname) {
        this.name = contactname;
    }

    /**
	 * Retrieves the email of the contact.
	 *
	 * @return The email of the contact.
	 */
	public String getContactmail() {
		return getMail();
	}

	/**
     * Retrieves the email of the contact.
     *
     * @return The email of the contact.
     */
    public String getMail() {
        return mail;
    }

    /**
	 * Sets the email of the contact.
	 *
	 * @param contactmail The new email of the contact.
	 */
	public void setContactmail(String contactmail) {
		setMail(contactmail);
	}

	/**
     * Sets the email of the contact.
     *
     * @param mail The new email of the contact.
     */
    public void setMail(String contactmail) {
        this.mail = contactmail;
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
     * Retrieves the phonenumber number of the contact.
     *
     * @return The phonenumber number of the contact.
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets the phonenumber number of the contact.
     *
     * @param phonenumber The new phonenumber number of the contact.
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
     * Retrieves the CATEGORIES associated with the contact.
     *
     * @return A list of CATEGORIES.
     */
    public List<String> getCategory() {
        return category;
    }

    /**
     * Sets the CATEGORIES for the contact.
     *
     * @param category A list of CATEGORIES to associate with the contact.
     */
    public void setCategory(List<String> category) {
        this.category = category;
    }

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
}
