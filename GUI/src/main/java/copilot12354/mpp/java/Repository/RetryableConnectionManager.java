package copilot12354.mpp.java.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class RetryableConnectionManager implements ConnectionManager {
    private final String url;
    private final Properties info;
    private Connection connection;

    public RetryableConnectionManager(Properties info) {
        this.url = info.getProperty("url");
        this.info = info;
        try {
            this.connection = makeNewConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection makeNewConnection() throws SQLException {
        return DriverManager.getConnection(url, info);
    }

    @Override
    public Connection getConnection() {
        boolean badConnection = false;
        try {
            if (connection.isClosed())
                badConnection = true;
        } catch (SQLException e) {
            badConnection = true;
        }

        if (badConnection)
            try {
                connection = makeNewConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Could not reopen connection", e);
            }

        return connection;
    }
}