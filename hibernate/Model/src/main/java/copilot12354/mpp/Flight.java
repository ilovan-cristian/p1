package copilot12354.mpp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Flight")
public class Flight extends EntityBase<Integer>
{
    @Column(name = "destination", nullable = false)
    private String destination;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "departure", nullable = false)
    private LocalDateTime departure;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "location", nullable = false)
    private String location;

    public Flight()
    {
        super();
    }

    public Flight(Integer id, String destination, LocalDateTime departure, Integer seats, String location)
    {
        this.id = id; // inherited from Base
        this.destination = destination;
        this.departure = departure;
        this.seats = seats;
        this.location = location;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public LocalDateTime getDeparture()
    {
        return departure;
    }

    public void setDeparture(LocalDateTime departure)
    {
        this.departure = departure;
    }

    public Integer getSeats()
    {
        return seats;
    }

    public void setSeats(Integer seats)
    {
        this.seats = seats;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "Flight{" +
                "id=" + id + // inherited from Base
                ", destination='" + destination + '\'' +
                ", departure=" + departure +
                ", seats=" + seats +
                ", location='" + location + '\'' +
                '}';
    }
}
