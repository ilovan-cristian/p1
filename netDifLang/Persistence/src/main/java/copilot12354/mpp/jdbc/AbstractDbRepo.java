package copilot12354.mpp.jdbc;

import copilot12354.mpp.Entity;
import copilot12354.mpp.IRepo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public abstract class AbstractDbRepo<Id, T extends Entity<Id>> implements IRepo<Id, T> {
    private final ConnectionManager connectionManager;
    private final Logger logger;

    public AbstractDbRepo(ConnectionManager connectionManager, Logger logger) {
        this.connectionManager = connectionManager;
        this.logger = logger;

        logger.debug("Constructed repository");
    }

    @FunctionalInterface
    protected interface ResultSetMapper<E> {
        E apply(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    protected interface StatementGetter {
        PreparedStatement get(Connection connection) throws SQLException;
    }

    protected abstract PreparedStatement getDeleteStatement(Connection connection, Id id) throws SQLException;
    protected abstract PreparedStatement getFindAllStatement(Connection connection) throws SQLException;
    protected abstract PreparedStatement getFindOneStatement(Connection connection, Id id) throws SQLException;
    protected abstract PreparedStatement getSaveStatement(Connection connection, T entity) throws SQLException;
    protected abstract PreparedStatement getUpdateStatement(Connection connection, T entity) throws SQLException;
    protected abstract T transformDefaultResultSet(ResultSet resultSet) throws SQLException;

    protected <E> Iterable<E> executeQuery(StatementGetter statementGetter, ResultSetMapper<E> resultSetMapper) {
        var result = new ArrayList<E>();
        try {
            var connection = connectionManager.getConnection();
            try (var statement = statementGetter.get(connection);
                 var rs = statement.executeQuery()) {
                while (rs.next())
                    result.add(resultSetMapper.apply(rs));
            } catch (SQLException e) {
                throw e;
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Iterable<T> executeQuery(StatementGetter statementGetter) {
        return executeQuery(statementGetter, this::transformDefaultResultSet);
    }

    protected int executeUpdate(StatementGetter statementGetter) {
        try {
            var connection = connectionManager.getConnection();
            try (var statement = statementGetter.get(connection)) {
                return statement.executeUpdate();
            } catch (SQLException e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<T> findAll() {
        logger.debug("Caut toate obiectele");
        return executeQuery(this::getFindAllStatement);
    }

    @Override
    public Optional<T> findOne(Id id) {
        logger.debug("Caut entitatea cu id-ul {}", id);
        var result = executeQuery(con -> getFindOneStatement(con, id));
        return StreamSupport.stream(result.spliterator(), false).findFirst();
    }

    @Override
    public void save(T entity) {
        logger.debug("Salvez entitatea {}", entity);
        var result = executeUpdate(con -> getSaveStatement(con, entity));
        logger.debug("Save result: {}", result);
    }

    @Override
    public void delete(Id id) {
        logger.debug("Șterg entitatea cu id-ul: {}", id);
        executeUpdate(con -> getDeleteStatement(con, id));
        logger.debug("Delete result: {}", id);
    }

    @Override
    public void update(T entity) {
        logger.debug("Actualizez entitatea {}", entity);
        var result = executeUpdate(con -> getUpdateStatement(con, entity));
        logger.debug("Update result: {}", result);
    }
}