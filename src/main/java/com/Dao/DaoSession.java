package com.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import com.example.*;
import com.Bean.BeanSession;

public class DaoSession {
	private static DaoSession instance;
	public static synchronized DaoSession getInstance(String session) {
	        if (instance == null) {
	            instance = new DaoSession();
	        }
	        if(session!=null)
            SessionListUpdate.updateLastAccessed(session, LocalDateTime.now());
	        return instance;
	    }
    boolean check=true;
	 public boolean createSession(String sessionId, int userId, LocalDateTime expiryTime) {
	        String sql = "INSERT INTO session (sessionid, user_id, expiry_time, last_accessed) VALUES (?, ?, ?, ?)";
	        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	        	LocalDateTime lastAccessed=LocalDateTime.now();
	        	stmt.setString(1, sessionId);
	            stmt.setInt(2, userId);
	            stmt.setTimestamp(3, Timestamp.valueOf(expiryTime));
	            stmt.setTimestamp(4, Timestamp.valueOf(lastAccessed));  
	            int rowsAffected = stmt.executeUpdate();
	            boolean isCreated = rowsAffected > 0;
	            return isCreated;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	public static boolean updateSession(List<BeanSession> list) throws SQLException {
		 int[] rowsAffected;
		 String sql = "UPDATE session SET last_accessed = ?, expiry_time = ? WHERE sessionid = ? AND last_accessed <> ?";
		 try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        
		        for (BeanSession obj : list) {
		            stmt.setTimestamp(1, Timestamp.valueOf(obj.getAccessed_time())); 
		            stmt.setTimestamp(2, Timestamp.valueOf(obj.getAccessed_time().plusMinutes(3))); 
		            stmt.setString(3, obj.getSession_id());
		            stmt.setTimestamp(4, Timestamp.valueOf(obj.getAccessed_time())); 
		            
		            stmt.addBatch();  
		        }
		        rowsAffected = stmt.executeBatch();  
		        System.out.println("In update");
		    }
		 list.clear();
		 return rowsAffected.length > 0;
    }
	public static boolean autoDeleteExpiredSessions(List<BeanSession> sessionList) throws SQLException {
	    int[] rowsDeleted;
	    String sql = "DELETE FROM session WHERE sessionid = ? AND DATE_ADD(?, INTERVAL 3 MINUTE) < CURRENT_TIMESTAMP";
	    
	    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	         PreparedStatement stmt = con.prepareStatement(sql)) {
	        
	        for (BeanSession obj : sessionList) {
	            stmt.setString(1, obj.getSession_id()); 
	            stmt.setTimestamp(2, Timestamp.valueOf(obj.getAccessed_time()));  
	            stmt.addBatch(); 
	        }
	        rowsDeleted = stmt.executeBatch();  
	        System.out.println("In delete");
	    }
	    return rowsDeleted.length > 0;
	}
	 public int validateSession(String sessionId, Cookie[] cookies) {
	        String sql = "SELECT user_id,expiry_time FROM session WHERE sessionid = ?";
	        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
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
	public boolean invalidateSession(String sessionId) {
        String sql = "DELETE FROM session WHERE sessionid = ?";
        try  {
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	    	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sessionId);
            SessionListUpdate.removeObj(sessionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
