package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Query.Enum.Tables;
import com.Query.QueryLayer;
import com.example.HikariCPDataSource;

public class DaoServer {
	public void registerServer(String ipAddress, int port) {
        try (Connection connection =  HikariCPDataSource.getConnection()) {
            String query = "INSERT INTO servers (ip_address, port_number) VALUES (?, ?) " +
                           "ON DUPLICATE KEY UPDATE created_time = CURRENT_TIMESTAMP";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ipAddress);
            stmt.setInt(2, port);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllServerUrls(String endpoint) {
        List<String> serverUrls = new ArrayList<>();
        try (Connection connection =  HikariCPDataSource.getConnection()) {
            String query = "SELECT ip_address, port_number FROM servers";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String serverUrl = "http://" + rs.getString("server_ip") + ":" + rs.getInt("server_port") + endpoint;
                serverUrls.add(serverUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverUrls;
    }

	public void remove(String ipAddress, int port) {
		 try (Connection connection =  HikariCPDataSource.getConnection()) {
	            String query = "DELETE FROM servers where ip_address=? and port_number=?";
	            PreparedStatement stmt = connection.prepareStatement(query);
	            stmt.setString(1, ipAddress);
	            stmt.setInt(2, port);
	            int rs = stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
