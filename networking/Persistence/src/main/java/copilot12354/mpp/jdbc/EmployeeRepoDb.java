package copilot12354.mpp.jdbc;

import copilot12354.mpp.Employee;
import copilot12354.mpp.EmployeeRepo;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeRepoDb extends AbstractDbRepo<Integer, Employee> implements EmployeeRepo
{
    public EmployeeRepoDb(ConnectionManager connectionManager)
    {
        super(connectionManager, LogManager.getLogger(EmployeeRepoDb.class));
    }
    @Override
    protected PreparedStatement getDeleteStatement(Connection connection, Integer id) throws SQLException {
        var result = connection.prepareStatement("DELETE FROM Employee WHERE id = ?");
        result.setInt(1, id);
        return result;
    }
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Employee");
    }
    @Override
    protected PreparedStatement getFindOneStatement(Connection connection, Integer id) throws SQLException {
        var result = connection.prepareStatement("SELECT * FROM Employee WHERE id = ?");
        result.setInt(1, id);
        return result;
    }
    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Employee entity) throws SQLException {
        if (entity.getId() != 0)
                throw new UnsupportedOperationException("Cannot save an entity that already has an ID");
        var result = connection.prepareStatement("INSERT INTO Employee VALUES (NULL, ?, ?)");
        result.setString(1, entity.getName());
        result.setString(2, entity.getPassword());
        return result;
    }
    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Employee entity) throws SQLException {
        var result = connection.prepareStatement("UPDATE Employee SET name = ?, password = ? WHERE id = ?");
        result.setString(1, entity.getName());
        result.setString(2, entity.getPassword());
        result.setInt(3, entity.getId());
        return result;
    }
    @Override
    protected Employee transformDefaultResultSet(java.sql.ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var password = resultSet.getString("password");
        return new Employee(id, name, password);
    }
}
