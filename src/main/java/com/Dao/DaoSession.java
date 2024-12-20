package com.Dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeSet;

import javax.servlet.http.Cookie;

import com.Bean.BeanSession;
import com.Bean.BeanUserDetails;
import com.Query.Column;
import com.Query.Condition;
import com.Query.Enum.Session;
import com.Query.Enum.Tables;
import com.Query.QueryLayer;
import com.Server.ServerNotifier;
import com.Session.*;
import com.example.HikariCPDataSource;

public class DaoSession {
	private static DaoSession instance;
	public static synchronized DaoSession getInstance(String session) throws IOException {
	        if (instance == null) {
	            instance = new DaoSession();
	        }
	        if(session!=null)
				try {
					SessionData.updateLastAccessed(session, Timestamp.valueOf(LocalDateTime.now()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        return instance;
	    }
	
	 public boolean createSession(String sessionId, int userId, LocalDateTime expiryTime) throws Exception {
		 BeanSession obj=new BeanSession();
		 obj.setUser_id(userId);
		 obj.setLast_accessed(Timestamp.valueOf(LocalDateTime.now()));
		 obj.setExpiry_time(Timestamp.valueOf(expiryTime));
		 obj.setSessionid(sessionId);
		 SessionData.getSessionSet().add(obj);
		 int k=QueryLayer.buildInsertQuery(Tables.SESSION, obj, new Column[] {Session.sessionid,Session.user_id,Session.expiry_time,Session.last_accessed});
		 DaoUserContact rd=new DaoUserContact();
		 BeanUserDetails user=rd.getUserDetailsById(userId);
		 SessionData.setUserDataValue(user, userId);
		 return k!=0;
	    }
	 
	public static boolean updateSession(TreeSet<BeanSession> list) throws SQLException {
		 int[] rowsAffected =null;
		 TreeSet<BeanSession> list1=new TreeSet<BeanSession>(list);
		 list.clear();
		 String sql = "UPDATE session SET last_accessed = ?, expiry_time = ? WHERE sessionid = ? AND last_accessed <> ?";
		 try (	 Connection con = HikariCPDataSource.getConnection();
		         PreparedStatement stmt = con.prepareStatement(sql)) {
		        
		        for (BeanSession obj : list1) {
		            stmt.setTimestamp(1, obj.getLast_accessed()); 
		            stmt.setTimestamp(2, obj.getExpiry_time()); 
		            stmt.setString(3, obj.getSessionid());
		            stmt.setTimestamp(4, obj.getLast_accessed()); 
		            stmt.addBatch();  
		        }
		        rowsAffected = stmt.executeBatch();  
		        System.out.println("In update");
		    } 
		 return rowsAffected.length > 0;
    }
	public static void autoDeleteExpiredSessions() throws SQLException {
		 String sql = "DELETE FROM session WHERE expiry_time < CURRENT_TIMESTAMP LIMIT 200";
		    int rowsDeleted;

		    try ( Connection con = HikariCPDataSource.getConnection();
		         PreparedStatement stmt = con.prepareStatement(sql)) {

		        do {
		            rowsDeleted = stmt.executeUpdate();
		            System.out.println(rowsDeleted + " SESSION(s) deleted in this batch.");
		        } while (rowsDeleted > 0); 
		    } 
		    System.out.print("in delete");
	}
	 public int validateSession(String sessionId, Cookie[] cookies) throws Exception {
		    BeanSession obj=new BeanSession();
		    obj.setSessionid(sessionId);
		    for (BeanSession session : SessionData.getSessionSet()) {
	            if (session.getSessionid().equals(sessionId)) {
	            	if (cookies == null ||session.getExpiry_time().before(new Timestamp(System.currentTimeMillis())))
			    	 {
			    		 invalidateSession(sessionId);
		            	 return 0;
			    	 }
	                return session.getUser_id(); 
	            }
	        }
	        return 0;
	    }
	 
	public boolean invalidateSession(String sessionId) throws SQLException, IOException {
		BeanSession s=new BeanSession();
		s.setSessionid(sessionId);
		int k=QueryLayer.buildDeleteQuery(
				Tables.SESSION, new Condition(Session.user_id,"=",false), null, s);
		SessionData.removeObj(sessionId);
		ServerNotifier.notifyServers(sessionId, null, "DELETE");
		return k>0;
    }
}
