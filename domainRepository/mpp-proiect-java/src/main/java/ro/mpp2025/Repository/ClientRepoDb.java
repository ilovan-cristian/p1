package ro.mpp2025.Repository;

import org.apache.logging.log4j.LogManager;
import ro.mpp2025.Domain.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepoDb extends AbstractDbRepo<Integer, Client> implements ClientRepo {
    public ClientRepoDb(ConnectionManager connectionManager) {
        super(connectionManager, LogManager.getLogger(ClientRepoDb.class));
    }
    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("DELETE FROM Client WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Client");
    }
    @Override
    protected PreparedStatement getFindOneStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("SELECT * FROM Client WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Client entity) throws SQLException {
        if (entity.getId() != 0)
                throw new UnsupportedOperationException("Cannot save an entity that already has an ID");
        var result = connection.prepareStatement("INSERT INTO Client VALUES (?, ?)");
        result.setInt(1, entity.getId());
        result.setString(2, entity.getName());
        return result;
    }
    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Client entity) throws SQLException {
        var result = connection.prepareStatement("UPDATE Client SET name = ? WHERE id = ?");
        result.setString(1, entity.getName());
        result.setInt(2, entity.getId());
        return result;
    }
    @Override
    protected Client transformDefaultResultSet(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        return new Client(id, name);
    }
}
