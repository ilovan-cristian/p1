package copilot12354.mpp.Hibernate;

import copilot12354.mpp.Flight;
import copilot12354.mpp.FlightRepo;
import copilot12354.mpp.IRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlightRepoDb implements FlightRepo
{
    private final SessionFactory sessionFactory;

    public FlightRepoDb()
    {
        this.sessionFactory = new Configuration()
                .configure()                    // loads hibernate.cfg.xml
                .addAnnotatedClass(Flight.class)
                .buildSessionFactory();
    }

    public void close()
    {
        sessionFactory.close();
    }

    @Override
    public Iterable<Flight> findAll()
    {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Flight", Flight.class)
                    .list();
        }
    }

    @Override
    public Optional<Flight> findOne(Integer id)
    {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Flight.class, id));
        }
    }

    @Override
    public void save(Flight entity)
    {
        sessionFactory.inTransaction(session -> {
            session.persist(entity);
            System.out.println("Flight added: " + entity);
        });
    }

    @Override
    public void delete(Integer id)
    {
        sessionFactory.inTransaction(session -> {
            Flight flight = session.find(Flight.class, id);
            if (flight != null) {
                session.remove(flight);
                System.out.println("Flight deleted: " + flight);
            }
        });
    }

    @Override
    public void update(Flight entity)
    {
        sessionFactory.inTransaction(session -> {
            session.merge(entity);
            System.out.println("Flight updated: " + entity);
        });
    }
}
