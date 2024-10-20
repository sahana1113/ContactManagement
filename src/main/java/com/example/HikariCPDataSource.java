package com.example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try (InputStream input = HikariCPDataSource.class.getClassLoader().getResourceAsStream("/config.properties")) {
            Properties properties = new Properties();
            
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }

            properties.load(input);
            config.setJdbcUrl(properties.getProperty("jdbc.url"));
            config.setUsername(properties.getProperty("jdbc.username"));
            config.setPassword(properties.getProperty("jdbc.password"));
            config.setDriverClassName(properties.getProperty("jdbc.driverClassName"));

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JDBC settings", e);
        }

        config.setMaximumPoolSize(50);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(60000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        ds = new HikariDataSource(config);
    }

    private HikariCPDataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static void close() {
        if (ds != null) {
            ds.close();
            ds = null; 
            System.out.println("Data source closed.");
        }
    }
}

