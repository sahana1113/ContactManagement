package com.Bean;

import java.time.LocalDateTime;

/**
 * Represents a user SESSION in the application.
 * This class encapsulates SESSION-related details, including the SESSION ID,
 * user ID, and timestamps for creation, access, and expiration.
 * 
 * <p>This class implements the Comparable interface to allow sorting based
 * on the accessed time of the sessions.</p>
 * 
 * @author Sahana
 * @version 1.0
 */
public class BeanSession implements Comparable<BeanSession>,Bean{

    private String sessionid;
    private int user_id;
    private LocalDateTime creation_time;
    private LocalDateTime accessed_time;
    private LocalDateTime expiry_time;

    /**
     * Constructs a new BeanSession with the specified SESSION ID and accessed time.
     *
     * @param sessionid The ID of the SESSION.
     * @param accessed_time The time when the SESSION was last accessed.
     */
    public BeanSession(String session_id, LocalDateTime accessed_time) {
        this.sessionid = session_id;
        this.accessed_time = accessed_time;
    }

    /**
	 * Retrieves the SESSION ID.
	 *
	 * @return The ID of the SESSION.
	 */
	public String getSession_id() {
		return getSessionid();
	}

	/**
     * Retrieves the SESSION ID.
     *
     * @return The ID of the SESSION.
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
	 * Sets the SESSION ID.
	 *
	 * @param session_id The new SESSION ID.
	 */
	public void setSession_id(String session_id) {
		setSessionid(session_id);
	}

	/**
     * Sets the SESSION ID.
     *
     * @param sessionid The new SESSION ID.
     */
    public void setSessionid(String session_id) {
        this.sessionid = session_id;
    }

    /**
     * Retrieves the user ID associated with this SESSION.
     *
     * @return The user ID.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets the user ID for this SESSION.
     *
     * @param user_id The new user ID.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves the creation time of the SESSION.
     *
     * @return The creation time.
     */
    public LocalDateTime getCreation_time() {
        return creation_time;
    }

    /**
     * Sets the creation time for the SESSION.
     *
     * @param creation_time The new creation time.
     */
    public void setCreation_time(LocalDateTime creation_time) {
        this.creation_time = creation_time;
    }

    /**
     * Retrieves the last accessed time of the SESSION.
     *
     * @return The last accessed time.
     */
    public LocalDateTime getAccessed_time() {
        return accessed_time;
    }

    /**
     * Sets the last accessed time for the SESSION.
     *
     * @param accessed_time The new last accessed time.
     */
    public void setAccessed_time(LocalDateTime accessed_time) {
        this.accessed_time = accessed_time;
    }

    /**
     * Retrieves the expiry time of the SESSION.
     *
     * @return The expiry time.
     */
    public LocalDateTime getExpiry_time() {
        return expiry_time;
    }

    /**
     * Sets the expiry time for the SESSION.
     *
     * @param expiry_time The new expiry time.
     */
    public void setExpiry_time(LocalDateTime expiry_time) {
        this.expiry_time = expiry_time;
    }

    /**
     * Compares this SESSION to another SESSION based on accessed time.
     *
     * @param other The other SESSION to compare against.
     * @return A negative integer, zero, or a positive integer as this SESSION
     *         is less than, equal to, or greater than the specified SESSION.
     */
    @Override
    public int compareTo(BeanSession other) {
        int timeComparison = this.accessed_time.compareTo(other.accessed_time);
        if (timeComparison == 0) {
            return this.sessionid.compareTo(other.sessionid);
        }
        return timeComparison;
    }

    /**
     * Indicates whether some other object is "equal to" this SESSION.
     *
     * @param obj The reference object with which to compare.
     * @return true if this SESSION is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanSession that = (BeanSession) obj;
        return sessionid.equals(that.sessionid);
    }

    /**
     * Returns a hash code value for this SESSION.
     *
     * @return A hash code value for this SESSION.
     */
    @Override
    public int hashCode() {
        return sessionid.hashCode();
    }
}
