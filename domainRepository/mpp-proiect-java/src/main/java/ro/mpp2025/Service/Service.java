package ro.mpp2025.Service;

import ro.mpp2025.Domain.Client;
import ro.mpp2025.Domain.Employee;
import ro.mpp2025.Domain.Flight;
import ro.mpp2025.Domain.Ticket;
import ro.mpp2025.Repository.ClientRepo;
import ro.mpp2025.Repository.EmployeeRepo;
import ro.mpp2025.Repository.FlightRepo;
import ro.mpp2025.Repository.TicketRepo;

import java.util.Optional;

public class Service {
    private final ClientRepo clientRepo;
    private final EmployeeRepo employeeRepo;
    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;

    public Service(ClientRepo clientRepo, EmployeeRepo employeeRepo, FlightRepo flightRepo, TicketRepo ticketRepo) {
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    // Client operations
    public Iterable<Client> getAllClients() {
        return clientRepo.findAll();
    }

    public Optional<Client> getClientById(Integer id) {
        return clientRepo.findOne(id);
    }

    public void addClient(Client client) {
        clientRepo.save(client);
    }

    public void updateClient(Client client) {
        clientRepo.update(client);
    }

    public void deleteClient(Integer id) {
        clientRepo.delete(id);
    }

    // Employee operations
    public Iterable<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepo.findOne(id);
    }

    public void addEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepo.update(employee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepo.delete(id);
    }

    // Flight operations
    public Iterable<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public Optional<Flight> getFlightById(Integer id) {
        return flightRepo.findOne(id);
    }

    public void addFlight(Flight flight) {
        flightRepo.save(flight);
    }

    public void updateFlight(Flight flight) {
        flightRepo.update(flight);
    }

    public void deleteFlight(Integer id) {
        flightRepo.delete(id);
    }

    // Ticket operations
    public Iterable<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    public Optional<Ticket> getTicketById(Integer id) {
        return ticketRepo.findOne(id);
    }

    public void addTicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketRepo.update(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepo.delete(id);
    }
}
