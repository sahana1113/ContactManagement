package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeSet;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import com.example.*;
import com.Bean.BeanSession;
import com.Session.SessionTreeUpdate;
/**
 * Data Access Object (DAO) for managing user sessions in the database.
 * This singleton class provides methods to create, validate, update, and delete sessions.
 *
 * @author Sahana
 * @version 1.0
 */
public class DaoSession {
	private static DaoSession instance;
	/**
	 * Retrieves the singleton instance of DaoSession. 
	 * If a session ID is provided, updates the last accessed time.
	 *
	 * @param session The session ID to update last accessed time.
	 * @return The singleton instance of DaoSession.
	 * @since 1.0
	 */
	public static synchronized DaoSession getInstance(String session) {
	        if (instance == null) {
	            instance = new DaoSession();
	        }
	        if(session!=null)
				try {
					SessionTreeUpdate.updateLastAccessed(session, LocalDateTime.now());
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        return instance;
	    }
	/**
	 * Creates a new session in the database.
	 *
	 * @param sessionId The unique ID of the session.
	 * @param userId The ID of the user associated with the session.
	 * @param expiryTime The expiration time of the session.
	 * @return true if the session was created successfully, false otherwise.
	 */
	 public boolean createSession(String sessionId, int userId, LocalDateTime expiryTime) {
	        String sql = "INSERT INTO session (sessionid, user_id, expiry_time, last_accessed) VALUES (?, ?, ?, ?)";
	        try (Connection con = HikariCPDataSource.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	        	LocalDateTime lastAccessed=LocalDateTime.now();
	        	stmt.setString(1, sessionId);
	            stmt.setInt(2, userId);
	            stmt.setTimestamp(3, Timestamp.valueOf(expiryTime));
	            stmt.setTimestamp(4, Timestamp.valueOf(lastAccessed));  
	            int rowsAffected = stmt.executeUpdate();
	            boolean isCreated = rowsAffected > 0;
	            return isCreated;
	        } catch (SQLException  e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 /**
	  * Updates the last accessed time and expiry time for a set of sessions.
	  *
	  * @param list A TreeSet of BeanSession objects containing session details.
	  * @return true if any sessions were updated, false otherwise.
	  * @throws SQLException if a database access error occurs.
	  */
	public static boolean updateSession(TreeSet<BeanSession> list) throws SQLException {
		 int[] rowsAffected =null;
		 TreeSet<BeanSession> list1=new TreeSet<BeanSession>(list);
		 list.clear();
		 String sql = "UPDATE session SET last_accessed = ?, expiry_time = ? WHERE sessionid = ? AND last_accessed <> ?";
		 try (	 Connection con = HikariCPDataSource.getConnection();
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        
		        for (BeanSession obj : list1) {
		            stmt.setTimestamp(1, Timestamp.valueOf(obj.getAccessed_time())); 
		            stmt.setTimestamp(2, Timestamp.valueOf(obj.getAccessed_time().plusMinutes(30))); 
		            stmt.setString(3, obj.getSessionid());
		            stmt.setTimestamp(4, Timestamp.valueOf(obj.getAccessed_time())); 
		            stmt.addBatch();  
		        }
		        rowsAffected = stmt.executeBatch();  
		        System.out.println("In update");
		    } 
		 return rowsAffected.length > 0;
    }
	/**
	 * Deletes expired sessions from the database in batches.
	 *
	 * @throws SQLException if a database access error occurs.
	 */
	public static void autoDeleteExpiredSessions() throws SQLException {
		 String sql = "DELETE FROM session WHERE expiry_time < CURRENT_TIMESTAMP LIMIT 200";
		    int rowsDeleted;

		    try ( Connection con = HikariCPDataSource.getConnection();
		         PreparedStatement stmt = con.prepareStatement(sql)) {

		        do {
		            rowsDeleted = stmt.executeUpdate();
		            System.out.println(rowsDeleted + " session(s) deleted in this batch.");
		        } while (rowsDeleted > 0); 
		    } 
		    System.out.print("in delete");
	}
	/**
	 * Validates a session by checking its existence and expiry time.
	 *
	 * @param sessionId The ID of the session to validate.
	 * @param cookies An array of cookies associated with the session.
	 * @return The user ID if the session is valid; 0 if the session is invalid or expired.
	 */
	 public int validateSession(String sessionId, Cookie[] cookies) {
	        String sql = "SELECT user_id,expiry_time FROM session WHERE sessionid = ?";
	        try (	            Connection con = HikariCPDataSource.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	             
	            stmt.setString(1, sessionId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	            	if(cookies==null ||rs.getTimestamp("expiry_time").before(new Timestamp(System.currentTimeMillis())))
	            	{
	            		invalidateSession(sessionId);
	            		return 0;
	            	}
	                return rs.getInt("user_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }
	 /**
	  * Invalidates a session by deleting it from the database.
	  *
	  * @param sessionId The ID of the session to invalidate.
	  * @return true if the session was successfully invalidated, false otherwise.
	  */
	public boolean invalidateSession(String sessionId) {
        String sql = "DELETE FROM session WHERE sessionid = ?";
        try  {
            Connection con = HikariCPDataSource.getConnection();
	    	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sessionId);
            SessionTreeUpdate.removeObj(sessionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException  e) {
            e.printStackTrace();
            return false;
        }
    }
}
