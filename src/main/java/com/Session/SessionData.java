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
import com.Dao.DaoUserContact;
import com.Server.ServerNotifier;

public class SessionData {
	private static int maxSize = 200; 
    private static TreeSet<BeanSession> sessionSet = new TreeSet<>();
    private static Map<Integer,BeanUserDetails> userData = new HashMap<>();
    private static List<String> servers=new ArrayList<>();
    private static List<BeanContactDetails> list;
    private static Map<String,Object> auditCache=new HashMap<>();
    
    public static List<BeanContactDetails> getList() {
		return list;
	}
	public static void setList(List<BeanContactDetails> list) {
		SessionData.list = list;
	}
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
    public static void getDataFromDB(int userId) throws Exception
    {
    	DaoUserContact rd = new DaoUserContact();
		BeanUserDetails user = rd.getUserDetailsById(userId);
		SessionData.setUserDataValue(user, userId);
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
    public static TreeSet<BeanSession> getSessionSet() {
		return sessionSet;
	}
	public static void setSessionSet(TreeSet<BeanSession> sessionSet) {
		SessionData.sessionSet = sessionSet;
	}
	public static void removeObj(String sessionId) {
        sessionSet.removeIf(session -> session.getSessionid().equals(sessionId)); 
    }
	public static Map<String, Object> getAuditCache() {
		return auditCache;
	}
	public static void setAuditCache(Map<String, Object> auditCache) {
		SessionData.auditCache = auditCache;
	}
	public static void addAuditCache(String key,Object value)
	{
		auditCache.put(key,value);
	}
	public static void removeAuditCache(String key)
	{
		auditCache.remove(key);
	}
}

