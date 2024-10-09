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

public class SessionScheduler {
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
	public static void startScheduler() {
        scheduler.scheduleWithFixedDelay (() -> {
            try {
                DaoSession.updateSession(BeanSession.getList()); 
               DaoSession.autoDeleteExpiredSessions(BeanSession.getList()); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.MINUTES);  
    }

    public static void stopScheduler() {
        scheduler.shutdown();
    }
    
}
