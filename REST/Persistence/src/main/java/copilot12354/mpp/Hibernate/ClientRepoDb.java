package copilot12354.mpp.Hibernate;

import copilot12354.mpp.Client;
import copilot12354.mpp.ClientRepo;
import copilot12354.mpp.IRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class ClientRepoDb implements ClientRepo
{
    private final SessionFactory sessionFactory;

    public ClientRepoDb()
    {
        this.sessionFactory = new Configuration()
                .configure()                   // loads hibernate.cfg.xml
                .addAnnotatedClass(Client.class)
                .buildSessionFactory();
    }

    public void close()
    {
        sessionFactory.close();
    }

    @Override
    public Iterable<Client> findAll()
    {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Client", Client.class)
                    .list();
        }
    }

    @Override
    public Optional<Client> findOne(Integer id)
    {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Client.class, id));
        }
    }

    @Override
    public void save(Client entity)
    {
        sessionFactory.inTransaction(session -> {
            session.persist(entity);
            System.out.println("Client added: " + entity);
        });
    }

    @Override
    public void delete(Integer id)
    {
        sessionFactory.inTransaction(session -> {
            Client client = session.find(Client.class, id);
            if (client != null) {
                session.remove(client);
                System.out.println("Client deleted: " + client);
            }
        });
    }

    @Override
    public void update(Client entity)
    {
        sessionFactory.inTransaction(session -> {
            session.merge(entity);
            System.out.println("Client updated: " + entity);
        });
    }
}
