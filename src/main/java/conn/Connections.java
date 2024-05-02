// Connections.java
package conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Connections {
    private static HikariDataSource pool = null;

    public static synchronized Connection getConnection() throws SQLException {
        if (pool != null) {
            return pool.getConnection();
        }
        // Initialize connection pool if not already initialized
        initializeConnectionPool();
        return pool.getConnection();
    }

    private static void initializeConnectionPool() throws SQLException {
        if (pool == null) {
            // Set up connection pool configuration
            HikariConfig config = new HikariConfig();
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                // Production environment settings
                String jdbcURL = String.format("jdbc:mysql:///%s", System.getProperty("googledatabasename"));
                Properties connProps = new Properties();
                connProps.setProperty("user", System.getProperty("googleusername"));
                connProps.setProperty("password", System.getProperty("googlepassword"));
                connProps.setProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
                connProps.setProperty("cloudSqlInstance", System.getProperty("cloudsqlinstance"));
                connProps.setProperty("ssl", System.getProperty("usessl"));
                config.setJdbcUrl(jdbcURL);
                config.setDataSourceProperties(connProps);
            } else {
                // Local environment settings
                config.setJdbcUrl("jdbc:mysql://localhost:3306/" + System.getProperty("localdatabasename") +
                        "?useSSL=false");
                config.setUsername(System.getProperty("localusername"));
                config.setPassword(System.getProperty("localpassword"));
            }
            config.setDriverClassName(System.getProperty("drivername"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            // Create the Hikari connection pool
            pool = new HikariDataSource(config);
        }
    }
}
