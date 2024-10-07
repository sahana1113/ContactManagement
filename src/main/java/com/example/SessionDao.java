package com.example;

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

public class SessionDao {
	private static LocalDateTime lastAccessed;
	private LocalDateTime lastCheckTime;
	private ScheduledExecutorService scheduler;
	private static String session;
	private static SessionDao instance;
	public static synchronized SessionDao getInstance(String session) {
	        if (instance == null) {
	            instance = new SessionDao();
	        }
	        if(session!=null)
            SessionManagerScheduler.updateLastAccessed(session, LocalDateTime.now());
	        return instance;
	    }
    boolean check=true;
	 public boolean createSession(String sessionId, int userId, LocalDateTime expiryTime) {
	        String sql = "INSERT INTO session (sessionid, user_id, expiry_time, last_accessed) VALUES (?, ?, ?, ?)";
	        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	        	lastAccessed=LocalDateTime.now();
	        	stmt.setString(1, sessionId);
	            stmt.setInt(2, userId);
	            stmt.setTimestamp(3, Timestamp.valueOf(expiryTime));
	            stmt.setTimestamp(4, Timestamp.valueOf(lastAccessed));  
	            int rowsAffected = stmt.executeUpdate();
	            boolean isCreated = rowsAffected > 0;
	           // startScheduledTask(sessionId);
	            return isCreated;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
//	public void updateAllSessions() {
//	    String sql = "SELECT sessionid FROM session WHERE expiry_time > CURRENT_TIMESTAMP";  // Fetch valid sessions
//	    try  {
//	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
//	        PreparedStatement stmt = con.prepareStatement(sql);
//	        ResultSet rs = stmt.executeQuery();
//	        while (rs.next()) {
//	            String sessionId = rs.getString("sessionid");
//	            updateSession(sessionId);  // Call update for each session
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	}

	public static boolean updateSession(List<SessionManagerScheduler> list) throws SQLException {
		 int rowsAffected=0;
		for(SessionManagerScheduler obj:list) {
        String sql = "UPDATE session SET last_accessed = ?, expiry_time = ? WHERE sessionid = ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setTimestamp(1, Timestamp.valueOf(obj.lastaccessed)); 
            stmt.setTimestamp(2, Timestamp.valueOf(obj.lastaccessed.plusMinutes(3))); 
            stmt.setString(3, obj.sessionid);
            rowsAffected = stmt.executeUpdate();
            System.out.println("updated:"+lastAccessed);
           
        } 
	}
		 return rowsAffected > 0;
    }
	public static boolean autoDeleteExpiredSessions() {
        String sql = "DELETE FROM session WHERE expiry_time < CURRENT_TIMESTAMP";
        try  {
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	    	PreparedStatement stmt = con.prepareStatement(sql);
            int rowsDeleted = stmt.executeUpdate();
            if(rowsDeleted>0)
            	System.out.println("deleted");
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	 public int validateSession(String sessionId) {
	        String sql = "SELECT user_id FROM session WHERE sessionid = ?";
	        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	             
	            stmt.setString(1, sessionId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	    	        lastAccessed = LocalDateTime.now(); 
	            	System.out.println("last:"+lastAccessed);
	                return rs.getInt("user_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }
	public LocalDateTime getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(LocalDateTime lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	public boolean invalidateSession(String sessionId) {
        String sql = "DELETE FROM session WHERE sessionid = ?";
        try  {
	    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
	    	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sessionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
