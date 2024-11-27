package com.example;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.Session.SessionScheduler;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
/**
 * A servlet context listener that initializes and stops the SESSION scheduler 
 * when the web application context is created and destroyed.
 *
 *  @author Sahana
 *  @version 1.0
 */
@WebListener
public class AppContextListener implements ServletContextListener {
	/**
     * Called when the servlet context is initialized. 
     * Starts the SESSION scheduler.
     *
     * @param sce the ServletContextEvent containing the servlet context that 
     *            was initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    	System.setProperty("app.logfile.name", "/home/sahana-pt7693/Project1/logs/app_" + timestamp + ".log");
        System.setProperty("access.logfile.name", "/home/sahana-pt7693/Project1/logs/access_" + timestamp + ".log");
        String log4jConfig = sce.getServletContext().getRealPath("/WEB-INF/classes/log4j.properties");
        PropertyConfigurator.configure(log4jConfig);
        SessionScheduler.startScheduler();
        Logger logger = Logger.getLogger(getClass());
        logger.info("Server started at " + new java.util.Date());
        System.out.println("Scheduler started!");
    }
    /**
     * Called when the servlet context is destroyed. 
     * Stops the SESSION scheduler.
     *
     * @param sce the ServletContextEvent containing the servlet context that 
     *            was destroyed.
     */     
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
        HikariCPDataSource.close();
        AbandonedConnectionCleanupThread.checkedShutdown();
        Logger logger = Logger.getLogger(getClass());
        logger.info("Server stoped at " + new java.util.Date());
    }
}
