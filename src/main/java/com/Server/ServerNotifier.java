package com.Server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.Dao.DaoServer;
import com.Session.SessionData;

public class ServerNotifier {
    public static void notifyServers(String sessionId, Timestamp lastAccessed, String action) throws IOException {
    	List<String> serverUrls = SessionData.getServers();
        for(String url:serverUrls) {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(action);
            connection.setDoOutput(true);

            if (action.equals("POST")) {
                String payload = "session_id=" + sessionId + "&last_accessed=" + lastAccessed + "&action=" + action;
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(payload.getBytes(StandardCharsets.UTF_8));
                }
            } else if(action.equals("DELETE")) {
                String payload = "session_id=" + sessionId + "&action=" + action;
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(payload.getBytes(StandardCharsets.UTF_8));
                }
            } else if(action.equals("FETCH_DB"))
            {
            	String payload="action=" + action;
            	try (OutputStream os = connection.getOutputStream()) {
                    os.write(payload.getBytes(StandardCharsets.UTF_8));
                }
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpServletResponse.SC_OK) {
                System.out.println("Failed to notify server at: " + url);
            }
        }
    }
}
