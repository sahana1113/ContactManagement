package com.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanSession;
import com.Dao.DaoServer;
import com.Session.SessionData;

@WebServlet("/syncSession")
public class SyncSessionServlet extends HttpServlet {
    private static TreeSet<BeanSession> sessionSet = SessionData.getSessionSet();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String action = request.getParameter("action");
        String sessionId = request.getParameter("session_id");
        String lastAccessed = request.getParameter("last_accessed");
        Timestamp last = Timestamp.valueOf(lastAccessed);
        if(action.equals("POST")) {
        if (sessionId != null && lastAccessed != null) {
            synchronized (sessionSet) {
            	BeanSession sessionData = new BeanSession(sessionId, last);
              sessionSet.remove(sessionData); // Remove if it already exists
              sessionSet.add(sessionData); 
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        }
        else if(action.equals("FETCH_DB"))
        {
        	SessionData.setServers(new DaoServer().getAllServerUrls("/syncSession"));
        	response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getParameter("session_id");

        if (sessionId != null) {
            synchronized (sessionSet) {
                sessionSet.remove(sessionId);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
