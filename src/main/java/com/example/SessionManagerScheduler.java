package com.example;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionManagerScheduler {
    private static List<SessionManagerScheduler> list=new ArrayList<>();
    String sessionid;
    LocalDateTime lastaccessed;
   
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
   // private SessionDao sessionDAO = new SessionDao();
    public SessionManagerScheduler(String sessionid, LocalDateTime lastaccessed) {
		this.sessionid = sessionid;
		this.lastaccessed = lastaccessed;
	}
	public static List<SessionManagerScheduler> getList() {
		return list;
	}
	public static void setList(List<SessionManagerScheduler> list) {
		SessionManagerScheduler.list = list;
	}
	public static void updateLastAccessed(String sessionId, LocalDateTime newLastAccessed) {
		boolean check=false;
        for (SessionManagerScheduler session : list) {
            if (session.getSessionid().equals(sessionId)) {
                session.setLastaccessed(newLastAccessed);
                check=true;
                break;
            }
        }
        if(!check)
        {
        	list.add(new SessionManagerScheduler(sessionId,newLastAccessed));
        }
    }
    public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public LocalDateTime getLastaccessed() {
		return lastaccessed;
	}
	public void setLastaccessed(LocalDateTime lastaccessed) {
		this.lastaccessed = lastaccessed;
	}
	public static void startScheduler() {
        scheduler.scheduleWithFixedDelay (() -> {
            try {
                SessionDao.updateSession(list); 
               SessionDao.autoDeleteExpiredSessions(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.MINUTES);  
    }

    public static void stopScheduler() {
        scheduler.shutdown();
    }
}
