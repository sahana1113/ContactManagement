package com.example;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionScheduler.startScheduler();
        System.out.println("Scheduler started!");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
    }
}
