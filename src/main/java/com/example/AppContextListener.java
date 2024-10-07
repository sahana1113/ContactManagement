package com.example;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

   // private SessionManagerScheduler sessionManagerScheduler = new SessionManagerScheduler();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionManagerScheduler.startScheduler();
        System.out.println("Scheduler started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionManagerScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
    }
}