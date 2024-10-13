package com.example;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.Bean.BeanSession;
import com.Dao.DaoSession;
/**
 * Manages the scheduling of session updates and automatic deletion of 
 * expired sessions in the application. This class uses a scheduled 
 * executor service to run these tasks at fixed intervals.
 *
 * @author Sahana
 *  @version 1.0
 */
public class SessionScheduler {
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	/**
     * Starts the session scheduler. This method schedules tasks to 
     * update sessions and delete expired sessions at fixed intervals 
     * (every 2 minutes).
     */
	public static void startScheduler() {
        scheduler.scheduleWithFixedDelay (() -> {
            try {
                DaoSession.updateSession(SessionTreeUpdate.getSessionSet()); 
               DaoSession.autoDeleteExpiredSessions(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.MINUTES);  
    }
	  /**
     * Stops the session scheduler. This method gracefully shuts down 
     * the scheduled executor service, stopping all scheduled tasks.
     */
    public static void stopScheduler() {
        scheduler.shutdown();
    }
    
}
