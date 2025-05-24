package copilot12354.mpp.java.Repository;

import org.apache.logging.log4j.LogManager;
import copilot12354.mpp.java.Domain.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FlightRepoDb extends AbstractDbRepo<Integer, Flight> implements FlightRepo {
    public FlightRepoDb(ConnectionManager connectionManager) {
        super(connectionManager, LogManager.getLogger(FlightRepoDb.class));
    }
    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("DELETE FROM Flight WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Flight");
    }
    @Override
    protected PreparedStatement getFindOneStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("SELECT * FROM Flight WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Flight entity) throws SQLException {
        if (entity.getId() != 0)
                throw new UnsupportedOperationException("Cannot save an entity that already has an ID");
        var result = connection.prepareStatement("INSERT INTO Flight VALUES (NULL, ?, ?, ?, ?)");
        result.setString(1, entity.getDestination());
        result.setTimestamp(2, Timestamp.valueOf(entity.getDeparture()));
        result.setInt(3, entity.getSeats());
        result.setString(4, entity.getLocation());
        return result;
    }
    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Flight entity) throws SQLException {
        var result = connection.prepareStatement("UPDATE Flight SET destination = ?, departure = ?, seats = ?, location = ? WHERE id = ?");
        result.setString(1, entity.getDestination());
        result.setTimestamp(2, Timestamp.valueOf(entity.getDeparture()));
        result.setInt(3, entity.getSeats());
        result.setString(4, entity.getLocation());
        result.setInt(5, entity.getId());
        return result;
    }
    @Override
    protected Flight transformDefaultResultSet(java.sql.ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var destination = resultSet.getString("destination");
        var departure = resultSet.getTimestamp("departure").toLocalDateTime();
        var seats = resultSet.getInt("seats");
        var location = resultSet.getString("location");
        return new Flight(id, destination, departure, seats, location);
    }
}
