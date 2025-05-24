package copilot12354.mpp.jdbc;

import copilot12354.mpp.ClientRepo;
import copilot12354.mpp.FlightRepo;
import copilot12354.mpp.Ticket;
import copilot12354.mpp.TicketRepo;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketRepoDb extends AbstractDbRepo<Integer, Ticket> implements TicketRepo {
    private final ClientRepo clientRepo;
    private final FlightRepo flightRepo;
    public TicketRepoDb(ConnectionManager connectionManager, ClientRepo clientRepo, FlightRepo flightRepo) {
        super(connectionManager, LogManager.getLogger(TicketRepoDb.class));
        this.clientRepo = clientRepo;
        this.flightRepo = flightRepo;
    }
    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("DELETE FROM Ticket WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Ticket");
    }
    @Override
    protected PreparedStatement getFindOneStatement(Connection connection, Integer integer) throws SQLException {
        var result = connection.prepareStatement("SELECT * FROM Ticket WHERE id = ?");
        result.setInt(1, integer);
        return result;
    }
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Ticket entity) throws SQLException {
        if (entity.getId() != 0)
                throw new UnsupportedOperationException("Cannot save an entity that already has an ID");
        var result = connection.prepareStatement("INSERT INTO Ticket VALUES (NULL, ?, ?)");
        result.setInt(1, entity.getClient().getId());
        result.setInt(2, entity.getFlight().getId());
        return result;
    }
    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Ticket entity) throws SQLException {
        var result = connection.prepareStatement("UPDATE Ticket SET client_id = ?, flight_id = ? WHERE id = ?");
        result.setInt(1, entity.getClient().getId());
        result.setInt(2, entity.getFlight().getId());
        result.setInt(3, entity.getId());
        return result;
    }
    @Override
    protected Ticket transformDefaultResultSet(java.sql.ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var client = clientRepo.findOne(resultSet.getInt("client_id")).get();
        var flight = flightRepo.findOne(resultSet.getInt("flight_id")).get();
        return new Ticket(id, client, flight);
    }
}
