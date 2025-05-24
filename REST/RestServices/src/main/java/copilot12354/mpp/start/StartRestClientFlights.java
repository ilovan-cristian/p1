package copilot12354.mpp.start;

import copilot12354.mpp.Flight;
import copilot12354.mpp.client.NewFlightsClient;
import copilot12354.mpp.services.ServiceException;

import java.time.LocalDateTime;

public class StartRestClientFlights
{
    private static final NewFlightsClient client = new NewFlightsClient();

    public static void main(String[] args)
    {
        try
        {
            Flight f = new Flight(
                    null, // ID (use a valid ID or generate it)
                    "Paris", // Destination
                    LocalDateTime.of(2025, 5, 9, 14, 30),
                    150, // Number of seats
                    "Bucharest" // Location
            );
            System.out.println("Create: " + client.create(f));

            // Read all
            System.out.println("\nAll flights:");
            for (Flight fx : client.getAll())
            {
                System.out.println(fx);
            }

            // Read one
            System.out.println("\nFlight #3: " + client.getById(3));

            // Update
            f.setDestination("London");
            System.out.println("\nUpdate: " + client.update(3, f));

            // Delete
            System.out.println("\nDelete #3");
            client.delete(3);
        } catch (ServiceException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
