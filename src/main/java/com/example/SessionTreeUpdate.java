package com.example;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.Bean.BeanSession;
import com.Dao.DaoSession;
/**
 * Manages the updates and storage of session data in a tree set. 
 * This class tracks session access times and ensures that the 
 * maximum number of sessions stored does not exceed a specified limit.
 *
 *  @author Sahana
 *  @version 1.0
 */
public class SessionTreeUpdate {
    private static int maxSize = 200; 
    private static TreeSet<BeanSession> sessionSet = new TreeSet<>();
    /**
     * Updates the last accessed time for a given session ID. If the session 
     * already exists, it is removed and re-added with the new access time. 
     * If the size of the session set exceeds the maximum allowed size, 
     * it triggers an update to the database.
     *
     * @param sessionId       The ID of the session to update.
     * @param newLastAccessed The new last accessed time for the session.
     * @throws SQLException If a database access error occurs.
     */
    public static void updateLastAccessed(String sessionId, LocalDateTime newLastAccessed) throws SQLException {
    	BeanSession tempSession = new BeanSession(sessionId, newLastAccessed);

        if (sessionSet.contains(tempSession)) {
            sessionSet.remove(tempSession);
        }

        sessionSet.add(tempSession);

        if (sessionSet.size() >= maxSize) {
            DaoSession.updateSession(sessionSet);
        }
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
     * Sets the session set to a new TreeSet of sessions.
     *
     * @param sessionSet The new TreeSet of sessions to set.
     */
	public static void setSessionSet(TreeSet<BeanSession> sessionSet) {
		SessionTreeUpdate.sessionSet = sessionSet;
	}
	/**
     * Removes a session from the set based on the session ID.
     *
     * @param sessionId The ID of the session to remove.
     */
	public static void removeObj(String sessionId) {
        sessionSet.removeIf(session -> session.getSession_id().equals(sessionId)); 
    }
}

