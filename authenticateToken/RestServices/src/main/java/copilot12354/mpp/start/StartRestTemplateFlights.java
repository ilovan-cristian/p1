package copilot12354.mpp.start;

import copilot12354.mpp.Flight;
import copilot12354.mpp.client.FlightsClient;
import copilot12354.mpp.services.ServiceException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class StartRestTemplateFlights
{
    private static final FlightsClient client = new FlightsClient();

    public static void main(String[] args)
    {
        try
        {
            Flight flight = new Flight(
                    null,
                    "Paris", // Destination
                    LocalDateTime.of(2025, 5, 9, 14, 30),
                    150, // Number of seats
                    "Bucharest" // Location
            );
            System.out.println("Create: " + client.create(flight));
            Arrays.stream(client.getAll()).forEach(System.out::println);
            System.out.println("One: " + client.getById(3));
            System.out.println("Update:");
            Flight u = client.getById(3);
            u.setDestination("London");
            client.update(3, u);
            System.out.println("After: " + client.getById(3));
            System.out.println("Delete #3");
            client.delete(3);
        } catch (ServiceException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
}