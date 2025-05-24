package copilot12354.mpp;

import java.util.Optional;

public interface IRepo <Id, T extends EntityBase<Id>> {
    Iterable<T> findAll();
    Optional<T> findOne(Id id);
    void save(T entity);
    void delete(Id id);
    void update(T entity);
}