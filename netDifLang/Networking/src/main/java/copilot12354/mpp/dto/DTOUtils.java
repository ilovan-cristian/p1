package copilot12354.mpp.dto;

import copilot12354.mpp.Client;
import copilot12354.mpp.Employee;
import copilot12354.mpp.Flight;
import copilot12354.mpp.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DTOUtils
{

    public static Employee getFromDTO(DTOEmployee dto)
    {
        return new Employee(dto.getId(), dto.getName(), dto.getPassword());
    }

    public static DTOEmployee getDTO(Employee employee)
    {
        return new DTOEmployee(employee.getId(), employee.getName(), employee.getPassword());
    }

    public static Client getFromDTO(DTOClient dto)
    {
        return new Client(dto.getId(), dto.getName());
    }

    public static DTOClient getDTO(Client client)
    {
        return new DTOClient(client.getId(), client.getName());
    }

    public static Flight getFromDTO(DTOFlight dto)
    {
        LocalDateTime departure = LocalDateTime.parse(dto.getDeparture());
        return new Flight(dto.getId(), dto.getDestination(), departure, dto.getSeats(), dto.getLocation());
    }

    public static DTOFlight getDTO(Flight flight)
    {
        String departureStr = flight.getDeparture().toString();
        return new DTOFlight(flight.getId(), flight.getDestination(), departureStr, flight.getSeats(), flight.getLocation());
    }

    public static DTOTicket getDTO(Integer clientId, Integer flightId)
    {
        return new DTOTicket(clientId, flightId);
    }

    public static ArrayList<DTOFlight> getDTO(ArrayList<Flight> flights)
    {
        ArrayList<DTOFlight> DTOflights = new ArrayList<>();
        for (int i = 0; i < flights.size(); ++i)
        {
            DTOflights.add(getDTO(flights.get(i)));
        }
        return DTOflights;
    }

    public static ArrayList<Flight> getFromDTO(ArrayList<DTOFlight> DTOflights)
    {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int i = 0; i < DTOflights.size(); ++i)
        {
            flights.add(getFromDTO(DTOflights.get(i)));
        }
        return flights;
    }

    public static ArrayList<DTOClient> getClientsDTO(ArrayList<Client> clients)
    {
        ArrayList<DTOClient> DTOclients = new ArrayList<>();
        for (int i = 0; i < clients.size(); ++i)
        {
            DTOclients.add(getDTO(clients.get(i)));
        }
        return DTOclients;
    }

    public static ArrayList<Client> getClientsFromDTO(ArrayList<DTOClient> DTOclients)
    {
        ArrayList<Client> clients = new ArrayList<>();
        for (int i = 0; i < DTOclients.size(); ++i)
        {
            clients.add(getFromDTO(DTOclients.get(i)));
        }
        return clients;
    }
}
