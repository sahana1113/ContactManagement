package com.example;
import org.apache.log4j.Logger;
import java.util.Scanner;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.zaxxer.hikari.HikariDataSource;
/**
 * A servlet context listener that initializes and stops the session scheduler 
 * when the web application context is created and destroyed.
 *
 *  @author Sahana
 *  @version 1.0
 */
@WebListener
public class AppContextListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(AppContextListener.class);
	/**
     * Called when the servlet context is initialized. 
     * Starts the session scheduler.
     *
     * @param sce the ServletContextEvent containing the servlet context that 
     *            was initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	logger.info("Server started at " + new java.util.Date());
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
    	logger.info("Server stopped at " + new java.util.Date());
        SessionScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
        HikariCPDataSource.close();
    }
}
