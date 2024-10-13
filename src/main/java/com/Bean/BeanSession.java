package com.Bean;

import java.time.LocalDateTime;

/**
 * Represents a user session in the application.
 * This class encapsulates session-related details, including the session ID,
 * user ID, and timestamps for creation, access, and expiration.
 * 
 * <p>This class implements the Comparable interface to allow sorting based
 * on the accessed time of the sessions.</p>
 * 
 * @author Sahana
 * @version 1.0
 */
public class BeanSession implements Comparable<BeanSession> {

    private String session_id;
    private int user_id;
    private LocalDateTime creation_time;
    private LocalDateTime accessed_time;
    private LocalDateTime expiry_time;

    /**
     * Constructs a new BeanSession with the specified session ID and accessed time.
     *
     * @param session_id The ID of the session.
     * @param accessed_time The time when the session was last accessed.
     */
    public BeanSession(String session_id, LocalDateTime accessed_time) {
        this.session_id = session_id;
        this.accessed_time = accessed_time;
    }

    /**
     * Retrieves the session ID.
     *
     * @return The ID of the session.
     */
    public String getSession_id() {
        return session_id;
    }

    /**
     * Sets the session ID.
     *
     * @param session_id The new session ID.
     */
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    /**
     * Retrieves the user ID associated with this session.
     *
     * @return The user ID.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets the user ID for this session.
     *
     * @param user_id The new user ID.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves the creation time of the session.
     *
     * @return The creation time.
     */
    public LocalDateTime getCreation_time() {
        return creation_time;
    }

    /**
     * Sets the creation time for the session.
     *
     * @param creation_time The new creation time.
     */
    public void setCreation_time(LocalDateTime creation_time) {
        this.creation_time = creation_time;
    }

    /**
     * Retrieves the last accessed time of the session.
     *
     * @return The last accessed time.
     */
    public LocalDateTime getAccessed_time() {
        return accessed_time;
    }

    /**
     * Sets the last accessed time for the session.
     *
     * @param accessed_time The new last accessed time.
     */
    public void setAccessed_time(LocalDateTime accessed_time) {
        this.accessed_time = accessed_time;
    }

    /**
     * Retrieves the expiry time of the session.
     *
     * @return The expiry time.
     */
    public LocalDateTime getExpiry_time() {
        return expiry_time;
    }

    /**
     * Sets the expiry time for the session.
     *
     * @param expiry_time The new expiry time.
     */
    public void setExpiry_time(LocalDateTime expiry_time) {
        this.expiry_time = expiry_time;
    }

    /**
     * Compares this session to another session based on accessed time.
     *
     * @param other The other session to compare against.
     * @return A negative integer, zero, or a positive integer as this session
     *         is less than, equal to, or greater than the specified session.
     */
    @Override
    public int compareTo(BeanSession other) {
        int timeComparison = this.accessed_time.compareTo(other.accessed_time);
        if (timeComparison == 0) {
            return this.session_id.compareTo(other.session_id);
        }
        return timeComparison;
    }

    /**
     * Indicates whether some other object is "equal to" this session.
     *
     * @param obj The reference object with which to compare.
     * @return true if this session is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanSession that = (BeanSession) obj;
        return session_id.equals(that.session_id);
    }

    /**
     * Returns a hash code value for this session.
     *
     * @return A hash code value for this session.
     */
    @Override
    public int hashCode() {
        return session_id.hashCode();
    }
}
