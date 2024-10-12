package com.example;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.Bean.BeanSession;
import com.Dao.DaoSession;

public class SessionTreeUpdate {
    private static int maxSize = 200; 
    private static TreeSet<BeanSession> sessionSet = new TreeSet<>();

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

    public static TreeSet<BeanSession> getSessionSet() {
		return sessionSet;
	}

	public static void setSessionSet(TreeSet<BeanSession> sessionSet) {
		SessionTreeUpdate.sessionSet = sessionSet;
	}

	public static void removeObj(String sessionId) {
        sessionSet.removeIf(session -> session.getSession_id().equals(sessionId)); 
    }
}

