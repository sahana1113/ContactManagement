package com.Bean;

import java.sql.Timestamp;
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
    private Timestamp creation_time;
    private Timestamp last_accessed;
    private Timestamp expiry_time;
    
    public BeanSession(String session_id, Timestamp accessed_time) {
        this.sessionid = session_id;
        this.last_accessed = accessed_time;
    }

    public BeanSession() {
		
	}

    public BeanSession(String sessionId2) {
		this.sessionid=sessionId2;
	}

	public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String session_id) {
        this.sessionid = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Timestamp creation_time) {
        this.creation_time = creation_time;
    }

    public Timestamp getAccessed_time() {
		return getLast_accessed();
	}

	public Timestamp getLast_accessed() {
        return last_accessed;
    }

    public void setAccessed_time(Timestamp accessed_time) {
		setLast_accessed(accessed_time);
	}

	public void setLast_accessed(Timestamp timestamp) {
        this.last_accessed = timestamp;
    }

    public Timestamp getExpiry_time() {
        return expiry_time;
    }
    
    public void setExpiry_time(Timestamp timestamp) {
        this.expiry_time = timestamp;
    }

    @Override
    public int compareTo(BeanSession other) {
        int timeComparison = this.last_accessed.compareTo(other.last_accessed);
        if (timeComparison == 0) {
            return this.sessionid.compareTo(other.sessionid);
        }
        return timeComparison;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeanSession that = (BeanSession) obj;
        return sessionid.equals(that.sessionid);
    }

    @Override
    public int hashCode() {
        return sessionid.hashCode();
    }

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
}
