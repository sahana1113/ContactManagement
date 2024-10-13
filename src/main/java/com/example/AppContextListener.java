package com.example;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * A servlet context listener that initializes and stops the session scheduler 
 * when the web application context is created and destroyed.
 *
 *  @author Sahana
 *  @version 1.0
 */
@WebListener
public class AppContextListener implements ServletContextListener {
	/**
     * Called when the servlet context is initialized. 
     * Starts the session scheduler.
     *
     * @param sce the ServletContextEvent containing the servlet context that 
     *            was initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionScheduler.startScheduler();
        System.out.println("Scheduler started!");
    }
    /**
     * Called when the servlet context is destroyed. 
     * Stops the session scheduler.
     *
     * @param sce the ServletContextEvent containing the servlet context that 
     *            was destroyed.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
    }
}
