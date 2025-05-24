package ro.mpp2025.Repository;

import ro.mpp2025.Domain.Entity;

import java.util.Optional;

public interface IRepo <Id, T extends Entity<Id>> {
    Iterable<T> findAll();
    Optional<T> findOne(Id id);
    void save(T entity);
    void delete(Id id);
    void update(T entity);
}