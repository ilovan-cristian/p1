package ro.mpp2025.Domain;

import java.time.LocalDateTime;

public class Flight extends Entity<Integer> {
    private String destination;
    private LocalDateTime departure;
    private Integer seats;
    private String location;

    public Flight() {
        super();
        this.destination = null;
        this.departure = null;
        this.seats = null;
        this.location = null;
    }

    public Flight(Integer id, String destination, LocalDateTime departure, Integer seats, String location) {
        super(id);
        this.destination = destination;
        this.departure = departure;
        this.seats = seats;
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", destination='" + destination + "', departure=" + departure +
                ", seats=" + seats + ", location='" + location + "'}";
    }
}