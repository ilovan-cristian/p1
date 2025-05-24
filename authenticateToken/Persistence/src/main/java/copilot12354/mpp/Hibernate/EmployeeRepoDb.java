package copilot12354.mpp.Hibernate;

import copilot12354.mpp.Employee;
import copilot12354.mpp.EmployeeRepo;
import copilot12354.mpp.IRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeRepoDb implements EmployeeRepo
{
    private final SessionFactory sessionFactory;

    public EmployeeRepoDb()
    {
        this.sessionFactory = new Configuration()
                .configure()                      // looks for hibernate.cfg.xml on classpath
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
    }

    public void close()
    {
        sessionFactory.close();
    }

    @Override
    public Iterable<Employee> findAll()
    {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Employee", Employee.class)
                    .list();
        }
    }

    @Override
    public Optional<Employee> findOne(Integer id)
    {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Employee.class, id));
        }
    }

    @Override
    public void save(Employee entity)
    {
        sessionFactory.inTransaction(session -> {
            session.persist(entity);
            System.out.println("Employee added: " + entity);
        });
    }

    @Override
    public void delete(Integer id)
    {
        sessionFactory.inTransaction(session -> {
            Employee emp = session.find(Employee.class, id);
            if (emp != null) {
                session.remove(emp);
                System.out.println("Employee deleted: " + emp);
            }
        });
    }

    @Override
    public void update(Employee entity)
    {
        sessionFactory.inTransaction(session -> {
            session.merge(entity);
            System.out.println("Employee updated: " + entity);
        });
    }
}
