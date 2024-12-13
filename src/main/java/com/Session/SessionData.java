package com.Session;

import java.io.IOException;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.Bean.*;
import com.Dao.DaoSession;
import com.Server.ServerNotifier;
/**
 * Manages the updates and storage of SESSION data in a tree set. 
 * This class tracks SESSION access times and ensures that the 
 * maximum number of sessions stored does not exceed a specified limit.
 *
 *  @author Sahana
 *  @version 1.0
 */
public class SessionData {
	private static int maxSize = 200; 
    private static TreeSet<BeanSession> sessionSet = new TreeSet<>();
    private static Map<Integer,BeanUserDetails> userData = new HashMap<>();
    private static List<String> servers=new ArrayList<>();
    /**
     * Updates the last accessed time for a given SESSION ID. If the SESSION 
     * already exists, it is removed and re-added with the new access time. 
     * If the size of the SESSION set exceeds the maximum allowed size, 
     * it triggers an update to the database.
     *
     * @param sessionId       The ID of the SESSION to update.
     * @param newLastAccessed The new last accessed time for the SESSION.
     * @throws SQLException If a database access error occurs.
     * @throws IOException 
     */
    public static void updateLastAccessed(String sessionId, Timestamp newLastAccessed) throws SQLException, IOException {
    	BeanSession tempSession = new BeanSession(sessionId, newLastAccessed);

        if (sessionSet.contains(tempSession)) {
            sessionSet.remove(tempSession);
        }
        tempSession.setExpiry_time(Timestamp.from(newLastAccessed.toInstant().plus(Duration.ofMinutes(30))));
        sessionSet.add(tempSession);
        
        if (sessionSet.size() >= maxSize) {
            DaoSession.updateSession(sessionSet);
        }
        ServerNotifier.notifyServers(sessionId, newLastAccessed, "POST");
    }
    
    public static List<String> getServers() {
		return servers;
	}

	public static void setServers(List<String> servers) {
		SessionData.servers = servers;
	}

	public static int getMaxSize() {
		return maxSize;
	}
	public static void setMaxSize(int maxSize) {
		SessionData.maxSize = maxSize;
	}
	public static Map<Integer, BeanUserDetails> getUserData() {
		return userData;
	}
	public static void setUserData(Map<Integer, BeanUserDetails> userData) {
		SessionData.userData = userData;
	}
	public static void setUserDataValue(BeanUserDetails obj,int user_id)
	{
		userData.put(user_id, obj);
	}
    /**
     * Retrieves the current set of sessions.
     *
     * @return A TreeSet containing the current sessions.
     */
    public static TreeSet<BeanSession> getSessionSet() {
		return sessionSet;
	}
    /**
     * Sets the SESSION set to a new TreeSet of sessions.
     *
     * @param sessionSet The new TreeSet of sessions to set.
     */
	public static void setSessionSet(TreeSet<BeanSession> sessionSet) {
		SessionData.sessionSet = sessionSet;
	}
	/**
     * Removes a SESSION from the set based on the SESSION ID.
     *
     * @param sessionId The ID of the SESSION to remove.
     */
	public static void removeObj(String sessionId) {
        sessionSet.removeIf(session -> session.getSessionid().equals(sessionId)); 
    }
}

