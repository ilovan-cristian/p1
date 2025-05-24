package copilot12354.mpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ServicesImplementation implements IServices
{
    private final EmployeeRepo repositoryEmployee;
    private final ClientRepo repositoryClient;
    private final FlightRepo repositoryFlight;
    private final TicketRepo repositoryTicket;
    private Map<Integer, IObserver> loggedClients;
    private static Logger logger = LogManager.getLogger(ServicesImplementation.class);

    public ServicesImplementation(
            EmployeeRepo repositoryEmployee,
            ClientRepo repositoryClient,
            FlightRepo repositoryFlight,
            TicketRepo repositoryTicket)
    {
        this.repositoryEmployee = repositoryEmployee;
        this.repositoryClient = repositoryClient;
        this.repositoryFlight = repositoryFlight;
        this.repositoryTicket = repositoryTicket;
        loggedClients = new ConcurrentHashMap<>();
    }

    // EMPLOYEE
    public void employeeAdd(String name, String password)
    {
        repositoryEmployee.save(new Employee(null, name, password));
    }

    public void employeeDelete(int id)
    {
        repositoryEmployee.delete(id);
    }

    public void employeeUpdate(int id, String name, String password)
    {
        var employee = repositoryEmployee.findOne(id);
        if (employee.isPresent())
        {
            var emp = employee.get();
            emp.setName(name);
            emp.setPassword(password);
            repositoryEmployee.update(emp);
        }
    }

    public Employee employeeFindOne(int id)
    {
        return repositoryEmployee.findOne(id).orElse(null);
    }

    public List<Employee> employeeFindAll()
    {
        return (List<Employee>) repositoryEmployee.findAll();
    }

    // CLIENT
    public void clientAdd(String name)
    {
        repositoryClient.save(new Client(null, name));
    }

    public void clientDelete(int id)
    {
        repositoryClient.delete(id);
    }

    public void clientUpdate(int id, String name)
    {
        var client = repositoryClient.findOne(id);
        if (client.isPresent())
        {
            var cli = client.get();
            cli.setName(name);
            repositoryClient.update(cli);
        }
    }

    public Client clientFindOne(int id)
    {
        return repositoryClient.findOne(id).orElse(null);
    }

    public ArrayList<Client> clientFindAll()
    {
        return (ArrayList<Client>) repositoryClient.findAll();
    }

    // TICKET
    @Override
    public void ticketAdd(Integer clientId, Integer flightId)
    {
        var client = repositoryClient.findOne(clientId);
        var flight = repositoryFlight.findOne(flightId);
        if (client.isPresent() && flight.isPresent())
            repositoryTicket.save(new Ticket(null, client.get(), flight.get()));
    }

    private final int defaultThreadsNo = 3;

    private void refresh()
    {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (Map.Entry<Integer, IObserver> entry : loggedClients.entrySet())
        {
            Integer clientId = entry.getKey();
            IObserver client = entry.getValue();
            if (client != null)
            {
                executor.execute(() -> {
                    try
                    {
                        client.ticketBooked();
                        logger.debug("Notifying client " + clientId + " about ticket booking.");
                    }
                    catch (Exception e)
                    {
                        logger.error("Error notifying client {}: {}", clientId, e.getMessage(), e);
                    }
                });
            }
        }

        executor.shutdown();

    }

    public void ticketDelete(int id)
    {
        repositoryTicket.delete(id);
    }

    public void ticketUpdate(int id, int clientId, int flightId)
    {
        var ticket = repositoryTicket.findOne(id);
        if (ticket.isPresent())
        {
            var cli = repositoryClient.findOne(clientId);
            var flight = repositoryFlight.findOne(flightId);
            if (cli.isPresent() && flight.isPresent())
            {
                var t = ticket.get();
                t.setClient(cli.get());
                t.setFlight(flight.get());
                repositoryTicket.update(t);
            }
        }
    }

    public Ticket ticketFindOne(int id)
    {
        return repositoryTicket.findOne(id).orElse(null);
    }

    public List<Ticket> ticketFindAll()
    {
        return (List<Ticket>) repositoryTicket.findAll();
    }

    // FLIGHT
    public void flightAdd(String destination, LocalDateTime departure, Integer seats, String location)
    {
        repositoryFlight.save(new Flight(null, destination, departure, seats, location));
    }

    public void flightDelete(int id)
    {
        repositoryFlight.delete(id);
    }

    @Override
    public void flightUpdate(Flight flight)
    {
        var f = repositoryFlight.findOne(flight.getId());
        if (f.isPresent())
        {
            repositoryFlight.update(flight);
            refresh();
        }
    }

    @Override
    public void setClient(IObserver client)
    {

    }

    public Flight flightFindOne(int id)
    {
        return repositoryFlight.findOne(id).orElse(null);
    }

    @Override
    public ArrayList<Flight> flightFindAll()
    {
        return (ArrayList<Flight>) repositoryFlight.findAll();
    }

    public List<Flight> flightFindByDestinationAndDeparture(String destination, LocalDateTime departure)
    {
        List<Flight> allFlights = new ArrayList<>();
        Iterable<Flight> flightsIterable = repositoryFlight.findAll();
        flightsIterable.forEach(allFlights::add);
        return allFlights.stream()
                .filter(f -> f.getDestination().equals(destination) && f.getDeparture().equals(departure))
                .collect(Collectors.toList());
    }

    @Override
    public void login(Employee employee, IObserver client) throws ServicesException
    {
        Employee e = employeeFindOne(employee.getId());
        if (e == null || !e.getPassword().equals(employee.getPassword()))
            throw new ServicesException("Incorrect ID or password");
        loggedClients.put(employee.getId(), client);
        logger.debug("Employee " + employee.getId() + " logged in.");
    }
}
