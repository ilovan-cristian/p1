package ro.mpp2025.Repository;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}