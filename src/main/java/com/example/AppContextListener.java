package com.example;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.Bean.BeanServer;
import com.Dao.DaoServer;
import com.Server.ServerNotifier;
import com.Session.SessionData;
import com.Session.SessionScheduler;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

@WebListener
public class AppContextListener implements ServletContextListener {
	BeanServer obj=new BeanServer();
    DaoServer dao=new DaoServer();
    public static String ipAddress;
    public static int port;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    	System.setProperty("app.logfile.name", "/home/sahana-pt7693/Project1/logs/app_" + timestamp + ".log");
        System.setProperty("access.logfile.name", "/home/sahana-pt7693/Project1/logs/access_" + timestamp + ".log");
        String log4jConfig = sce.getServletContext().getRealPath("/WEB-INF/classes/log4j.properties");
        PropertyConfigurator.configure(log4jConfig);
        SessionScheduler.startScheduler();
        Logger logger = Logger.getLogger(getClass());
        
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
            port = Integer.parseInt(sce.getServletContext().getInitParameter("serverPort"));
            obj.setIp_address(ipAddress);
            obj.setPort_number(port);
            dao.registerServer(ipAddress, port);
            SessionData.setServers(dao.getAllServerUrls("/syncSession",ipAddress,port));
            ServerNotifier.notifyServers(null, null, "FETCH_DB");
            logger.info("Server registered with IP: " + ipAddress + " and Port: " + port);
        } 
        catch (UnknownHostException e) {
            logger.error("Error retrieving IP address: " + e.getMessage(), e);
        } 
        catch (Exception e) {
            logger.error("Server registration failed: " + e.getMessage(), e);
        }
        
        logger.info("Server started at " + new java.util.Date());			
        System.out.println("Scheduler started!");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionScheduler.stopScheduler();
        System.out.println("Scheduler stopped!");
        dao.remove(ipAddress,port);
        HikariCPDataSource.close();
        AbandonedConnectionCleanupThread.checkedShutdown();
        Logger logger = Logger.getLogger(getClass());
        logger.info("Server stoped at " + new java.util.Date());
    }
}
