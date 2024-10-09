package com.example;

import java.time.LocalDateTime;
import java.util.Iterator;

import com.Bean.BeanSession;

public class SessionListUpdate {
	public static void updateLastAccessed(String sessionId, LocalDateTime newLastAccessed) {
		boolean check=false;
        for (BeanSession session : BeanSession.getList()) {
            if (session.getSession_id().equals(sessionId)) {
                session.setAccessed_time(newLastAccessed);
                check=true;
                break;
            }
        }
        if(!check)
        {
        	BeanSession.getList().add(new BeanSession(sessionId,newLastAccessed));
        }
    }
	public static void removeObj(String sessionId)
	{
		 Iterator<BeanSession> iterator = BeanSession.getList().iterator();
	        while (iterator.hasNext()) {
	            BeanSession session = iterator.next();
	            if (session.getSession_id().equals(sessionId)) { 
	                iterator.remove();
	                break;
	            }
	        }
	}
}
