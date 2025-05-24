package copilot12354.mpp.java.Service;

import copilot12354.mpp.java.Domain.Client;
import copilot12354.mpp.java.Domain.Employee;
import copilot12354.mpp.java.Domain.Flight;
import copilot12354.mpp.java.Domain.Ticket;
import copilot12354.mpp.java.Repository.EmployeeRepo;
import copilot12354.mpp.java.Repository.ClientRepo;
import copilot12354.mpp.java.Repository.FlightRepo;
import copilot12354.mpp.java.Repository.TicketRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAll
{
    private final EmployeeRepo repositoryEmployee;
    private final ClientRepo repositoryClient;
    private final FlightRepo repositoryFlight;
    private final TicketRepo repositoryTicket;

    public ServiceAll(
            EmployeeRepo repositoryEmployee,
            ClientRepo repositoryClient,
            FlightRepo repositoryFlight,
            TicketRepo repositoryTicket)
    {
        this.repositoryEmployee = repositoryEmployee;
        this.repositoryClient = repositoryClient;
        this.repositoryFlight = repositoryFlight;
        this.repositoryTicket = repositoryTicket;
    }

    // EMPLOYEE
    public void employeeAdd(String name, String password)
    {
        repositoryEmployee.save(new Employee(0, name, password));
    }
    public void employeeDelete(int id) {
        repositoryEmployee.delete(id);
    }
    public void employeeUpdate(int id, String name, String password) {
        var employee = repositoryEmployee.findOne(id);
        if (employee.isPresent()) {
            var emp = employee.get();
            emp.setName(name);
            emp.setPassword(password);
            repositoryEmployee.update(emp);
        }
    }
    public Employee employeeFindOne(int id) {
        return repositoryEmployee.findOne(id).orElse(null);
    }
    public List<Employee> employeeFindAll() {
        return (List<Employee>) repositoryEmployee.findAll();
    }
    // CLIENT
    public void clientAdd(String name) {
        repositoryClient.save(new Client(0, name));
    }
    public void clientDelete(int id) {
        repositoryClient.delete(id);
    }
    public void clientUpdate(int id, String name) {
        var client = repositoryClient.findOne(id);
        if (client.isPresent()) {
            var cli = client.get();
            cli.setName(name);
            repositoryClient.update(cli);
        }
    }
    public Client clientFindOne(int id) {
        return repositoryClient.findOne(id).orElse(null);
    }
    public List<Client> clientFindAll() {
        return (List<Client>) repositoryClient.findAll();
    }
    // TICKET
    public void ticketAdd(int clientId, int flightId) {
        var client = repositoryClient.findOne(clientId);
        var flight = repositoryFlight.findOne(flightId);
        if (client.isPresent() && flight.isPresent()) {
            var ticket = new Ticket(0, client.get(), flight.get());
            repositoryTicket.save(ticket);
        }
    }
    public void ticketDelete(int id) {
        repositoryTicket.delete(id);
    }
    public void ticketUpdate(int id, int clientId, int flightId) {
        var ticket = repositoryTicket.findOne(id);
        if (ticket.isPresent()) {
            var cli = repositoryClient.findOne(clientId);
            var flight = repositoryFlight.findOne(flightId);
            if (cli.isPresent() && flight.isPresent()) {
                var t = ticket.get();
                t.setClient(cli.get());
                t.setFlight(flight.get());
                repositoryTicket.update(t);
            }
        }
    }
    public Ticket ticketFindOne(int id) {
        return repositoryTicket.findOne(id).orElse(null);
    }
    public List<Ticket> ticketFindAll() {
        return (List<Ticket>) repositoryTicket.findAll();
    }
    // FLIGHT
    public void flightAdd(int id, String destination, LocalDateTime departure, Integer seats, String location) {
        repositoryFlight.save(new Flight(id, destination, departure, seats, location));
    }
    public void flightDelete(int id) {
        repositoryFlight.delete(id);
    }
    public void flightUpdate(int id, String destination, LocalDateTime departure, Integer seats, String location) {
        var flight = repositoryFlight.findOne(id);
        if (flight.isPresent()) {
            var f = flight.get();
            f.setDestination(destination);
            f.setDeparture(departure);
            f.setSeats(seats);
            f.setLocation(location);
            repositoryFlight.update(f);
        }
    }
    public Flight flightFindOne(int id) {
        return repositoryFlight.findOne(id).orElse(null);
    }
    public List<Flight> flightFindAll() {
        return (List<Flight>) repositoryFlight.findAll();
    }
    public List<Flight> flightFindByDestinationAndDeparture(String destination, LocalDateTime departure) {
        List<Flight> allFlights = new ArrayList<>();
        Iterable<Flight> flightsIterable = repositoryFlight.findAll();
        flightsIterable.forEach(allFlights::add);
        return allFlights.stream()
                .filter(f -> f.getDestination().equals(destination) && f.getDeparture().equals(departure))
                .collect(Collectors.toList());
    }
}
