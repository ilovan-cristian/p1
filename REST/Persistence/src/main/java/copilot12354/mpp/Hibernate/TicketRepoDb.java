package copilot12354.mpp.Hibernate;

import copilot12354.mpp.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class TicketRepoDb implements TicketRepo
{
    private final SessionFactory sessionFactory;

    public TicketRepoDb()
    {
        this.sessionFactory = new Configuration()
                .configure()                    // loads hibernate.cfg.xml
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(Flight.class) // for the @ManyToOne relation
                .addAnnotatedClass(Client.class) // for the @ManyToOne relation
                .buildSessionFactory();
    }

    public void close()
    {
        sessionFactory.close();
    }

    @Override
    public Iterable<Ticket> findAll()
    {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Ticket", Ticket.class)
                    .list();
        }
    }

    @Override
    public Optional<Ticket> findOne(Integer id)
    {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Ticket.class, id));
        }
    }

    @Override
    public void save(Ticket entity)
    {
        sessionFactory.inTransaction(session -> {
            session.persist(entity);
            System.out.println("Ticket added: " + entity);
        });
    }

    @Override
    public void delete(Integer id)
    {
        sessionFactory.inTransaction(session -> {
            Ticket ticket = session.find(Ticket.class, id);
            if (ticket != null) {
                session.remove(ticket);
                System.out.println("Ticket deleted: " + ticket);
            }
        });
    }

    @Override
    public void update(Ticket entity)
    {
        sessionFactory.inTransaction(session -> {
            session.merge(entity);
            System.out.println("Ticket updated: " + entity);
        });
    }
}
