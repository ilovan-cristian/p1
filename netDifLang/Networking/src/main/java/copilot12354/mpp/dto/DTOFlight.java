package copilot12354.mpp.dto;

import java.io.Serializable;

public class DTOFlight implements Serializable
{
    private Integer id;
    private String destination;
    // Represent departure as an ISO-8601 String, e.g., "2025-04-15T14:30:00"
    private String departure;
    private Integer seats;
    private String location;

    public DTOFlight(Integer id)
    {
        this(id, "", "", 0, "");
    }

    public DTOFlight(Integer id, String destination, String departure, Integer seats, String location)
    {
        this.id = id;
        this.destination = destination;
        this.departure = departure;
        this.seats = seats;
        this.location = location;
    }

    public Integer getId()
    {
        return id;
    }

    public String getDestination()
    {
        return destination;
    }

    public String getDeparture()
    {
        return departure;
    }

    public Integer getSeats()
    {
        return seats;
    }

    public String getLocation()
    {
        return location;
    }

    @Override
    public String toString()
    {
        return "FlightDTO[" + id + " --> destination: " + destination + ", departure: " + departure +
                ", seats: " + seats + ", location: " + location + "]";
    }
}
