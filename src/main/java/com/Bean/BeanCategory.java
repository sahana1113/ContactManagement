package com.Bean;

/**
 * Represents a category with an ID and a name.
 * This class is used to manage category data in the application.
 * @author Sahana
 * @version 1.0
 */
public class BeanCategory {

    // Category ID
    private int c_id;

    // Category name
    private String category;

    /**
     * Constructs a new BeanCategory with the specified ID and name.
     *
     * @param c_id The ID of the category.
     * @param category The name of the category.
     */
    public BeanCategory(int c_id, String category) {
        this.c_id = c_id;
        this.category = category;
    }

    /**
     * Retrieves the category ID.
     *
     * @return The ID of the category.
     */
    public int getC_id() {
        return c_id;
    }

    /**
     * Sets the category ID.
     *
     * @param c_id The new ID for the category.
     */
    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    /**
     * Retrieves the category name.
     *
     * @return The name of the category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category name.
     *
     * @param category The new name for the category.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
