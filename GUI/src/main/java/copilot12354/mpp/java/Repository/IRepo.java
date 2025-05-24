package copilot12354.mpp.java.Repository;

import copilot12354.mpp.java.Domain.Entity;

import java.util.Optional;

public interface IRepo <Id, T extends Entity<Id>> {
    Iterable<T> findAll();
    Optional<T> findOne(Id id);
    void save(T entity);
    void delete(Id id);
    void update(T entity);
}