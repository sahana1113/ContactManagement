package com.Bean;

/**
 * Represents a category_name with an ID and a name.
 * This class is used to manage category_name data in the application.
 * @author Sahana
 * @version 1.0
 */
public class BeanCategory implements Bean{

    // Category ID
    private int category_id;

    // Category name
    private String category_name;

    /**
     * Constructs a new BeanCategory with the specified ID and name.
     *
     * @param category_id The ID of the category_name.
     * @param category_name The name of the category_name.
     */
    public BeanCategory(int c_id, String category) {
        this.category_id = c_id;
        this.category_name = category;
    }
    public BeanCategory() {
    	
    }
    /**
	 * Retrieves the category_name ID.
	 *
	 * @return The ID of the category_name.
	 */
	public int getC_id() {
		return getCategory_id();
	}

	/**
     * Retrieves the category_name ID.
     *
     * @return The ID of the category_name.
     */
    public int getCategory_id() {
        return category_id;
    }

    /**
	 * Sets the category_name ID.
	 *
	 * @param c_id The new ID for the category_name.
	 */
	public void setC_id(int c_id) {
		setCategory_id(c_id);
	}

	/**
     * Sets the category_name ID.
     *
     * @param category_id The new ID for the category_name.
     */
    public void setCategory_id(int c_id) {
        this.category_id = c_id;
    }

    /**
	 * Retrieves the category name.
	 *
	 * @return The name of the category.
	 */
	public String getCategory() {
		return getCategory_name();
	}

	/**
     * Retrieves the category_name name.
     *
     * @return The name of the category_name.
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
	 * Sets the category name.
	 *
	 * @param category The new name for the category.
	 */
	public void setCategory(String category) {
		setCategory_name(category);
	}

	/**
     * Sets the category_name name.
     *
     * @param category_name The new name for the category_name.
     */
    public void setCategory_name(String category) {
        this.category_name = category;
    }
}
